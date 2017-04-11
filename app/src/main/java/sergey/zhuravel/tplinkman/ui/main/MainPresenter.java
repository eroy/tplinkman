package sergey.zhuravel.tplinkman.ui.main;

import android.util.Log;

import rx.subscriptions.CompositeSubscription;
import sergey.zhuravel.tplinkman.constant.ApiConstant;
import sergey.zhuravel.tplinkman.utils.RxUtils;


public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private MainContract.Model mModel;
    private CompositeSubscription mCompositeSubscription;

    public MainPresenter(MainContract.View mView, MainContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;

        mCompositeSubscription = RxUtils.getNewCompositeSubIfUnsubscribed(mCompositeSubscription);
    }

    @Override
    public void onDestroy() {
        setLogout();
        mModel.clearSaveAll();

        mView = null;
        mModel = null;
        RxUtils.unsubscribeIfNotNull(mCompositeSubscription);
    }

    private void setLogout() {
        mCompositeSubscription.add(mModel.setLogout(ApiConstant.LOGOUT)
                .filter(s -> s.contains("logout"))
                .subscribe(s -> mView.setToast("Logout successful"),
                        throwable -> Log.e("SERJ", throwable.getMessage())));
    }

}
