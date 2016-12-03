package sergey.zhuravel.tplinkman.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sergey.zhuravel.tplinkman.Const;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.fragment.AppFragment;
import sergey.zhuravel.tplinkman.model.Info;
import sergey.zhuravel.tplinkman.model.WanInfo;


public class WanDynamicRA extends RecyclerView.Adapter<WanDynamicRA.RVHolder> implements Const{

    private List<WanInfo> wanInfoList;
    private Context context;
    private ArrayList<String> data;

    public WanDynamicRA(List<WanInfo> wanInfoList, ArrayList<String> data,Context context) {
        this.wanInfoList = wanInfoList;
        this.context = context;
        this.data = data;
    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wan_dynamic_ip, null);

        return new WanDynamicRA.RVHolder(view);
    }

    @Override
    public void onBindViewHolder(RVHolder holder, int position) {
        final WanInfo wanInfo = wanInfoList.get(position);

        holder.ip.setText(wanInfo.getIp());
        holder.mask.setText(wanInfo.getMask());
        holder.gateway.setText(wanInfo.getGateway());
        holder.dns.setText(wanInfo.getDns1());

        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppFragment appFragment = new AppFragment();
                String request = appFragment.setSettingsRouter(data,TYPE_WAN_DYN,new ArrayList<String>());
                if (request.equals("ok")) {
                    Snackbar.make(view, R.string.set_suc_applied,Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return wanInfoList.size();
    }

    class RVHolder extends RecyclerView.ViewHolder {
        TextView ip;
        TextView mask;
        TextView gateway;
        TextView dns;
        Button btnSave;

        public RVHolder(View view) {
            super(view);
            ip = (TextView) view.findViewById(R.id.ip);
            mask = (TextView) view.findViewById(R.id.mask);
            gateway = (TextView) view.findViewById(R.id.gateway);
            dns = (TextView) view.findViewById(R.id.dns1);
            btnSave = (Button) view.findViewById(R.id.save);



        }
    }
}
