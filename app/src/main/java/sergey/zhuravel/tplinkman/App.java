package sergey.zhuravel.tplinkman;

import android.app.Application;

import sergey.zhuravel.tplinkman.api.ApiManager;

public class App extends Application {
    private static ApiManager sApiManager;

    public static ApiManager getApiManager(String baseUrl) {
        if (sApiManager == null) {
            sApiManager = new ApiManager(baseUrl);
        }
        return sApiManager;
    }


}
