package sergey.zhuravel.tplinkman.ui.info;


import android.util.Log;

import sergey.zhuravel.tplinkman.constant.ApiConstant;
import sergey.zhuravel.tplinkman.constant.TypeConstant;

public class InfoPresenter implements InfoContract.Presenter {
    private InfoContract.View mView;
    private InfoContract.Model mModel;

    public InfoPresenter(InfoContract.View mView, InfoContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void getFirmwareInfo() {
        mModel.getInfoFirmware(ApiConstant.INFO_FIRMWARE, TypeConstant.INFO_FIRMWARE)
                .subscribe(strings -> {

                    mView.setInfoFirmware(strings);

                }, throwable -> Log.e("SERJ", throwable.getMessage()));
    }



    @Override
    public void getMacInfo() {
        mModel.getInfoMac(ApiConstant.INFO_MAC, TypeConstant.INFO_MAC_WAN)
                .subscribe(strings -> {

                    mView.setInfoMac(strings.get(0));

                }, throwable -> Log.e("SERJ", throwable.getMessage()));
    }

    @Override
    public void getWifiNameInfo() {
        mModel.getInfoWifiName(ApiConstant.INFO_WIFI_NAME, TypeConstant.INFO_WIFI)
                .subscribe(strings -> {

                    mView.setInfoWifiName(strings.get(0));

                }, throwable -> Log.e("SERJ", throwable.getMessage()));
    }

    @Override
    public void getWifiPassInfo() {
        mModel.getInfoWifiPass(ApiConstant.INFO_WIFI_PASS, TypeConstant.INFO_WIFI_SEC)
                .subscribe(strings -> {

                    mView.setInfoWifiPass(strings.get(1).substring(1));

                }, throwable -> Log.e("SERJ", throwable.getMessage()));
    }

}
