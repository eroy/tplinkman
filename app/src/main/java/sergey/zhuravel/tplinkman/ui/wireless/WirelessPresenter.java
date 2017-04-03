package sergey.zhuravel.tplinkman.ui.wireless;


import rx.subscriptions.CompositeSubscription;
import sergey.zhuravel.tplinkman.utils.RxUtils;

public class WirelessPresenter implements WirelessContract.Presenter {
    private WirelessContract.View mView;
    private WirelessContract.Model mModel;
    private CompositeSubscription mCompositeSubscription;

    public WirelessPresenter(WirelessContract.View mView, WirelessContract.Model mModel) {
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
