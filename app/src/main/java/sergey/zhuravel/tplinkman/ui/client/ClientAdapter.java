package sergey.zhuravel.tplinkman.ui.client;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.model.Client;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {

    private List<Client> mListClient;
    private ClientContract.Presenter mPresenter;

    public ClientAdapter(ClientContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
        mListClient = new ArrayList<>();


    }

    public void addClients(List<Client> list) {
        clearClient();
        mListClient.addAll(list);
        notifyDataSetChanged();
    }

    public void clearClient() {
        mListClient.clear();
        notifyDataSetChanged();

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_client, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Client client = mListClient.get(position);
        if (client != null) {
            holder.mTvName.setText(client.getName());
            holder.mTvMac.setText(client.getMac());
            holder.mTvIp.setText(client.getIp());
            holder.mTvDownload.setText(client.getDownload());
            holder.mTvUpload.setText(client.getUpload());
        }
        holder.mRlClient.setOnClickListener(v -> {
            Log.e("TEST-CLICK", client.getMac());
        });


    }

    @Override
    public int getItemCount() {
        return mListClient.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTvName;
        TextView mTvIp;
        TextView mTvMac;
        TextView mTvUpload;
        TextView mTvDownload;
        RelativeLayout mRlClient;

        public ViewHolder(View view) {
            super(view);

            mTvName = (TextView) view.findViewById(R.id.tvName);
            mTvIp = (TextView) view.findViewById(R.id.tvIp);
            mTvMac = (TextView) view.findViewById(R.id.tvMac);
            mTvDownload = (TextView) view.findViewById(R.id.tvDownload);
            mTvUpload = (TextView) view.findViewById(R.id.tvUpload);
            mRlClient = (RelativeLayout) view.findViewById(R.id.rlClient);
        }
    }
}
