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

public class FragmentInfo extends AppFragment {

    private ArrayList<String> data =new ArrayList<>();
    private List<Info> infoList;
    private RecyclerView recyclerView;
    private InfoRecyclerAdapter infoRecyclerAdapter;
    ArrayList<String> info;

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


//        String[] name ={"build: ","version: ","ssid: ","mac: ","ip: ","typeVpn: "
//                ,"mask: ","gateway: ","dns1: ","dns2: "};
        infoList.add(new Info("Build:",info.get(0).replace("\"","")));
        infoList.add(new Info("Version:",info.get(1).replace("\"","")));
        infoList.add(new Info("Mac address:",info.get(3).replace("\"","")));


        infoRecyclerAdapter = new InfoRecyclerAdapter(getActivity(), infoList);
        recyclerView.setAdapter(infoRecyclerAdapter);
        return view;
    }



}
