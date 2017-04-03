package sergey.zhuravel.tplinkman.ui.wireless;


import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.api.InfoService;
import sergey.zhuravel.tplinkman.api.SettingService;
import sergey.zhuravel.tplinkman.manager.DataManager;
import sergey.zhuravel.tplinkman.utils.LinkGenerate;

public class WirelessModel implements WirelessContract.Model {

    private DataManager mDataManager;
    private SettingService mSettingService;
    private InfoService mInfoService;

    public WirelessModel(DataManager dataManager) {
        this.mDataManager = dataManager;

        mSettingService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp(), dataManager.getKey())).getSettingService();
        mInfoService = App.getApiManager(LinkGenerate.baseLink(dataManager.getIp(), dataManager.getKey())).getInfoService();
    }

}
