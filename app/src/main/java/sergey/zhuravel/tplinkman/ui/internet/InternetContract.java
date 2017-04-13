package sergey.zhuravel.tplinkman.ui.internet;


import java.util.List;

import rx.Observable;

public interface InternetContract {
    interface Model {
        Observable<List<String>> getWanType(String link, String type);

        Observable<List<String>> getDynamicSetting(String link, String type);

        Observable<List<String>> getStaticSetting(String link, String type);

        Observable<List<String>> getPptpSetting(String link, String type);

        Observable<List<String>> getPppoeSetting(String link, String type);

        Observable<String> setWanDynamic(String linkReferer);

        Observable<String> setWanStatic(String linkReferer, String ip, String mask, String gateway, String dns1, String dns2);

        Observable<String> setWanPptp(String linkReferer, String username, String pass, String server);

        Observable<String> setWanPppoe(String linkReferer, String username, String pass);
    }

    interface View {
        void showProgressDialog();

        void hideProgressDialog();

        void setIpSettings(String ip, String mask, String gateway, String dns1, String dns2);

        void setIpSettings(String ip, String mask, String gateway);

        void setDataSettings(String username, String password, String vpn);

        void setDataSettings(String username, String password);

        void setWanType(String type);

        void showSuccessToast();

        void showErrorToast();

        void navigateSettingMenu();
    }

    interface Presenter {
        void onDestroy();

        void getDynamicSetting();

        void getStaticSetting();

        void getPptpSetting();

        void getPppoeSetting();

        void setWanDynamic();

        void setWanStatic(String ip, String mask, String gateway, String dns1, String dns2);

        void setWanPptp(String username, String pass, String server);

        void setWanPppoe(String username, String pass);

        void getWanType();
    }
}
