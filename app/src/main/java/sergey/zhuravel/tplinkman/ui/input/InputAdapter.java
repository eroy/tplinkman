package sergey.zhuravel.tplinkman.ui.input;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.model.RouterSession;


public class InputAdapter extends RecyclerView.Adapter<InputAdapter.ViewHolder> {

    private List<RouterSession> mRouterSessionList;
    private InputContract.Presenter mPresenter;

    public InputAdapter(InputContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
        mRouterSessionList = new ArrayList<>();
    }

    public void addSession(List<RouterSession> routerSessions) {
        mRouterSessionList.addAll(routerSessions);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_connect, parent, false);
        return new InputAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RouterSession routerSession = mRouterSessionList.get(position);

        if (!routerSession.getNameConnection().equals("")) {
            holder.mTvIp.setText(routerSession.getIp());
            holder.mTvName.setText(routerSession.getNameConnection());
        } else {
            holder.mTvIp.setText(routerSession.getIp());
            holder.mTvName.setText(routerSession.getIp());
        }


    }

    @Override
    public int getItemCount() {
        return mRouterSessionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTvName;
        TextView mTvIp;
        ImageView mImgVisible;
        RelativeLayout mRlClient;

        public ViewHolder(View view) {
            super(view);

            mTvName = (TextView) view.findViewById(R.id.ip_name);
            mTvIp = (TextView) view.findViewById(R.id.ip_address);
            mImgVisible = (ImageView) view.findViewById(R.id.iv_visible);

            mRlClient = (RelativeLayout) view.findViewById(R.id.rl_session);
        }
    }
}
