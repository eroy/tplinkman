package sergey.zhuravel.tplinkman.ui.internet;

import rx.subscriptions.CompositeSubscription;
import sergey.zhuravel.tplinkman.utils.RxUtils;

public class InternetPresenter implements InternetContract.Presenter {
    private InternetContract.View mView;
    private InternetContract.Model mModel;
    private CompositeSubscription mCompositeSubscription;


    public InternetPresenter(InternetContract.View mView, InternetContract.Model mModel) {
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
}
