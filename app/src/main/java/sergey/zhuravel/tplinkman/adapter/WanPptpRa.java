package sergey.zhuravel.tplinkman.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sergey.zhuravel.tplinkman.Const;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.fragment.AppFragment;
import sergey.zhuravel.tplinkman.model.WanInfo;


public class WanPptpRa extends RecyclerView.Adapter<WanPptpRa.RVHolder> implements Const {

    private List<WanInfo> wanInfoList;
    private Context context;
    private ArrayList<String> data;

    public WanPptpRa(List<WanInfo> wanInfoList, ArrayList<String> data, Context context) {
        this.wanInfoList = wanInfoList;
        this.context = context;
        this.data = data;
    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wan_pptp, null);

        return new WanPptpRa.RVHolder(view);
    }

    @Override
    public void onBindViewHolder(final RVHolder holder, int position) {
        final WanInfo wanInfo = wanInfoList.get(position);
        holder.ip.setText(wanInfo.getIp());
        holder.mask.setText(wanInfo.getMask());
        holder.gateway.setText(wanInfo.getGateway());
        holder.username.setText(wanInfo.getUsername());
        holder.password.setText(wanInfo.getPassword());
        holder.vpnServer.setText(wanInfo.getVpnServer());

        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppFragment appFragment = new AppFragment();
                ArrayList<String> value = new ArrayList<String>();
                value.add(holder.username.getText().toString());
                value.add(holder.password.getText().toString());
                value.add(holder.vpnServer.getText().toString());

                String request = appFragment.setSettingsRouter(context,data,TYPE_WAN_PPTP,value);
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
        EditText username;
        EditText password;
        EditText vpnServer;
        Button btnSave;

        public RVHolder(View view) {
            super(view);
            ip = (TextView) view.findViewById(R.id.ip);
            mask = (TextView) view.findViewById(R.id.mask);
            gateway = (TextView) view.findViewById(R.id.gateway);
            username = (EditText) view.findViewById(R.id.etUsername);
            password = (EditText) view.findViewById(R.id.etPass);
            vpnServer = (EditText) view.findViewById(R.id.etVpnServer);
            btnSave = (Button) view.findViewById(R.id.save);


        }
    }
}
