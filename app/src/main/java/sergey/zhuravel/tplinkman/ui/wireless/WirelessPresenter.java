package sergey.zhuravel.tplinkman.ui.wireless;


import android.util.Log;

import rx.subscriptions.CompositeSubscription;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.constant.ApiConstant;
import sergey.zhuravel.tplinkman.constant.TypeConstant;
import sergey.zhuravel.tplinkman.utils.RxUtils;
import sergey.zhuravel.tplinkman.utils.Utils;

public class WirelessPresenter implements WirelessContract.Presenter {
    private WirelessContract.View mView;
    private WirelessContract.Model mModel;
    private CompositeSubscription mCompositeSubscription;

    public WirelessPresenter(WirelessContract.View mView, WirelessContract.Model mModel) {
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
    public void getWifiParameters() {
        mCompositeSubscription.add(mModel.getInfoWifiName(ApiConstant.INFO_WIFI_NAME, TypeConstant.INFO_WIFI)
                .subscribe(strings -> {
                    String ssid = strings.get(0);
                    String regional = strings.get(1);
                    String chanel = strings.get(2);
                    String isEnableWifi = strings.get(3);

                    if (isEnableWifi.equals("0")) {
                        mView.setCheckedSwitchMain(false);
                        mView.setMainWifiVisible(false);
                    } else {
                        mView.setCheckedSwitchMain(true);
                        mView.setMainWifiVisible(true);
                    }

                    if (chanel.equals("15")) {
                        chanel = "auto";
                    }
                    String region = Utils.getRegionName(regional);

                    mView.setInfoWifiParameters(ssid, chanel, region);

                }, throwable -> Log.e("SERJ", throwable.getMessage())));

    }

    @Override
    public void getWifiSecurity() {
        mCompositeSubscription.add(mModel.getInfoWifiPass(ApiConstant.INFO_WIFI_PASS, TypeConstant.INFO_WIFI_SEC)
                .subscribe(strings -> {

                    if (strings.get(0).equals("0")) {
                        mView.statusSecurityAccessibility(true);
                    } else {
                        mView.statusSecurityAccessibility(false);
                        mView.setInfoWifiSecurity(strings.get(1).substring(1));
                    }

                }, throwable -> Log.e("SERJ", throwable.getMessage())));
    }


    @Override
    public void setWifiMode(String mode) {
        mCompositeSubscription.add(mModel.setWifiMode(ApiConstant.WIFI_MODE_SETTING, ApiConstant.WIFI_SETTING_REFERER, mode)
                .doOnRequest(request -> mView.showProgressDialog())
                .doOnUnsubscribe(() -> mView.hideProgressDialog())
                .subscribe(s -> {

                            if (s.contains("wlanPara")) {
                                mView.showToastMessage(R.string.setting_apply);
                            } else {
                                mView.showToastMessage(R.string.error);
                            }
                        },
                        throwable -> Log.e("SERJ", throwable.getMessage())));
    }

}
