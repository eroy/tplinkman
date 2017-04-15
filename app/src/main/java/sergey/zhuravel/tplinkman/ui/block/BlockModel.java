package sergey.zhuravel.tplinkman.ui.block;


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

public class BlockModel implements BlockContract.Model {

    private InfoService mInfoService;
    private DataManager mDataManager;
    private String mCookie;
    private String mReferer;
    private InfoOldService mInfoOldService;

    public BlockModel(DataManager dataManager) {
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


    private Observable<Response<ResponseBody>> getObsWifiFilter(String referer, int page) {
        if (mDataManager.getKey().length() > 0) {
            return mInfoService.getInfoWifiFilter(mCookie, referer, page);
        } else {
            return mInfoOldService.getInfoWifiFilter(mCookie, referer, page);
        }
    }

    @Override
    public Observable<List<String>> getInfoWifiFilter(String link, String type, int page) {
        String referer = mReferer + link;

        return getObsWifiFilter(referer, page)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
