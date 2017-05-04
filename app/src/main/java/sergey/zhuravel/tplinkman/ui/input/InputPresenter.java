package sergey.zhuravel.tplinkman.ui.input;


import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import sergey.zhuravel.tplinkman.model.RouterSession;
import sergey.zhuravel.tplinkman.utils.NetworkUtils;
import sergey.zhuravel.tplinkman.utils.RxUtils;

public class InputPresenter implements InputContract.Presenter {

    private InputContract.View mView;
    private InputContract.Model mModel;
    private CompositeSubscription mCompositeSubscription;


    public InputPresenter(InputContract.View mView, InputContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;

        mCompositeSubscription = RxUtils.getNewCompositeSubIfUnsubscribed(mCompositeSubscription);
    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel = null;


        RxUtils.unsubscribeIfNotNull(mCompositeSubscription);
    }


    @Override
    public void validateAndInput(String ip, String username, String password) {
        mCompositeSubscription.add(NetworkUtils.isHostReachable(ip)
                .subscribe(reachable -> {
                    if (reachable) {
                        mCompositeSubscription.add(mModel.getKey(ip, username, password)
                                .doOnRequest(request -> mView.showProgressDialog())
                                .doOnUnsubscribe(() -> mView.hideProgressDialog())
                                .subscribe(key -> {
                                            if (key.equals("old")) {
                                                validatePasswordOld(ip, username, password);
                                            } else {
                                                if (key.length() < 10) {
                                                    mView.showDialogRepeat();
                                                } else {
                                                    validatePassword(ip, username, password, key);
                                                }
                                            }
                                        },
                                        throwable -> Log.e("SERJ-key-error", throwable.getMessage())));

                    } else {
                        mView.showDialogHostUnreachable();
                    }
                }));


    }


    private void validatePassword(String ip, String username, String password, String key) {
        mCompositeSubscription.add(mModel.inputValidate(ip, username, password, key)
                .doOnRequest(request -> mView.showProgressDialog())
                .doOnUnsubscribe(() -> mView.hideProgressDialog())
                .subscribe(validate -> {
                            if (validate.contains("statusPara")) {
                                mModel.savePreference(ip, key, username, password);
                                mView.navigateToMainActivity();
                            } else {
                                mView.showDialogErrorInput();
                            }
                        },
                        throwable -> Log.e("SERJ-validate-error", throwable.getMessage())));
    }


    private void validatePasswordOld(String ip, String username, String password) {

        mCompositeSubscription.add(mModel.inputValidateOld(ip, username, password)
                .doOnRequest(request -> mView.showProgressDialog())
                .doOnUnsubscribe(() -> mView.hideProgressDialog())
                .subscribe(validate -> {
                            if (validate.contains("statusPara")) {
                                mModel.savePreference(ip, username, password);
                                mView.navigateToMainActivity();
                            } else {
                                mView.showDialogErrorInput();
                            }
                        },
                        throwable -> Log.e("SERJ-validate-error", throwable.getMessage())));
    }

    @Override
    public void saveSession(String ip, String username, String password, String nameConnection) {
        mModel.saveSession(new RouterSession(ip, username, password, nameConnection));
        getSession();
    }

    @Override
    public void getSession() {
        mCompositeSubscription.add(mModel.getSessions().subscribe(routerSessions -> {
                    if (routerSessions.size() != 0) {
                        mView.sessionHistoryAccessibility(true);
                        mView.addSession(routerSessions);


                    } else {
                        mView.sessionHistoryAccessibility(false);
                    }
                }

        ));
    }

    @Override
    public void isPing(String ip) {
        mCompositeSubscription.add(Observable.interval(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(v -> mView.setVisibleRouter(NetworkUtils.isPing(ip)),
                        e -> Log.e("TIMER", e.getMessage())));


    }

}

