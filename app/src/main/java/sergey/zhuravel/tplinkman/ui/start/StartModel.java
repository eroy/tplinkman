package sergey.zhuravel.tplinkman.ui.start;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.api.InputService;
import sergey.zhuravel.tplinkman.constant.ApiConstant;
import sergey.zhuravel.tplinkman.manager.DataManager;
import sergey.zhuravel.tplinkman.utils.LinkGenerate;
import sergey.zhuravel.tplinkman.utils.Utils;


public class StartModel implements StartContract.Model {

    private DataManager mDataManager;
    private InputService mInputService;

    public StartModel(DataManager dataManager) {
        this.mDataManager = dataManager;
    }


    @Override
    public Observable<String> getKey(String ip, String username, String password) {

        mInputService = App.getApiManager(LinkGenerate.baseLink(ip)).getInputService();

        String cookie = LinkGenerate.cookie(username, password);


        return mInputService.getKey(cookie)
                .retry(3)
                .flatMap(Utils::replaceResponseKeyToText)
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

    @Override
    public Observable<String> inputValidateOld(String ip, String username, String password) {

        mInputService = App.getApiManager(LinkGenerate.baseLink(ip)).getInputService();
        String referer = LinkGenerate.referer(ip, ApiConstant.INPUT_VALIDATE_OLD);
        String authorization = LinkGenerate.authorization(username, password);

        return mInputService.validateInputOld(referer, authorization)
                .retry(3)
                .flatMap(Utils::replaceResponseToText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void savePreference(String ip, String key, String username, String password) {
        mDataManager.saveData(ip, key, username, password);
    }

    @Override
    public void savePreference(String ip, String username, String password) {
        mDataManager.saveData(ip, username, password);
    }


}
