package sergey.zhuravel.tplinkman;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import sergey.zhuravel.tplinkman.api.ApiManager;
import sergey.zhuravel.tplinkman.manager.DataManager;
import sergey.zhuravel.tplinkman.manager.RealmManager;

public class App extends Application {
    private static ApiManager sApiManager;
    private static DataManager sDataManager;
    private static RealmManager sRealmManager;

    public static ApiManager getApiManager(String baseUrl) {
        if (sApiManager != null) {
            sApiManager = null;
        }
        sApiManager = new ApiManager(baseUrl);

        return sApiManager;
    }

    public static RealmManager getRealmManager() {
        return sRealmManager;
    }

    public static DataManager getDataManager(Context context) {
        if (sDataManager == null) {
            sDataManager = new DataManager(context);
        }
        return sDataManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration configuration = new RealmConfiguration
                .Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);

        sRealmManager = new RealmManager();
    }
}
