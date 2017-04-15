package sergey.zhuravel.tplinkman.ui.client;


import java.util.List;

import rx.Observable;
import sergey.zhuravel.tplinkman.model.Client;

public interface ClientContract {

    interface Model {
        Observable<List<String>> getInfoWifiStation(String link, String type);
        Observable<List<String>> getInfoWifiStationName(String link, String type);

        Observable<String> setBlockClient(String linkReferer, String mac, String reason);
    }

    interface View {
        void addClientToList(List<Client> list);

        void clearClientList();

        void showSuccessToast(String mac);

        void showErrorToast();

        void showNoValidateMacToast();

        String getMacDevice();
    }

    interface Presenter {
        void onDestroy();

        void getWifiStationInfo();

        void getWifiStationNameInfo(String strWifiStation);

        void updateClientList();

        void blockClient(int position);
    }

}
