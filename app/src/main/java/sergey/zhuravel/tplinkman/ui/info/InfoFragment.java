package sergey.zhuravel.tplinkman.ui.info;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.adapter.InfoRecyclerAdapter;
import sergey.zhuravel.tplinkman.constant.ApiConstant;
import sergey.zhuravel.tplinkman.fragment.AppFragment;
import sergey.zhuravel.tplinkman.model.Info;

public class InfoFragment extends AppFragment implements InfoContract.View {

    private ArrayList<String> data = new ArrayList<>();
    private List<Info> infoList;
    private RecyclerView recyclerView;
    private InfoRecyclerAdapter infoRecyclerAdapter;
    private ArrayList<String> info;
    private ArrayList<String> listWifiInfo = new ArrayList<>();
    private ArrayList<String> listWifiSecInfo = new ArrayList<>();
    private ArrayList<String> listWanTypeInfo = new ArrayList<>();
    private ArrayList<String> listFirmwareInfo = new ArrayList<>();
    private ArrayList<String> listMacWanInfo = new ArrayList<>();

    private TextView mTvBuild;
    private TextView mTvVersion;

    private Toolbar mToolbar;

    private InfoContract.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_info, container, false);

//        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        infoList = new ArrayList<>();
//        get value data
        data = getArguments().getStringArrayList("data");

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Info");
        }
        mTvBuild = (TextView) view.findViewById(R.id.info_build);
        mTvVersion = (TextView) view.findViewById(R.id.version_build);


        String baseUrl = ApiConstant.SERVER + data.get(0) + "/" + data.get(1) + "/";

        mPresenter = new InfoPresenter(this, new InfoModel(App.getApiManager(baseUrl).getInfoService()));

        String refer = ApiConstant.SERVER + data.get(0) + "/" + data.get(1) + "/" + ApiConstant.INFO_FIRMWARE;
        String cookie = "Authorization=" + cookieEncodeMD5(data.get(2), data.get(3));

        mPresenter.getFirmwareInfo(cookie, refer);

//        info = asyncInfo(data);
//        listWifiInfo = getInfo(data, INFO_WIFI);
//        listWifiSecInfo = getInfo(data, INFO_WIFI_SEC);
//        listWanTypeInfo = getInfo(data, INFO_WAN_TYPE);
//        listFirmwareInfo = getInfo(data, INFO_FIRMWARE);
//        listMacWanInfo = getInfo(data, INFO_MAC_WAN);

//        infoList.add(new Info(getString(R.string.info_build), listFirmwareInfo.get(0)));
//        infoList.add(new Info(getString(R.string.info_version), listFirmwareInfo.get(1)));
//        infoList.add(new Info(getString(R.string.info_mac), listMacWanInfo.get(0)));

//        infoList.add(new Info("Type WAN", getWanTypeInfo(listWanTypeInfo.get(0))));
//        infoList.add(new Info(getString(R.string.info_ssid), listWifiInfo.get(0)));
//        infoList.add(new Info(getString(R.string.info_pass), listWifiSecInfo.get(1).substring(1)));


//        infoRecyclerAdapter = new InfoRecyclerAdapter(getActivity());
//        recyclerView.setAdapter(infoRecyclerAdapter);

//        setInfoList(infoList);
        return view;
    }


    private void setInfoList(List<Info> infoList) {
        infoRecyclerAdapter.addInfo(infoList);
    }

    @Override
    public void setInfoFirmware(List<String> lists) {
        mTvBuild.setText(lists.get(0));
        mTvVersion.setText(lists.get(1));

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
