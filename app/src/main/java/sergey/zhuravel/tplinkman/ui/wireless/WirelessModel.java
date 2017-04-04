package sergey.zhuravel.tplinkman.ui.wireless;


import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.api.InfoService;
import sergey.zhuravel.tplinkman.api.SettingService;
import sergey.zhuravel.tplinkman.manager.DataManager;
import sergey.zhuravel.tplinkman.utils.LinkGenerate;
import sergey.zhuravel.tplinkman.utils.Utils;

public class WirelessModel implements WirelessContract.Model {

    private DataManager mDataManager;
    private SettingService mSettingService;
    private InfoService mInfoService;

    public WirelessModel(DataManager dataManager) {
        this.mDataManager = dataManager;

        mSettingService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp(), dataManager.getKey())).getSettingService();
        mInfoService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp(), dataManager.getKey())).getInfoService();
    }

    @Override
    public Observable<List<String>> getInfoWifiName(String link, String type) {
        String referer = LinkGenerate.referer(mDataManager.getIp(), mDataManager.getKey(), link);
        String cookie = LinkGenerate.cookie(mDataManager.getUsername(), mDataManager.getPass());

        return mInfoService.getInfoWifiName(cookie, referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<String>> getInfoWifiPass(String link, String type) {
        String referer = LinkGenerate.referer(mDataManager.getIp(), mDataManager.getKey(), link);
        String cookie = LinkGenerate.cookie(mDataManager.getUsername(), mDataManager.getPass());

        return mInfoService.getInfoWifiPass(cookie, referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
