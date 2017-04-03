package sergey.zhuravel.tplinkman.ui.main;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.api.SettingService;
import sergey.zhuravel.tplinkman.manager.DataManager;
import sergey.zhuravel.tplinkman.utils.LinkGenerate;
import sergey.zhuravel.tplinkman.utils.Utils;

public class MainModel implements MainContract.Model {
    private DataManager mDataManager;
    private SettingService mSettingService;

    public MainModel(DataManager dataManager) {
        this.mDataManager = dataManager;

        mSettingService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp(), dataManager.getKey())).getSettingService();
    }

    @Override
    public Observable<String> setLogout(String link) {
        String referer = LinkGenerate.referer(mDataManager.getIp(), mDataManager.getKey(), link);
        String cookie = LinkGenerate.cookie(mDataManager.getUsername(), mDataManager.getPass());

        return mSettingService.setLogout(cookie, referer)
                .retry(3)
                .flatMap(Utils::replaceResponseToText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
