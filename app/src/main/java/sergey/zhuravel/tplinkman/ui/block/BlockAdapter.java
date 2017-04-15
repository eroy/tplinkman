package sergey.zhuravel.tplinkman.ui.block;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.model.Blocked;

public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.ViewHolder> {

    private List<Blocked> mListBlockedClient;
    private BlockContract.Presenter mPresenter;

    public BlockAdapter(BlockContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;

        mListBlockedClient = new ArrayList<>();
    }

    public void addClients(List<Blocked> list) {
        mListBlockedClient.addAll(list);
        notifyDataSetChanged();
    }


    public void clearClients() {
        mListBlockedClient.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_blocked, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Blocked blocked = mListBlockedClient.get(position);

        holder.mTvMac.setText(blocked.getMac());
        holder.mTvDescrp.setText(blocked.getDescription());


        holder.mRlBlocked.setOnClickListener(v -> mPresenter.setConfirmUnBlock(position));

    }

    @Override
    public int getItemCount() {
        return mListBlockedClient.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView mTvMac;
        TextView mTvDescrp;

        LinearLayout mRlBlocked;

        public ViewHolder(View view) {
            super(view);

            mTvMac = (TextView) view.findViewById(R.id.tvMac);
            mTvDescrp = (TextView) view.findViewById(R.id.tvDescrp);
            mRlBlocked = (LinearLayout) view.findViewById(R.id.rlBlocked);
        }

    }
}
