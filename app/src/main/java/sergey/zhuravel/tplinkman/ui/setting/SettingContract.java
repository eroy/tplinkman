package sergey.zhuravel.tplinkman.ui.setting;


import rx.Observable;

public interface SettingContract {
    interface Model {
        Observable<String> setRebootRouter(String link, String refererLink);


    }

    interface View {
        void navigateToStartActivity();

        void showRebootSuccessToast();

        void showRebootErrorToast();

    }

    interface Presenter {
        void setRebootRouter();
        void onDestroy();

    }
}
