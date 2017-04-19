package sergey.zhuravel.tplinkman.manager;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import sergey.zhuravel.tplinkman.model.RouterSession;


public class RealmManager {

    private Realm mRealm;

    public RealmManager() {
        mRealm = Realm.getDefaultInstance();
    }

    public Realm getRealm() {
        return mRealm;
    }

    public void saveSession(RouterSession routerSession) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(routerSession));
    }

    public Observable<List<RouterSession>> getSessions() {
        return Observable.fromCallable(() -> mRealm.where(RouterSession.class).findAll());
    }

    public void deleteSession(String ip) {
        mRealm.executeTransaction(realm -> {
            RealmResults<RouterSession> results = realm.where(RouterSession.class).equalTo("ip", ip).findAll();
            results.deleteAllFromRealm();
        });
    }
}
