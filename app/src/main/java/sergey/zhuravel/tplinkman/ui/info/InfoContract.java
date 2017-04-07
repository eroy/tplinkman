package sergey.zhuravel.tplinkman.ui.info;

import java.util.List;

import rx.Observable;



public interface InfoContract {
    interface Model {
        Observable<List<String>> getInfoFirmware(String link, String type);
        Observable<List<String>> getInfoMac(String link, String type);
        Observable<List<String>> getInfoWifiName(String link, String type);
        Observable<List<String>> getInfoWifiPass(String link, String type);
        Observable<List<String>> getInfoStatus(String link, String type);

        Observable<List<String>> getInfoWifiStation(String link, String type);
    }

    interface View {

        void setInfoMac(String mac);
        void setInfoWifiName(String ssid);
        void setInfoWifiPass(String pass);
        void setInfoFirmware(List<String> lists);

        void setInfoDownload(String download);
        void setInfoUpload(String upload);
    }

    interface Presenter {
        void onDestroy();
        void getMacInfo();
        void getFirmwareInfo();

        void getWifiNameInfo();
        void getWifiPassInfo();

        void getStatusInfo();

        void updateTraffic();

        void getWifiStationInfo();
    }
}
