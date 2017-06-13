package sergey.zhuravel.tplinkman.ui.man;


import java.util.List;

import rx.Observable;
import sergey.zhuravel.tplinkman.model.ManRouter;
import sergey.zhuravel.tplinkman.model.RouterSession;

public interface ManContract {

    interface Model {
        void saveManRouter(ManRouter manRouter);

        Observable<List<ManRouter>> getManRouters();

        void removeManRouter(String id);


    }

    interface View {
        void addGroup(String groupName);

        void addChildToGroup(String groupName, List<RouterSession> list);

    }

    interface Presenter {
        void getManRouters();
        void onDestroy();
    }
}
