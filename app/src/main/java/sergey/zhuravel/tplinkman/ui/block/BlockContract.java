package sergey.zhuravel.tplinkman.ui.block;

import java.util.List;

import rx.Observable;
import sergey.zhuravel.tplinkman.model.Blocked;

public interface BlockContract {

    interface Model {
        Observable<List<String>> getInfoWifiFilter(String link, String type, int page);

        Observable<String> setUnblockClient(String linkReferer, int id);

        Observable<String> setBlockClient(String linkReferer, String mac, String reason);
    }

    interface View {
        void noBlockedTextAccessibility(boolean visible);

        void addBlockedList(List<Blocked> list);

        void clearBlockedList();

        void showSuccessToast(String mac);

        void showErrorToast();

        void showConfirmUnBlockDialog(String mac, int position);

        void showNoValidateMacToast();

        String getMacDevice();
    }

    interface Presenter {
        void onDestroy();

        void setConfirmUnBlock(int position);
        void getWifiFilterInfo();

        void setUnBlockClient(String mac, int position);

        void setBlockClient(String mac, String reason);


    }
}
