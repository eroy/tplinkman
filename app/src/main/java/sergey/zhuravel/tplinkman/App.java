package sergey.zhuravel.tplinkman;

import android.app.Application;
import android.content.Context;

import sergey.zhuravel.tplinkman.api.ApiManager;
import sergey.zhuravel.tplinkman.manager.DataManager;

public class App extends Application {
    private static ApiManager sApiManager;
    private static DataManager sDataManager;

    public static ApiManager getApiManager(String baseUrl) {
        if (sApiManager == null) {
            sApiManager = new ApiManager(baseUrl);
        }
        return sApiManager;
    }

    public static DataManager getDataManager(Context context){
        if (sDataManager == null) {
            sDataManager = new DataManager(context);
        }
        return sDataManager;
    }


}
