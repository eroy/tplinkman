package sergey.zhuravel.tplinkman.ui.man;


import io.realm.RealmList;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;
import sergey.zhuravel.tplinkman.model.ManRouter;
import sergey.zhuravel.tplinkman.model.ManSession;
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
                            mView.addChildToGroup(manRouter);
                        })));
    }

    @Override
    public void saveManRouters(String groupName, ManSession routerSession) {
        ManRouter manRouter = mModel.getManRouter(groupName);
        if (manRouter != null) {
            if (routerSession != null) {
                RealmList<ManSession> manSessionList = new RealmList<>();
                manSessionList.addAll(manRouter.getManSessions());
                manSessionList.add(routerSession);
                ManRouter man = new ManRouter();
                man.setGroupName(manRouter.getGroupName());
                man.setManSessions(manSessionList);
                mModel.saveManRouter(man);
            }

        } else {
            mModel.saveManRouter(new ManRouter(groupName));
        }

    }


    @Override
    public void onDestroy() {
        mView = null;
        mModel = null;

        RxUtils.unsubscribeIfNotNull(mCompositeSubscription);
    }

}
