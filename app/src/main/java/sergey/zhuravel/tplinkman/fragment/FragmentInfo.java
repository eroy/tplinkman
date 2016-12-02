package sergey.zhuravel.tplinkman.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.adapter.InfoRecyclerAdapter;
import sergey.zhuravel.tplinkman.model.Info;
import sergey.zhuravel.tplinkman.model.WifiInfo;

public class FragmentInfo extends AppFragment {

    private ArrayList<String> data =new ArrayList<>();
    private List<Info> infoList;
    private RecyclerView recyclerView;
    private InfoRecyclerAdapter infoRecyclerAdapter;
    private ArrayList<String> info;
    private ArrayList<String> listWifiInfo = new ArrayList<>();
    private ArrayList<String> listWifiSecInfo = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_info, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        infoList = new ArrayList<>();
//        get value data
        data = getArguments().getStringArrayList("data");


        info = asyncInfo(data);
        listWifiInfo = getInfoWifi(data, INFO_WIFI);
        listWifiSecInfo = getInfoWifi(data, INFO_WIFI_SEC);

        String wTitle="Wireless\n" +"SSID:\n" +"PASSWORD";
        String wSub="\n" + listWifiInfo.get(0)+"\n" +listWifiSecInfo.get(1).substring(1);

//        String[] name ={"build: ","version: ","ssid: ","mac: ","ip: ","typeVpn: "
//                ,"mask: ","gateway: ","dns1: ","dns2: "};
        infoList.add(new Info("Build:",info.get(0).replace("\"","")));
        infoList.add(new Info("Version:",info.get(1).replace("\"","")));
        infoList.add(new Info("Mac address:",info.get(3).replace("\"","")));
        ;
        infoList.add(new Info("SSID:",listWifiInfo.get(0)));
        infoList.add(new Info("Password wifi:",listWifiSecInfo.get(1).substring(1)));


        infoRecyclerAdapter = new InfoRecyclerAdapter(getActivity(), infoList);
        recyclerView.setAdapter(infoRecyclerAdapter);
        return view;
    }



}
