package sergey.zhuravel.tplinkman.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class ManRouter extends RealmObject {

    @PrimaryKey
    private String groupName;
    private RealmList<ManSession> manSessions;

    public ManRouter() {
    }

    public ManRouter(String groupName) {
        this.groupName = groupName;
    }

    public ManRouter(String groupName, RealmList<ManSession> manSessions) {
        this.groupName = groupName;
        this.manSessions = manSessions;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public RealmList<ManSession> getManSessions() {
        return manSessions;
    }

    public void setManSessions(RealmList<ManSession> manSessions) {
        this.manSessions = manSessions;
    }
}
