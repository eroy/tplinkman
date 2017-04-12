package sergey.zhuravel.tplinkman.ui.internet;


public interface InternetContract {
    interface Model {


    }

    interface View {
        void showProgressDialog();

        void hideProgressDialog();

    }

    interface Presenter {
        void onDestroy();

    }
}
