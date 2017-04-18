package sergey.zhuravel.tplinkman.ui.main;


import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.api.SettingOldService;
import sergey.zhuravel.tplinkman.api.SettingService;
import sergey.zhuravel.tplinkman.manager.DataManager;
import sergey.zhuravel.tplinkman.utils.LinkGenerate;
import sergey.zhuravel.tplinkman.utils.Utils;

public class MainModel implements MainContract.Model {
    private DataManager mDataManager;
    private SettingService mSettingService;
    private SettingOldService mSettingOldService;
    private String mCookie;
    private String mReferer;


    public MainModel(DataManager dataManager) {
        this.mDataManager = dataManager;

        mSettingService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp(), dataManager.getKey())).getSettingService();
        mSettingOldService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp())).getSettingOldService();

        if (dataManager.getKey() != null && dataManager.getKey().length() > 0) {
            mReferer = LinkGenerate.refererNew(mDataManager.getIp(), mDataManager.getKey());
            mCookie = LinkGenerate.cookie(mDataManager.getUsername(), mDataManager.getPass());
        } else {
            mReferer = LinkGenerate.referer(mDataManager.getIp());
            mCookie = LinkGenerate.authorization(mDataManager.getUsername(), mDataManager.getPass());
        }
    }

    private Observable<Response<ResponseBody>> getObsLogout(String referer) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
            Log.e("setloout", "new - " + referer);
            return mSettingService.setLogout(mCookie, referer);
        } else {
            Log.e("setloout", "old - " + referer);
            return mSettingOldService.setLogout(mCookie, referer);
        }
    }

    @Override
    public Observable<String> setLogout(String link) {
        String referer = mReferer + link;

        return getObsLogout(referer)
                .retry(3)
                .flatMap(Utils::replaceResponseToCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void clearSaveAll() {
        mDataManager.clearAll();
    }
}
