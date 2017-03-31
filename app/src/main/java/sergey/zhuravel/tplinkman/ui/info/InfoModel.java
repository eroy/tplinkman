package sergey.zhuravel.tplinkman.ui.info;


import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.api.InfoService;
import sergey.zhuravel.tplinkman.manager.DataManager;
import sergey.zhuravel.tplinkman.utils.LinkGenerate;
import sergey.zhuravel.tplinkman.utils.Utils;


public class InfoModel implements InfoContract.Model {
    private InfoService mInfoService;
    private DataManager mDataManager;

    public InfoModel(DataManager dataManager) {
        this.mDataManager = dataManager;

        mInfoService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp(), dataManager.getKey())).getInfoService();

    }

    @Override
    public Observable<List<String>> getInfoFirmware(String link, String type) {
        String referer = LinkGenerate.referer(mDataManager.getIp(), mDataManager.getKey(), link);
        String cookie = LinkGenerate.cookie(mDataManager.getUsername(), mDataManager.getPass());

        return mInfoService.getInfoFirmware(cookie, referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservable(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<String>> getInfoMac(String link, String type) {
        String referer = LinkGenerate.referer(mDataManager.getIp(), mDataManager.getKey(), link);
        String cookie = LinkGenerate.cookie(mDataManager.getUsername(), mDataManager.getPass());

        return mInfoService.getInfoMac(cookie, referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservable(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<String>> getInfoWifiName(String link, String type) {
        String referer = LinkGenerate.referer(mDataManager.getIp(), mDataManager.getKey(), link);
        String cookie = LinkGenerate.cookie(mDataManager.getUsername(), mDataManager.getPass());

        return mInfoService.getInfoWifiName(cookie, referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservable(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<String>> getInfoWifiPass(String link, String type) {
        String referer = LinkGenerate.referer(mDataManager.getIp(), mDataManager.getKey(), link);
        String cookie = LinkGenerate.cookie(mDataManager.getUsername(), mDataManager.getPass());

        return mInfoService.getInfoWifiPass(cookie, referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservable(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<String>> getInfoStatus(String link, String type) {
        String referer = LinkGenerate.referer(mDataManager.getIp(), mDataManager.getKey(), link);
        String cookie = LinkGenerate.cookie(mDataManager.getUsername(), mDataManager.getPass());

        return mInfoService.getInfoStatus(cookie, referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservable(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
