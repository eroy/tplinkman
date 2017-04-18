package sergey.zhuravel.tplinkman.ui.info;


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


public class InfoModel implements InfoContract.Model {
    private InfoService mInfoService;
    private DataManager mDataManager;
    private String mCookie;
    private String mReferer;
    private InfoOldService mInfoOldService;


    public InfoModel(DataManager dataManager) {
        mCookie = null;
        mReferer = null;

        this.mDataManager = dataManager;

        mInfoService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp(), dataManager.getKey())).getInfoService();
        mInfoOldService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp())).getInfoOldService();


        if (dataManager.getKey() != null && dataManager.getKey().length() > 0) {
            mReferer = LinkGenerate.refererNew(mDataManager.getIp(), mDataManager.getKey());
            mCookie = LinkGenerate.cookie(mDataManager.getUsername(), mDataManager.getPass());
        } else {
            mReferer = LinkGenerate.referer(mDataManager.getIp());
            mCookie = LinkGenerate.authorization(mDataManager.getUsername(), mDataManager.getPass());
        }

    }

    private Observable<Response<ResponseBody>> getObsFirm(String referer) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
            return mInfoService.getInfoFirmware(mCookie, referer);
        } else {
            return mInfoOldService.getInfoFirmware(mCookie, referer);
        }
    }

    private Observable<Response<ResponseBody>> getObsMac(String referer) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
            return mInfoService.getInfoMac(mCookie, referer);
        } else {
            return mInfoOldService.getInfoMac(mCookie, referer);
        }
    }


    @Override
    public Observable<List<String>> getInfoFirmware(String link, String type) {
        String referer = mReferer + link;

        return getObsFirm(referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public Observable<List<String>> getInfoMac(String link, String type) {
        String referer = mReferer + link;

        return getObsMac(referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<ResponseBody>> getObsWifiName(String referer) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
            return mInfoService.getInfoWifiName(mCookie, referer);
        } else {
            return mInfoOldService.getInfoWifiName(mCookie, referer);
        }
    }


    @Override
    public Observable<List<String>> getInfoWifiName(String link, String type) {
        String referer = mReferer + link;


        return getObsWifiName(referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<ResponseBody>> getObsWifiPass(String referer) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
            return mInfoService.getInfoWifiPass(mCookie, referer);
        } else {
            return mInfoOldService.getInfoWifiPass(mCookie, referer);
        }
    }

    @Override
    public Observable<List<String>> getInfoWifiPass(String link, String type) {
        String referer = mReferer + link;

        return getObsWifiPass(referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<ResponseBody>> getObsStatus(String referer) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
            return mInfoService.getInfoStatus(mCookie, referer);
        } else {
            return mInfoOldService.getInfoStatus(mCookie, referer);
        }
    }

    @Override
    public Observable<List<String>> getInfoStatus(String link, String type) {
        String referer = mReferer + link;

        return getObsStatus(referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<ResponseBody>> getObsWifiStation(String referer) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
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

}
