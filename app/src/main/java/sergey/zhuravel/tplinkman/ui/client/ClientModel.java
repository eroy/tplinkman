package sergey.zhuravel.tplinkman.ui.client;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.api.InfoOldService;
import sergey.zhuravel.tplinkman.api.InfoService;
import sergey.zhuravel.tplinkman.manager.DataManager;
import sergey.zhuravel.tplinkman.utils.LinkGenerate;
import sergey.zhuravel.tplinkman.utils.Utils;

public class ClientModel implements ClientContract.Model {
    private InfoService mInfoService;
    private DataManager mDataManager;
    private String mCookie;
    private String mReferer;
    private InfoOldService mInfoOldService;

    public ClientModel(DataManager dataManager) {
        mCookie = null;
        mReferer = null;

        this.mDataManager = dataManager;

        mInfoService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp(), dataManager.getKey())).getInfoService();
        mInfoOldService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp())).getInfoOldService();


        if (dataManager.getKey().length() > 0) {
            mReferer = LinkGenerate.refererNew(mDataManager.getIp(), mDataManager.getKey());
            mCookie = LinkGenerate.cookie(mDataManager.getUsername(), mDataManager.getPass());
        } else {
            mReferer = LinkGenerate.referer(mDataManager.getIp());
            mCookie = LinkGenerate.authorization(mDataManager.getUsername(), mDataManager.getPass());
        }
    }

    private Observable<Response<ResponseBody>> getObsWifiStation(String referer) {
        if (mDataManager.getKey().length() > 0) {
            return mInfoService.getInfoWifiStation(mCookie, referer);
        } else {
            return mInfoOldService.getInfoWifiStation(mCookie, referer);
        }
    }

    @Override
    public Observable<List<String>> getInfoWifiStation(String link, String type) {
        String referer = mReferer + link;

        return getObsWifiStation(referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<ResponseBody>> getObsWifiStationName(String referer) {
        if (mDataManager.getKey().length() > 0) {
            return mInfoService.getInfoWifiStationName(mCookie, referer);
        } else {
            return mInfoOldService.getInfoWifiStationName(mCookie, referer);
        }
    }

    @Override
    public Observable<List<String>> getInfoWifiStationName(String link, String type) {
        String referer = mReferer + link;

        return getObsWifiStationName(referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
