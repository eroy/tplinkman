package sergey.zhuravel.tplinkman.ui.start;


import android.util.Log;

import rx.subscriptions.CompositeSubscription;
import sergey.zhuravel.tplinkman.utils.RxUtils;

public class StartPresenter implements StartContract.Presenter {
    private StartContract.View mView;
    private StartContract.Model mModel;
    private CompositeSubscription mCompositeSubscription;

    public StartPresenter(StartContract.View mView, StartContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;

        mCompositeSubscription = RxUtils.getNewCompositeSubIfUnsubscribed(mCompositeSubscription);
    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel = null;
        RxUtils.unsubscribeIfNotNull(mCompositeSubscription);
    }


    @Override
    public void validateAndInput(String ip, String username, String password) {
        mCompositeSubscription.add(mModel.getKey(ip, username, password)
                .subscribe(s -> {
                            Log.e("SERJ", s);
                        },
                        throwable -> Log.e("SERJ-error", throwable.getMessage())));
    }

}
