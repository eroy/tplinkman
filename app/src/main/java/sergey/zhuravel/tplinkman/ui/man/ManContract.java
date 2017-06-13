package sergey.zhuravel.tplinkman.ui.man;


import java.util.List;

import rx.Observable;
import sergey.zhuravel.tplinkman.model.ManRouter;
import sergey.zhuravel.tplinkman.model.ManSession;

public interface ManContract {

    interface Model {
        void saveManRouter(ManRouter manRouter);

        Observable<List<ManRouter>> getManRouters();

        void removeManRouter(String id);

        ManRouter getManRouter(String groupName);


    }

    interface View {
        void addGroup(String groupName);

        void addChildToGroup(ManRouter manRouter);

    }

    interface Presenter {
        void getManRouters();
        void onDestroy();

        void saveManRouters(String groupName, ManSession routerSession);
    }
}
