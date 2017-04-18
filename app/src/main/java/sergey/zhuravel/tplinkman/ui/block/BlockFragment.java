package sergey.zhuravel.tplinkman.ui.block;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatEditText;
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
import android.widget.Toast;

import java.util.List;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.model.Blocked;
import sergey.zhuravel.tplinkman.ui.base.BaseFragment;
import sergey.zhuravel.tplinkman.ui.main.MacDevice;
import sergey.zhuravel.tplinkman.utils.MacFormatting;


public class BlockFragment extends BaseFragment implements BlockContract.View {

    private Toolbar mToolbar;
    private BlockContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private TextView mTvNoBlocked;
    private MacDevice mMacDevice;

    private BlockAdapter mBlockAdapter;

    private FloatingActionButton mFabBlock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mMacDevice = ((MacDevice) getActivity());
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


        mFabBlock.setOnClickListener(v -> {
            showBlockDialog();
        });
        return view;
    }


    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_client);
        mTvNoBlocked = (TextView) view.findViewById(R.id.tv_no_blocked);
        mFabBlock = (FloatingActionButton) view.findViewById(R.id.blocked_fab);

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

    @Override
    public void clearBlockedList() {
        mBlockAdapter.clearClients();
    }


    @Override
    public void showSuccessToast(String mac) {
        String text = getString(R.string.success_unblock) + " " + mac;
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorToast() {
        Toast.makeText(getActivity(), getString(R.string.error_toast), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showConfirmUnBlockDialog(String mac, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.do_you_want_unblocked) + " " + mac + " ?");

        builder.
                setPositiveButton(R.string.dialog_unblock, (dialog, which) ->
                        mPresenter.setUnBlockClient(mac, position))
                .setNegativeButton(R.string.cancel, (dialog, which) ->
                        dialog.dismiss()).setCancelable(false).show();
    }


    private void showBlockDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(R.string.dialog_title_block);
        View viewDialog = getActivity().getLayoutInflater().inflate(R.layout.dialog_block, null);
        dialog.setView(viewDialog);

        AppCompatEditText etMac = (AppCompatEditText) viewDialog.findViewById(R.id.input_mac);
        AppCompatEditText etDesc = (AppCompatEditText) viewDialog.findViewById(R.id.input_reason);


        MacFormatting.automaticMacAddressFormatting(etMac);


        dialog.setPositiveButton(R.string.dialog_block, (dialog1, which) -> {
            mPresenter.setBlockClient(etMac.getText().toString().toLowerCase(), etDesc.getText().toString());

        });

        dialog.setNegativeButton(R.string.cancel, (dialog1, which) ->
                dialog1.dismiss());


        dialog
                .setCancelable(false)
                .create()
                .show();


    }


    @Override
    public void showNoValidateMacToast() {
        Toast.makeText(getActivity(), getString(R.string.mac_is_not_valid), Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getMacDevice() {
        return mMacDevice.getMacDevice().replace(":", "-").toLowerCase();
    }

}
