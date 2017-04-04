package sergey.zhuravel.tplinkman.ui.main;


import java.util.List;

import rx.Observable;

public interface MainContract {

    interface Model {
        Observable<String> setLogout(String link);

    }

    interface View {
        void setToast(String message);

    }

    interface Presenter {
        void onDestroy();
        void onStop();
    }

}
