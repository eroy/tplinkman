package sergey.zhuravel.tplinkman.ui.input;


import java.util.List;

import rx.Observable;
import sergey.zhuravel.tplinkman.model.RouterSession;

public interface InputContract {
    interface Model {
        Observable<String> getKey(String ip, String username, String password);

        Observable<String> inputValidate(String ip, String username, String password, String key);

        Observable<String> inputValidateOld(String ip, String username, String password);

        void savePreference(String ip, String key, String username, String password);

        void savePreference(String ip, String username, String password);

        void saveSession(RouterSession routerSession);

        Observable<List<RouterSession>> getSessions();

        void deleteSession(String ip);
    }

    interface View {
        void showProgressDialog();

        void hideProgressDialog();

        void navigateToMainActivity();


        void showDialogErrorInput();

        void showDialogRepeat();

        void showDialogHostUnreachable();

        void addSession(List<RouterSession> routerSessions);

        void sessionHistoryAccessibility(boolean visible);
    }

    interface Presenter {
        void validateAndInput(String ip, String username, String password);

        void saveSession(String ip, String username, String password, String nameConnection);
        void onDestroy();

        void getSession();
    }

}
