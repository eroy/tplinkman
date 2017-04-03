package sergey.zhuravel.tplinkman.ui.info;


import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import sergey.zhuravel.tplinkman.constant.ApiConstant;
import sergey.zhuravel.tplinkman.constant.TypeConstant;
import sergey.zhuravel.tplinkman.utils.RxUtils;

public class InfoPresenter implements InfoContract.Presenter {
    private InfoContract.View mView;
    private InfoContract.Model mModel;
    private CompositeSubscription mCompositeSubscription;

    public InfoPresenter(InfoContract.View mView, InfoContract.Model mModel) {
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
    public void getFirmwareInfo() {
        mCompositeSubscription.add(mModel.getInfoFirmware(ApiConstant.INFO_FIRMWARE, TypeConstant.INFO_FIRMWARE)
                .subscribe(strings -> {

                    mView.setInfoFirmware(strings);

                }, throwable -> Log.e("SERJ", throwable.getMessage())));
    }


    @Override
    public void getMacInfo() {
        mCompositeSubscription.add(mModel.getInfoMac(ApiConstant.INFO_MAC, TypeConstant.INFO_MAC_WAN)
                .subscribe(strings -> {

                    mView.setInfoMac(strings.get(0));

                }, throwable -> Log.e("SERJ", throwable.getMessage())));
    }

    @Override
    public void getWifiNameInfo() {
        mCompositeSubscription.add(mModel.getInfoWifiName(ApiConstant.INFO_WIFI_NAME, TypeConstant.INFO_WIFI)
                .subscribe(strings -> {

                    mView.setInfoWifiName(strings.get(0));

                }, throwable -> Log.e("SERJ", throwable.getMessage())));
    }

    @Override
    public void getWifiPassInfo() {
        mCompositeSubscription.add(mModel.getInfoWifiPass(ApiConstant.INFO_WIFI_PASS, TypeConstant.INFO_WIFI_SEC)
                .subscribe(strings -> {

                    mView.setInfoWifiPass(strings.get(1).substring(1));

                }, throwable -> Log.e("SERJ", throwable.getMessage())));
    }

    @Override
    public void getStatusInfo() {
        mCompositeSubscription.add(mModel.getInfoStatus(ApiConstant.INFO_STATUS, TypeConstant.INFO_STATUS)
                .subscribe(strings -> {

                    mView.setInfoUpload(strings.get(0));
                    mView.setInfoDownload(strings.get(1));

                }, throwable -> Log.e("SERJ", throwable.getMessage())));
    }

    @Override
    public void updateTraffic() {
        mCompositeSubscription.add(Observable.interval(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> getStatusInfo(),
                        e -> Log.e("TIMER", e.getMessage())));
    }

}
