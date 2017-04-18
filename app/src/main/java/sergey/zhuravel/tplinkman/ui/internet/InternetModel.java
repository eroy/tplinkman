package sergey.zhuravel.tplinkman.ui.internet;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.api.InfoOldService;
import sergey.zhuravel.tplinkman.api.InfoService;
import sergey.zhuravel.tplinkman.api.SettingOldService;
import sergey.zhuravel.tplinkman.api.SettingService;
import sergey.zhuravel.tplinkman.manager.DataManager;
import sergey.zhuravel.tplinkman.utils.LinkGenerate;
import sergey.zhuravel.tplinkman.utils.Utils;

public class InternetModel implements InternetContract.Model {
    private DataManager mDataManager;

    private SettingService mSettingService;
    private SettingOldService mSettingOldService;

    private InfoService mInfoService;
    private InfoOldService mInfoOldService;

    private String mCookie;
    private String mReferer;

    public InternetModel(DataManager dataManager) {
        this.mDataManager = dataManager;

        mSettingService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp(), dataManager.getKey())).getSettingService();
        mSettingOldService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp())).getSettingOldService();
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


    private Observable<Response<ResponseBody>> getObsWanType(String referer) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
            return mInfoService.getInfoWanType(mCookie, referer);
        } else {
            return mInfoOldService.getInfoWanType(mCookie, referer);
        }
    }

    @Override
    public Observable<List<String>> getWanType(String link, String type) {
        String referer = mReferer + link;

        return getObsWanType(referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private Observable<Response<ResponseBody>> getObsDynamic(String referer) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
            return mInfoService.getInfoWanDynamic(mCookie, referer);
        } else {
            return mInfoOldService.getInfoWanDynamic(mCookie, referer);
        }
    }

    @Override
    public Observable<List<String>> getDynamicSetting(String link, String type) {
        String referer = mReferer + link;

        return getObsDynamic(referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private Observable<Response<ResponseBody>> getObsStatic(String referer) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
            return mInfoService.getInfoWanStatic(mCookie, referer);
        } else {
            return mInfoOldService.getInfoWanStatic(mCookie, referer);
        }
    }

    @Override
    public Observable<List<String>> getStaticSetting(String link, String type) {
        String referer = mReferer + link;

        return getObsStatic(referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private Observable<Response<ResponseBody>> getObsPptp(String referer) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
            return mInfoService.getInfoWanPptp(mCookie, referer);
        } else {
            return mInfoOldService.getInfoWanPptp(mCookie, referer);
        }
    }

    @Override
    public Observable<List<String>> getPptpSetting(String link, String type) {
        String referer = mReferer + link;

        return getObsPptp(referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<ResponseBody>> getObsPppoe(String referer) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
            return mInfoService.getInfoWanPppoe(mCookie, referer);
        } else {
            return mInfoOldService.getInfoWanPppoe(mCookie, referer);
        }
    }

    @Override
    public Observable<List<String>> getPppoeSetting(String link, String type) {
        String referer = mReferer + link;

        return getObsPppoe(referer)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private Observable<Response<ResponseBody>> getObsWanDynamic(String referer) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
            return mSettingService.setWanDynamic(mCookie, referer);
        } else {
            return mSettingOldService.setWanDynamic(mCookie, referer);
        }
    }


    @Override
    public Observable<String> setWanDynamic(String linkReferer) {
        String referer = mReferer + linkReferer;

        return getObsWanDynamic(referer)
                .retry(3)
                .flatMap(Utils::replaceResponseToText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<ResponseBody>> getObsWanStatic(String referer, String ip, String mask, String gateway, String dns1, String dns2) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
            return mSettingService.setWanStatic(mCookie, referer, ip, mask, gateway, dns1, dns2);
        } else {
            return mSettingOldService.setWanStatic(mCookie, referer, ip, mask, gateway, dns1, dns2);
        }
    }


    @Override
    public Observable<String> setWanStatic(String linkReferer, String ip, String mask, String gateway, String dns1, String dns2) {
        String referer = mReferer + linkReferer;

        return getObsWanStatic(referer, ip, mask, gateway, dns1, dns2)
                .retry(3)
                .flatMap(Utils::replaceResponseToText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<ResponseBody>> getObsWanPptp(String referer, String username, String pass, String server) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
            return mSettingService.setWanPptp(mCookie, referer, username, pass, pass, server);
        } else {
            return mSettingOldService.setWanPptp(mCookie, referer, username, pass, pass, server);
        }
    }


    @Override
    public Observable<String> setWanPptp(String linkReferer, String username, String pass, String server) {
        String referer = mReferer + linkReferer;

        return getObsWanPptp(referer, username, pass, server)
                .retry(3)
                .flatMap(Utils::replaceResponseToText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<ResponseBody>> getObsWanPppoe(String referer, String username, String pass) {
        if (mDataManager.getKey() != null && mDataManager.getKey().length() > 0) {
            return mSettingService.setWanPppoe(mCookie, referer, username, pass, pass);
        } else {
            return mSettingOldService.setWanPppoe(mCookie, referer, username, pass, pass);
        }
    }


    @Override
    public Observable<String> setWanPppoe(String linkReferer, String username, String pass) {
        String referer = mReferer + linkReferer;

        return getObsWanPppoe(referer, username, pass)
                .retry(3)
                .flatMap(Utils::replaceResponseToText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
