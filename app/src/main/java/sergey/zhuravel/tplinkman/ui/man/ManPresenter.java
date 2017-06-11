package sergey.zhuravel.tplinkman.ui.man;


import rx.subscriptions.CompositeSubscription;
import sergey.zhuravel.tplinkman.utils.RxUtils;

public class ManPresenter implements ManContract.Presenter {
    private ManContract.View mView;
    private ManContract.Model mModel;

    private CompositeSubscription mCompositeSubscription;


    public ManPresenter(ManContract.View mView, ManContract.Model mModel) {
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
