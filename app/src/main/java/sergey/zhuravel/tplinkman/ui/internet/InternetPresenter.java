package sergey.zhuravel.tplinkman.ui.internet;

import android.util.Log;

import rx.subscriptions.CompositeSubscription;
import sergey.zhuravel.tplinkman.constant.ApiConstant;
import sergey.zhuravel.tplinkman.constant.TypeConstant;
import sergey.zhuravel.tplinkman.utils.RxUtils;

public class InternetPresenter implements InternetContract.Presenter {
    private InternetContract.View mView;
    private InternetContract.Model mModel;
    private CompositeSubscription mCompositeSubscription;


    public InternetPresenter(InternetContract.View mView, InternetContract.Model mModel) {
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
    public void getDynamicSetting() {
        mCompositeSubscription.add(mModel.getDynamicSetting(ApiConstant.INFO_WAN_DYNAMIC, TypeConstant.INFO_WAN_DYN)
                .subscribe(strings -> {

                    mView.setIpSettings(strings.get(0), strings.get(1), strings.get(2), strings.get(3), "");

                }, throwable -> Log.e("INTERNET-dyn-error", throwable.getMessage())));
    }

    @Override
    public void getStaticSetting() {
        mCompositeSubscription.add(mModel.getStaticSetting(ApiConstant.INFO_WAN_STATIC, TypeConstant.INFO_WAN_STAT)
                .subscribe(strings -> {

                    mView.setIpSettings(strings.get(0), strings.get(1), strings.get(2), strings.get(3), strings.get(4));

                }, throwable -> Log.e("INTERNET-stat-error", throwable.getMessage())));
    }

    @Override
    public void getPptpSetting() {
        mCompositeSubscription.add(mModel.getPptpSetting(ApiConstant.INFO_WAN_PPTP, TypeConstant.INFO_WAN_PPTP)
                .subscribe(strings -> {
                    mView.setDataSettings(strings.get(1), strings.get(2), strings.get(0));
                    mView.setIpSettings(strings.get(3), strings.get(4), strings.get(5));

                }, throwable -> Log.e("INTERNET-pptp-error", throwable.getMessage())));
    }

    @Override
    public void getPppoeSetting() {
        mCompositeSubscription.add(mModel.getPppoeSetting(ApiConstant.INFO_WAN_PPPOE, TypeConstant.INFO_WAN_PPPOE)
                .subscribe(strings -> {

                    mView.setDataSettings(strings.get(0), strings.get(1));

                }, throwable -> Log.e("INTERNET-pppoe-error", throwable.getMessage())));
    }

    @Override
    public void setWanDynamic() {
        mCompositeSubscription.add(mModel.setWanDynamic(ApiConstant.INFO_WAN_DYNAMIC)
                .doOnRequest(request -> mView.showProgressDialog())
                .doOnUnsubscribe(() -> mView.hideProgressDialog())
                .subscribe(s -> {
                            if (s.contains(TypeConstant.WAN_DYNAMIC)) {
                                mView.showSuccessToast();
                                mView.navigateSettingMenu();
                            } else {
                                mView.showErrorToast();
                            }

                        },
                        throwable -> Log.e("INET-error-d", throwable.getMessage())));
    }

    @Override
    public void setWanStatic(String ip, String mask, String gateway, String dns1, String dns2) {
        mCompositeSubscription.add(mModel.setWanStatic(ApiConstant.INFO_WAN_STATIC, ip, mask, gateway, dns1, dns2)
                .doOnRequest(request -> mView.showProgressDialog())
                .doOnUnsubscribe(() -> mView.hideProgressDialog())
                .subscribe(s -> {
                            if (s.contains(TypeConstant.WAN_STATIC)) {
                                mView.showSuccessToast();
                                mView.navigateSettingMenu();
                            } else {
                                mView.showErrorToast();
                            }

                        },
                        throwable -> Log.e("INET-error-s", throwable.getMessage())));
    }

    @Override
    public void setWanPptp(String username, String pass, String server) {
        mCompositeSubscription.add(mModel.setWanPptp(ApiConstant.INFO_WAN_PPTP, username, pass, server)
                .doOnRequest(request -> mView.showProgressDialog())
                .doOnUnsubscribe(() -> mView.hideProgressDialog())
                .subscribe(s -> {
                            if (s.contains(TypeConstant.WAN_PPTP)) {
                                mView.showSuccessToast();
                                mView.navigateSettingMenu();
                            } else {
                                mView.showErrorToast();
                            }

                        },
                        throwable -> Log.e("INET-error-pt", throwable.getMessage())));
    }

    @Override
    public void setWanPppoe(String username, String pass) {
        mCompositeSubscription.add(mModel.setWanPppoe(ApiConstant.INFO_WAN_PPPOE, username, pass)
                .doOnRequest(request -> mView.showProgressDialog())
                .doOnUnsubscribe(() -> mView.hideProgressDialog())
                .subscribe(s -> {
                            if (s.contains(TypeConstant.WAN_PPPOE)) {
                                mView.showSuccessToast();
                                mView.navigateSettingMenu();
                            } else {
                                mView.showErrorToast();
                            }

                        },
                        throwable -> Log.e("INET-error-po", throwable.getMessage())));
    }

    @Override
    public void getWanType() {
        mCompositeSubscription.add(mModel.getWanType(ApiConstant.INFO_WAN_TYPE, TypeConstant.INFO_WAN_TYPE)
                .doOnRequest(request -> mView.showProgressDialog())
                .doOnUnsubscribe(() -> mView.hideProgressDialog())
                .subscribe(strings -> {
                    Log.e("TYPE", strings.get(0));
                    mView.setWanType(strings.get(0));
                }, throwable -> Log.e("INTERNET-type-error", throwable.getMessage())));
    }


}
