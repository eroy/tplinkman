package sergey.zhuravel.tplinkman.ui.start;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.api.InputService;
import sergey.zhuravel.tplinkman.api.SettingService;
import sergey.zhuravel.tplinkman.constant.ApiConstant;
import sergey.zhuravel.tplinkman.constant.TypeConstant;
import sergey.zhuravel.tplinkman.manager.DataManager;
import sergey.zhuravel.tplinkman.utils.LinkGenerate;
import sergey.zhuravel.tplinkman.utils.Utils;


public class StartModel implements StartContract.Model {

    private DataManager mDataManager;
    private SettingService mSettingService;
    private InputService mInputService;

    public StartModel(DataManager dataManager) {
        this.mDataManager = dataManager;

//        mSettingService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp(), dataManager.getKey())).getSettingService();
    }


    @Override
    public Observable<String> getKey(String ip, String username, String password) {

        mInputService = App.getApiManager(LinkGenerate.baseLink(ip)).getInputService();

        String cookie = LinkGenerate.cookie(username, password);


        return mInputService.getKey(cookie)
                .retry(3)
                .flatMap(responseBodyResponse -> Utils.replaceResponseToObservableList(responseBodyResponse, TypeConstant.INFO_KEY))
                .flatMap(lists -> Observable.just(lists.get(0)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String> inputValidate(String ip, String username, String password, String key) {

        mInputService = App.getApiManager(LinkGenerate.baseLink(ip, key)).getInputService();
        String referer = LinkGenerate.referer(ip, key, ApiConstant.INPUT_VALIDATE);
        String cookie = LinkGenerate.cookie(username, password);


        return mInputService.validateInput(cookie, referer)
                .retry(3)
                .flatMap(Utils::replaceResponseToText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
