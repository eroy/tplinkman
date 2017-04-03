package sergey.zhuravel.tplinkman.ui.setting;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.api.SettingService;
import sergey.zhuravel.tplinkman.manager.DataManager;
import sergey.zhuravel.tplinkman.utils.LinkGenerate;
import sergey.zhuravel.tplinkman.utils.Utils;

public class SettingModel implements SettingContract.Model {
    private DataManager mDataManager;
    private SettingService mSettingService;

    public SettingModel(DataManager dataManager) {
        this.mDataManager = dataManager;

        mSettingService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp(), dataManager.getKey())).getSettingService();
    }

    @Override
    public Observable<String> setRebootRouter(String link, String refererLink) {
        String referer = LinkGenerate.referer(mDataManager.getIp(), mDataManager.getKey(), refererLink);
        String cookie = LinkGenerate.cookie(mDataManager.getUsername(), mDataManager.getPass());

        return mSettingService.setReboot(cookie, referer)
                .retry(3)
                .flatMap(Utils::replaceResponseToText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
