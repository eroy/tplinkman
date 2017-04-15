package sergey.zhuravel.tplinkman.ui.block;

import java.util.List;

import rx.Observable;
import sergey.zhuravel.tplinkman.model.Blocked;

public interface BlockContract {

    interface Model {
        Observable<List<String>> getInfoWifiFilter(String link, String type, int page);


    }

    interface View {
        void noBlockedTextAccessibility(boolean visible);

        void addBlockedList(List<Blocked> list);
    }

    interface Presenter {
        void onDestroy();

        void getWifiFilterInfo();

    }
}
