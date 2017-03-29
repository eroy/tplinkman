package sergey.zhuravel.tplinkman.ui.info;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.ui.BaseFragment;

public class InfoFragment extends BaseFragment implements InfoContract.View {

    private TextView mTvBuild;
    private TextView mTvMac;
    private TextView mTvWifiSsid;
    private TextView mTvWifiPass;
    private ImageView mImageToolbar;

    private Toolbar mToolbar;

    private InfoContract.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_info, container, false);


        initView(view);


        mPresenter = new InfoPresenter(this, new InfoModel(App.getDataManager(getActivity())));

        mPresenter.getFirmwareInfo();
        mPresenter.getMacInfo();
        mPresenter.getWifiNameInfo();
        mPresenter.getWifiPassInfo();

//        infoList.add(new Info(getString(R.string.info_build), listFirmwareInfo.get(0)));
//        infoList.add(new Info(getString(R.string.info_version), listFirmwareInfo.get(1)));
//        infoList.add(new Info(getString(R.string.info_mac), listMacWanInfo.get(0)));

//        infoList.add(new Info("Type WAN", getWanTypeInfo(listWanTypeInfo.get(0))));
//        infoList.add(new Info(getString(R.string.info_ssid), listWifiInfo.get(0)));
//        infoList.add(new Info(getString(R.string.info_pass), listWifiSecInfo.get(1).substring(1)));


        return view;
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTvBuild = (TextView) view.findViewById(R.id.info_build);
        mTvMac = (TextView) view.findViewById(R.id.info_mac);
        mTvWifiSsid = (TextView) view.findViewById(R.id.info_ssid);
        mTvWifiPass = (TextView) view.findViewById(R.id.info_pass);
        mImageToolbar = (ImageView) view.findViewById(R.id.toolbar_image);


    }


    @Override
    public void setInfoFirmware(List<String> lists) {
        mTvBuild.setText(lists.get(0));
//        mTvVersion.setText(lists.get(1));

        initToolbar(mToolbar, lists.get(1), false);
        mImageToolbar.setImageResource(R.drawable.router_1);
    }

    @Override
    public void setInfoMac(String mac) {
        mTvMac.setText(mac);
    }

    @Override
    public void setInfoWifiName(String ssid) {
        mTvWifiSsid.setText(ssid);
    }

    @Override
    public void setInfoWifiPass(String pass) {
        mTvWifiPass.setText(pass);
    }

    private String getWanTypeInfo(String name) {
        if (name.contains("WanDynamicIpCfgRpm")) {
            return "Dynamic IP";
        } else if (name.contains("WanStaticIpCfgRpm")) {
            return "Static IP";
        } else if (name.contains("PPTPCfgRpm")) {
            return "PPTP";
        } else {
            return "";
        }

    }

}
