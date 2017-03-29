package sergey.zhuravel.tplinkman.ui.info;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import sergey.zhuravel.tplinkman.model.Info;



public interface InfoContract {
    interface Model {
        Observable<List<String>> getInfoFirmware(String link, String type);
        Observable<List<String>> getInfoMac(String link, String type);
        Observable<List<String>> getInfoWifiName(String link, String type);
        Observable<List<String>> getInfoWifiPass(String link, String type);

    }

    interface View {

        void setInfoMac(String mac);
        void setInfoWifiName(String ssid);
        void setInfoWifiPass(String pass);
        void setInfoFirmware(List<String> lists);

    }

    interface Presenter {
        void getMacInfo();
        void getFirmwareInfo();

        void getWifiNameInfo();
        void getWifiPassInfo();
    }
}
