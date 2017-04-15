package sergey.zhuravel.tplinkman.ui.client;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.model.Client;
import sergey.zhuravel.tplinkman.ui.base.BaseFragment;
import sergey.zhuravel.tplinkman.ui.block.BlockFragment;


public class ClientFragment extends BaseFragment implements ClientContract.View {

    private ClientContract.Presenter mPresenter;
    private Toolbar mToolbar;
    private ClientAdapter mClientAdapter;
    private RecyclerView mRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        Log.e("DESTROY-clients", "onDestroy");
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_client, container, false);

        initView(view);

        mPresenter = new ClientPresenter(this, new ClientModel(App.getDataManager(getActivity())));


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mClientAdapter = new ClientAdapter(mPresenter);
        mRecyclerView.setAdapter(mClientAdapter);

        mPresenter.getWifiStationInfo();

        mPresenter.updateClientList();

        return view;
    }

    @Override
    public void addClientToList(List<Client> list) {
        mClientAdapter.addClients(list);
    }

    @Override
    public void clearClientList() {
        mClientAdapter.clearClients();
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_client);


        initToolbar(mToolbar, "Client list", false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_client_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.block_list:
                mPresenter.onDestroy();
                navigateToNextFragment(new BlockFragment());
                break;
        }


        return super.onOptionsItemSelected(item);
    }


}
