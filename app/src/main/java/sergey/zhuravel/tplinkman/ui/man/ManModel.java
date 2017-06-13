package sergey.zhuravel.tplinkman.ui.man;


import java.util.List;

import rx.Observable;
import sergey.zhuravel.tplinkman.manager.DataManager;
import sergey.zhuravel.tplinkman.manager.RealmManager;
import sergey.zhuravel.tplinkman.model.ManRouter;

public class ManModel implements ManContract.Model {

    private DataManager mDataManager;
    private RealmManager mRealmManager;

    public ManModel(DataManager mDataManager, RealmManager mRealmManager) {
        this.mDataManager = mDataManager;
        this.mRealmManager = mRealmManager;
    }

    @Override
    public void saveManRouter(ManRouter manRouter) {
        mRealmManager.saveManRouter(manRouter);
    }

    @Override
    public Observable<List<ManRouter>> getManRouters() {
        return mRealmManager.getManRoutersList();
    }

    @Override
    public void removeManRouter(String id) {
        mRealmManager.deleteManRouter(id);
    }

    @Override
    public ManRouter getManRouter(String groupName) {
        return mRealmManager.getManRouter(groupName);
    }

}
