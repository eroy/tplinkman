package sergey.zhuravel.tplinkman.ui.setting;

import android.util.Log;

import rx.subscriptions.CompositeSubscription;
import sergey.zhuravel.tplinkman.constant.ApiConstant;
import sergey.zhuravel.tplinkman.utils.RxUtils;


public class SettingPresenter implements SettingContract.Presenter {
    private SettingContract.View mView;
    private SettingContract.Model mModel;
    private CompositeSubscription mCompositeSubscription;

    public SettingPresenter(SettingContract.View mView, SettingContract.Model mModel) {
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
    public void setRebootRouter() {
        mCompositeSubscription.add(mModel.setRebootRouter(ApiConstant.REBOOT, ApiConstant.REBOOT_REFERER)
                .subscribe(s -> {
                            if (s.contains("Reboot")) {
                                mView.showRebootSuccessToast();
                                mView.navigateToStartActivity();
                            } else {
                                mView.showRebootErrorToast();
                            }
                        },
                        throwable -> Log.e("SERJ", throwable.getMessage())));
    }

}
