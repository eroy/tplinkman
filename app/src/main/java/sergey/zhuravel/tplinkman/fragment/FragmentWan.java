package sergey.zhuravel.tplinkman.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.adapter.WanDynamicRA;
import sergey.zhuravel.tplinkman.adapter.WanPptpRa;
import sergey.zhuravel.tplinkman.adapter.WanStaticRA;
import sergey.zhuravel.tplinkman.model.Info;
import sergey.zhuravel.tplinkman.model.WanInfo;


public class FragmentWan extends AppFragment {


    private Spinner spinner;
    private RecyclerView recyclerView;
    private List<WanInfo> wanInfoList;
    private List<Info> infoList;
    private ArrayList<String> data =new ArrayList<>();
    private ArrayList<String> infoWanDyn = new ArrayList<>();
    private ArrayList<String> infoWanType = new ArrayList<>();

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmnet_wan, container, false);
        data = getArguments().getStringArrayList("data");
        wanInfoList = new ArrayList<>();
        infoList = new ArrayList<>();

        initView(view);
        infoWanType = getInfo(data, INFO_WAN_TYPE);
        getWanTypeInfo(infoWanType.get(0),spinner);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (spinner.getSelectedItem().toString()) {

                    case "Dynamic IP":
                        wanInfoList.clear();
                        infoWanDyn = getInfo(data,INFO_WAN_DYN);
                        wanInfoList.add(new WanInfo(infoWanDyn.get(0),infoWanDyn.get(1),infoWanDyn.get(2),infoWanDyn.get(3)));
                        WanDynamicRA wanDynamicRA = new WanDynamicRA(wanInfoList,data,getActivity());
                        recyclerView.setAdapter(wanDynamicRA);
                        wanDynamicRA.notifyDataSetChanged();

                        break;
                    case "Static IP":
                        wanInfoList.clear();
                        infoWanDyn = getInfo(data,INFO_WAN_STAT);
                        wanInfoList.add(new WanInfo(infoWanDyn.get(0),infoWanDyn.get(1),infoWanDyn.get(2),infoWanDyn.get(3),infoWanDyn.get(4)));
                        WanStaticRA wanStaticRA = new WanStaticRA(wanInfoList,data,getActivity());
                        recyclerView.setAdapter(wanStaticRA);
                        wanStaticRA.notifyDataSetChanged();
                        break;

                    case "PPTP":
                        wanInfoList.clear();
                        infoWanDyn = getInfo(data,INFO_WAN_PPTP);
                        wanInfoList.add(new WanInfo(infoWanDyn.get(0),infoWanDyn.get(1),infoWanDyn.get(2),infoWanDyn.get(3),infoWanDyn.get(4),infoWanDyn.get(5)));
                        WanPptpRa wanPptpRa = new WanPptpRa(wanInfoList,data,getActivity());
                        recyclerView.setAdapter(wanPptpRa);
                        wanPptpRa.notifyDataSetChanged();
                        break;
                    case "PPOE":

                        break;
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return view;
    }

    private void initView(View view) {
        spinner = (Spinner) view.findViewById(R.id.spTypeWan);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void getWanTypeInfo(String name,Spinner spinner) {
        if (name.contains("WanDynamicIpCfgRpm")) {
            spinner.setSelection(0);
        }
        else if(name.contains("WanStaticIpCfgRpm")) {
            spinner.setSelection(1);
        }
        else if (name.contains("PPTPCfgRpm")){
            spinner.setSelection(2);
        }


    }
}
