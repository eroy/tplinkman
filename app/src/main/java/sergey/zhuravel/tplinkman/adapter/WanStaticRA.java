package sergey.zhuravel.tplinkman.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sergey.zhuravel.tplinkman.Const;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.fragment.AppFragment;
import sergey.zhuravel.tplinkman.model.WanInfo;


public class WanStaticRA extends RecyclerView.Adapter<WanStaticRA.RVHolder> implements Const {
    private List<WanInfo> wanInfoList;
    private Context context;
    private ArrayList<String> data;

    public WanStaticRA(List<WanInfo> wanInfoList, ArrayList<String> data,Context context) {
        this.wanInfoList = wanInfoList;
        this.context = context;
        this.data = data;
    }


    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wan_static_ip, null);

        return new WanStaticRA.RVHolder(view);
    }

    @Override
    public void onBindViewHolder(final RVHolder holder, int position) {
        final WanInfo wanInfo = wanInfoList.get(position);

        holder.ip.setText(wanInfo.getIp());
        holder.mask.setText(wanInfo.getMask());
        holder.gateway.setText(wanInfo.getGateway());
        holder.dns1.setText(wanInfo.getDns1());
        holder.dns2.setText(wanInfo.getDns2());
        if (wanInfo.getDns2().isEmpty()){
            holder.dns2.setText("0.0.0.0");
        }



        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppFragment appFragment = new AppFragment();
                ArrayList<String> value = new ArrayList<String>();
                value.add(holder.ip.getText().toString());
                value.add(holder.mask.getText().toString());
                value.add(holder.gateway.getText().toString());
                value.add(holder.dns1.getText().toString());
                value.add(holder.dns2.getText().toString());

                String request = appFragment.setSettingsRouter(context,data,TYPE_WAN_STAT,value);
                if (request.equals("ok")) {
                    Snackbar.make(view, R.string.set_suc_applied,Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    private void setError(final EditText editText, final TextInputLayout til, final String name) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!validateIP(editText.getText().toString())) {
                    til.setError("Error " + name);

                } else {
                    til.setErrorEnabled(false);
                }
            }
        });
    }
    public boolean validateIP(String ip) {
        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    @Override
    public int getItemCount() {
        return wanInfoList.size();
    }

    class RVHolder extends RecyclerView.ViewHolder {
        EditText ip;
        EditText mask;
        EditText gateway;
        EditText dns1;
        EditText dns2;
        Button btnSave;
        TextInputLayout tilIp,tilMask,tilGateway,tilDns1,tilDns2;

        public RVHolder(View view) {
            super(view);
            ip = (EditText) view.findViewById(R.id.etIP);
            mask = (EditText) view.findViewById(R.id.etMask);
            gateway = (EditText) view.findViewById(R.id.etGateway);
            dns1 = (EditText) view.findViewById(R.id.etDns1);
            dns2 = (EditText) view.findViewById(R.id.etDns2);

            tilIp = (TextInputLayout) view.findViewById(R.id.tilIP);
            tilMask = (TextInputLayout) view.findViewById(R.id.tilMask);
            tilGateway = (TextInputLayout) view.findViewById(R.id.tilGateway);
            tilDns1 = (TextInputLayout) view.findViewById(R.id.tilDns1);
            tilDns2 = (TextInputLayout) view.findViewById(R.id.tilDns2);

            btnSave = (Button) view.findViewById(R.id.save);

            setError(ip,tilIp,"IP");
            setError(mask,tilMask,"Mask");
            setError(gateway,tilGateway,"Gateway");
            setError(dns1,tilDns1,"Primary DNS");
            setError(dns2,tilDns2,"Secondary DNS");

        }
    }
}
