package sergey.zhuravel.tplinkman.ui.man;


import java.util.List;

import rx.Observable;
import sergey.zhuravel.tplinkman.model.ManRouter;
import sergey.zhuravel.tplinkman.model.ManSession;

public interface ManContract {

    interface Model {
        void saveManRouter(ManRouter manRouter);

        Observable<List<ManRouter>> getManRouters();

        void removeManRouter(String groupName);

        ManRouter getManRouter(String groupName);


    }

    interface View {
        void addGroup(String groupName);

        void addChildToGroup(ManRouter manRouter);

        void showTextNoGroupAccessibility();
    }

    interface Presenter {
        void getManRouters();
        void onDestroy();

        void saveManRouters(String groupName, ManSession routerSession);

        ManRouter getManRouterByName(String groupName);

        void removeManRouter(String groupName);

        void removeManSession(String groupName, ManSession routerSession);
    }
}
