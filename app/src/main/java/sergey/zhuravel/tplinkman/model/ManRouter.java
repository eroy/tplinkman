package sergey.zhuravel.tplinkman.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class ManRouter extends RealmObject {

    @PrimaryKey
    private String groupName;
    private RealmList<RouterSession> listSession;

    public ManRouter() {
    }

    public ManRouter(String groupName, RealmList<RouterSession> listSession) {
        this.groupName = groupName;
        this.listSession = listSession;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<RouterSession> getListSession() {
        return listSession;
    }

    public void setListSession(RealmList<RouterSession> listSession) {
        this.listSession = listSession;
    }

}
