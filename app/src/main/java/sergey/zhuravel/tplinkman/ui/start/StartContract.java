package sergey.zhuravel.tplinkman.ui.start;


import rx.Observable;

public interface StartContract {
    interface Model {
        Observable<String> getKey(String ip, String username, String password);

        Observable<String> inputValidate(String ip, String username, String password, String key);

        Observable<String> inputValidateOld(String ip, String username, String password);

        void savePreference(String ip, String key, String username, String password);

        void savePreference(String ip, String username, String password);
    }

    interface View {
        void showProgressDialog();

        void hideProgressDialog();

        void navigateToMainActivity();


        void showDialogErrorInput();

        void showDialogRepeat();

        void showDialogHostUnreachable();
    }

    interface Presenter {
        void validateAndInput(String ip, String username, String password);

        void onDestroy();
    }

}
