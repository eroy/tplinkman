package sergey.zhuravel.tplinkman.ui.info;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sergey.zhuravel.tplinkman.api.InfoService;
import sergey.zhuravel.tplinkman.constant.TypeConstant;
import sergey.zhuravel.tplinkman.utils.Utils;


public class InfoModel implements InfoContract.Model {
    private InfoService mInfoService;

    public InfoModel(InfoService mInfoService) {
        this.mInfoService = mInfoService;
    }

    @Override
    public Observable<List<String>> getInfoFirmware(String cookie, String referer, String type) {
        return mInfoService.getInfoFirmware(cookie, referer)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservable(responseBodyResponse, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
