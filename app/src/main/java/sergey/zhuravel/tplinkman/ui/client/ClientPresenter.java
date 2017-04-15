package sergey.zhuravel.tplinkman.ui.client;


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
import sergey.zhuravel.tplinkman.model.Client;
import sergey.zhuravel.tplinkman.utils.RxUtils;
import sergey.zhuravel.tplinkman.utils.Utils;

public class ClientPresenter implements ClientContract.Presenter {

    private ClientContract.View mView;
    private ClientContract.Model mModel;
    private CompositeSubscription mCompositeSubscription;
    private int mCurrentPage = 1;
    private List<Client> mClientList;

    public ClientPresenter(ClientContract.View mView, ClientContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
        mClientList = new ArrayList<>();
        mCompositeSubscription = RxUtils.getNewCompositeSubIfUnsubscribed(mCompositeSubscription);
    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel = null;
        RxUtils.unsubscribeIfNotNull(mCompositeSubscription);

    }


    @Override
    public void getWifiStationInfo() {
        mCompositeSubscription.add(mModel.getInfoWifiStation(ApiConstant.INFO_WIFI_STATION, TypeConstant.INFO_WIFI_STATION)
                .subscribe(strings -> {
                    if (Integer.parseInt(strings.get(1)) == mCurrentPage) {
                        getWifiStationNameInfo(strings.get(0));


                        ++mCurrentPage;
                        getWifiStationInfo();
                    }
                }, throwable -> Log.e("SERJ", throwable.getMessage())));
    }

    @Override
    public void getWifiStationNameInfo(String strWifiStation) {
        mCompositeSubscription.add(mModel.getInfoWifiStationName(ApiConstant.INFO_WIFI_STATION_NAME, TypeConstant.INFO_WIFI_STATION_NAME)
                .subscribe(strings -> {

                    List<Client> cl = getClient(strings.get(0), strWifiStation);

                    mClientList.addAll(cl);

                    mView.addClientToList(cl);


                }, throwable -> Log.e("SERJ", throwable.getMessage())));
    }

    @Override
    public void updateClientList() {
        mCompositeSubscription.add(Observable.interval(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> {
                            mView.clearClientList();
                            mCurrentPage = 1;
                            getWifiStationInfo();

                        },
                        e -> Log.e("TIMER", e.getMessage())));
    }

    @Override
    public void blockClient(int position) {
        Client client = mClientList.get(position);
        Log.e("BLOCK", client.getMac());


    }

    private List<Client> getClient(String strWifiStationName, String strWifiStation) {
        List<Client> clients = new ArrayList<>();

        for (String mac : Utils.getMacByString(strWifiStation)) {

            String[] response = strWifiStationName.split(mac);
            List<String> listName = new ArrayList<>();
            List<String> listIp = new ArrayList<>();
            Pattern pattern = Pattern.compile("\"(.*?)\"");
            Matcher matcherName = pattern.matcher(response[0]);
            while (matcherName.find()) {
                listName.add(matcherName.group(0).replace("\"", ""));
            }
            Pattern patternIp = Pattern.compile(",(.*?),");
            Matcher matcherIp = patternIp.matcher(response[1].replace("\"", ""));
            while (matcherIp.find()) {
                listIp.add(matcherIp.group(0).replace(",", "").replace(" ", ""));
            }

            Client client = new Client();
            client.setName(listName.get(listName.size() - 1));
            client.setIp(listIp.get(0));
            client.setMac(mac);
            client.setDownload(getPackageByMac(strWifiStation, mac).get(0));
            client.setUpload(getPackageByMac(strWifiStation, mac).get(1));
            clients.add(client);
        }
        return clients;
    }


    private List<String> getPackageByMac(String str, String mac) {
        String[] response = str.split(mac);
        String[] response1 = response[1].split(",");

        List<String> result = new ArrayList<>();
        result.add(response1[2].replace(" ", ""));
        result.add(response1[3].replace(" ", ""));
        return result;
    }


}
