package sergey.zhuravel.tplinkman.ui.man;


import java.util.List;

import io.realm.RealmList;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;
import sergey.zhuravel.tplinkman.model.ManRouter;
import sergey.zhuravel.tplinkman.model.RouterSession;
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
    public void getManRouters() {
        mCompositeSubscription.add(mModel.getManRouters().subscribe(manRouters ->
                Observable
                        .from(manRouters)
                        .subscribe(manRouter -> {
                            mView.addGroup(manRouter.getGroupName());
                            mView.addChildToGroup(manRouter.getGroupName(), manRouter.getListSession());
                        })));
    }

    public void saveManRouters(String groupName, List<RouterSession> list) {
        RealmList<RouterSession> realmList = new RealmList<>();
        realmList.addAll(list);
        ManRouter manRouter = new ManRouter(groupName, realmList);
        mModel.saveManRouter(manRouter);
    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel = null;

        RxUtils.unsubscribeIfNotNull(mCompositeSubscription);
    }

}
