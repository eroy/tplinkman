package sergey.zhuravel.tplinkman.ui.block;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.model.Blocked;
import sergey.zhuravel.tplinkman.ui.base.BaseFragment;


public class BlockFragment extends BaseFragment implements BlockContract.View {

    private Toolbar mToolbar;
    private BlockContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private TextView mTvNoBlocked;

    private BlockAdapter mBlockAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_block, container, false);

        initView(view);

        mPresenter = new BlockPresenter(this, new BlockModel(App.getDataManager(getActivity())));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBlockAdapter = new BlockAdapter(mPresenter);
        mRecyclerView.setAdapter(mBlockAdapter);

        mPresenter.getWifiFilterInfo();
        return view;
    }


    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_client);
        mTvNoBlocked = (TextView) view.findViewById(R.id.tv_no_blocked);

        initToolbar(mToolbar, "Blocked list", true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void noBlockedTextAccessibility(boolean visible) {
        if (visible) {
            mTvNoBlocked.setVisibility(View.VISIBLE);
        } else {
            mTvNoBlocked.setVisibility(View.GONE);
        }
    }

    @Override
    public void addBlockedList(List<Blocked> list) {
        mBlockAdapter.addClients(list);
    }

}
