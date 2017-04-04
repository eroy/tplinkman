package sergey.zhuravel.tplinkman.ui.wireless;


import java.util.List;

import rx.Observable;

public interface WirelessContract {
    interface Model {
        Observable<List<String>> getInfoWifiName(String link, String type);
        Observable<List<String>> getInfoWifiPass(String link, String type);

        Observable<String> setWifiMode(String link, String refererLink,String ap);
    }

    interface View {
        void setInfoWifiParameters(String ssid,String chanel,String regional);
        void setInfoWifiSecurity(String password);
        void statusSecurityAccessibility(boolean enable);
        void setCheckedSwitchMain(boolean checked);
        void setMainWifiVisible(boolean visible);

        void showToastMessage(int resId);

        void showProgressDialog();
        void hideProgressDialog();
    }

    interface Presenter {
        void onDestroy();

        void getWifiParameters();
        void getWifiSecurity();

        void setWifiMode(String mode);


    }

}
