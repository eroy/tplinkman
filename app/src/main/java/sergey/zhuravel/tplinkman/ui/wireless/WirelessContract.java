package sergey.zhuravel.tplinkman.ui.wireless;


import java.util.List;

import rx.Observable;

public interface WirelessContract {
    interface Model {
        Observable<List<String>> getInfoWifiName(String link, String type);
        Observable<List<String>> getInfoWifiPass(String link, String type);

    }

    interface View {
        void setInfoWifiParameters(String ssid,String chanel,String regional);
        void setInfoWifiSecurity(String password);
        void statusSecurityAccessibility(boolean enable);
    }

    interface Presenter {
        void onDestroy();

        void setWifiParameters();
        void setWifiSecurity();


    }

}
