package sergey.zhuravel.tplinkman.ui.info;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import sergey.zhuravel.tplinkman.model.Info;



public interface InfoContract {
    interface Model {
        Observable<List<String>> getInfoFirmware(String cookie, String referer,String type);

    }

    interface View {

        void setInfoFirmware(List<String> lists);

    }

    interface Presenter {
        void getFirmwareInfo(String cookie, String referer);
    }
}
