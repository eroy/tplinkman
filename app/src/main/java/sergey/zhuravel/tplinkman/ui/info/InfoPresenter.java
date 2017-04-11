package sergey.zhuravel.tplinkman.ui.info;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

                }, throwable -> Log.e("SERJ-error-status-info", throwable.getMessage())));
    }

    @Override
    public void updateTraffic() {
        mCompositeSubscription.add(Observable.interval(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> getStatusInfo(),
                        e -> Log.e("TIMER", e.getMessage())));
    }

    @Override
    public void getWifiStationInfo() {
        mCompositeSubscription.add(mModel.getInfoWifiStation(ApiConstant.INFO_WIFI_STATION, TypeConstant.INFO_WIFI_STATION)
                .subscribe(strings -> {
                    parseWifiStation(strings.get(0));

                }, throwable -> Log.e("SERJ", throwable.getMessage())));
    }


    private void parseWifiStation(String str) {

        String[] a = parseStringMac(str);
        for (String b : a) {
            Log.e("PARSE", getPackageMac(str, b).toString());

        }


    }

    private List<String> getPackageMac(String str, String mac) {
        String[] response = str.split(mac);
        String[] response1 = response[1].split(",");

        List<String> result = new ArrayList<>();
        result.add(mac);
        result.add(response1[2].replace(" ", ""));
        result.add(response1[3].replace(" ", ""));
        return result;
    }

    private String[] parseStringMac(String str) {

        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(str);
        str = "";
        while (matcher.find()) {
            str += matcher.group(0);
        }
        str = str.replace("\"\"", ",");
        str = str.replace("\"", "");
        String[] list = str.split(",");

//        Log.e("PARSE", str.replace("\"\"",","));


        return list;
    }

}
