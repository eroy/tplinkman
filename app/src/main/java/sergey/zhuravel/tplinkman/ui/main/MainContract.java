package sergey.zhuravel.tplinkman.ui.main;


import rx.Observable;

public interface MainContract {

    interface Model {
        Observable<String> setLogout(String link);

        void clearSaveAll();
    }

    interface View {
        void setToast(String message);

    }

    interface Presenter {
        void onDestroy();
    }

}
