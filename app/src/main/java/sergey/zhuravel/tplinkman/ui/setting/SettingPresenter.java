package sergey.zhuravel.tplinkman.ui.setting;

/**
 * Created by serj on 29.03.2017.
 */

public class SettingPresenter implements SettingContract.Presenter {
    SettingContract.View mView;
    SettingContract.Model mModel;

    public SettingPresenter(SettingContract.View mView, SettingContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }


}
