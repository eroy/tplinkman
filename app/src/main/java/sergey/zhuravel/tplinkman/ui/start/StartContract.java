package sergey.zhuravel.tplinkman.ui.start;


import rx.Observable;

public interface StartContract {
    interface Model {
        Observable<String> getKey(String ip, String username, String password);

        Observable<String> inputValidate(String ip, String username, String password, String key);

    }

    interface View {
        void showProgressDialog();

        void hideProgressDialog();

    }

    interface Presenter {
        void validateAndInput(String ip, String username, String password);

        void onDestroy();
    }

}
