package sergey.zhuravel.tplinkman.manager;

import io.realm.Realm;


public class RealmManager {

    private Realm mRealm;

    public RealmManager() {
        mRealm = Realm.getDefaultInstance();
    }

    public Realm getRealm() {
        return mRealm;
    }

}
