package sergey.zhuravel.tplinkman.ui.start;


import android.util.Log;

import rx.subscriptions.CompositeSubscription;
import sergey.zhuravel.tplinkman.utils.NetworkUtils;
import sergey.zhuravel.tplinkman.utils.RxUtils;

public class StartPresenter implements StartContract.Presenter {
    private StartContract.View mView;
    private StartContract.Model mModel;
    private CompositeSubscription mCompositeSubscription;

    public StartPresenter(StartContract.View mView, StartContract.Model mModel) {
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

}

