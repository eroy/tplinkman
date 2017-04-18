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
        mModel.clearSaveAll();
        mView.navigateToStartActivity();

        mView = null;
        mModel = null;
        RxUtils.unsubscribeIfNotNull(mCompositeSubscription);
        Log.e("SERJ", "MainPresenter -- onDestroy");
    }


    @Override
    public void setLogout() {
        Log.e("SERJ", "setlogout");
        mCompositeSubscription.add(mModel.setLogout(ApiConstant.LOGOUT)
                .subscribe(s -> {
                            Log.e("LOGOUT", "logout succes");

                            onDestroy();
                        },
                        throwable -> Log.e("LOGOUT-error", throwable.getMessage())));
    }

}
