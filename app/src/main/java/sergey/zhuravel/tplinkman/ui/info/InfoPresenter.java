package sergey.zhuravel.tplinkman.ui.info;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.constant.TypeConstant;
import sergey.zhuravel.tplinkman.model.Info;

public class InfoPresenter implements InfoContract.Presenter {
    private InfoContract.View mView;
    private InfoContract.Model mModel;

    public InfoPresenter(InfoContract.View mView, InfoContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void getFirmwareInfo(String cookie, String referer) {
        mModel.getInfoFirmware(cookie, referer, TypeConstant.INFO_FIRMWARE)
                .subscribe(strings -> {
//                   List<Info> infos = new ArrayList<>();
//                    infos.add(new Info(mView.resourceIdToString(R.string.info_build), strings.get(0)));
//                    infos.add(new Info(mView.resourceIdToString(R.string.info_version), strings.get(1)));

                    mView.setInfoFirmware(strings);

                }, throwable -> Log.e("SERJ", throwable.getMessage()));
    }
}
