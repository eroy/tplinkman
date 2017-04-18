package sergey.zhuravel.tplinkman.ui.start;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.ui.base.BaseActivity;
import sergey.zhuravel.tplinkman.ui.main.MainActivity;
import sergey.zhuravel.tplinkman.utils.NetworkUtils;
import sergey.zhuravel.tplinkman.utils.Vendors;

public class StartActivity extends BaseActivity implements StartContract.View {

    private StartContract.Presenter mPresenter;
    private Toolbar mToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TextView mTextNotFound;
    private TextView mIp;

    private RecyclerView mRvHistory;
    private FloatingActionButton mFabAddNewConnect;

    private RelativeLayout mRlHistory;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initView();
        initProgressDialog();
        mPresenter = new StartPresenter(this, new StartModel(App.getDataManager(this)));

        setLocalIp();
        setRefreshLayout();

        mIp.setOnClickListener(v -> {
            mPresenter.validateAndInput("192.168.0.1", "admin", "trifle_best");
        });
        mFabAddNewConnect.setOnClickListener(v -> showRemoteConnectionDialog());

    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTextNotFound = (TextView) findViewById(R.id.text_not_found);
        mIp = (TextView) findViewById(R.id.ip_address);
        mRvHistory = (RecyclerView) findViewById(R.id.recycler_history);
        mRlHistory = (RelativeLayout) findViewById(R.id.rl_history);
        mFabAddNewConnect = (FloatingActionButton) findViewById(R.id.remoute_fab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        initToolbar(mToolbar, getString(R.string.app_name), false);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }


    private List<String> getGateway() {
        List<String> result = new ArrayList<>();
        @SuppressLint("WifiManagerPotentialLeak")
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            result.add(Formatter.formatIpAddress(wifiManager.getDhcpInfo().gateway));
            result.add(wifiInfo.getBSSID());

        }
        return result;
    }

    private void showRemoteConnectionDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.remote_conect_to);
        View viewDialog = getLayoutInflater().inflate(R.layout.dialog_remote, null);
        dialog.setView(viewDialog);

        AppCompatEditText etName = (AppCompatEditText) viewDialog.findViewById(R.id.input_name);
        AppCompatEditText etIp = (AppCompatEditText) viewDialog.findViewById(R.id.input_ip);
        AppCompatEditText etUsername = (AppCompatEditText) viewDialog.findViewById(R.id.input_user);
        AppCompatEditText etPassword = (AppCompatEditText) viewDialog.findViewById(R.id.input_password);


        etUsername.setText("admin");
        etPassword.setText("trifle_best");

        dialog.setPositiveButton(R.string.connect, (dialog1, which) -> {

            mPresenter.validateAndInput(etIp.getText().toString(),
                    etUsername.getText().toString(),
                    etPassword.getText().toString());

        });

        dialog.setNegativeButton(R.string.cancel, (dialog1, which) ->
                dialog1.dismiss());


        dialog
                .setCancelable(false)
                .create()
                .show();


    }


    private void setLocalIp() {
        List<String> ips = getGateway();
        if (ips.size() != 0 && Vendors.isTpLink(ips.get(1))) {
            textNotFountVisible(false);
            mIp.setText(ips.get(0) + " (" + ips.get(1) + ")");
        } else {
            textNotFountVisible(true);
            mIp.setText("");
        }
    }


    private void setRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            setLocalIp();
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }

    private void textNotFountVisible(boolean visible) {
        if (visible) {
            mTextNotFound.setVisibility(View.VISIBLE);
        } else {
            mTextNotFound.setVisibility(View.GONE);
        }

    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.connecting));
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }


    @Override
    public void navigateToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public boolean isReachableHost(String host) {
        if (NetworkUtils.isReachable(host)) {
            return true;
        } else {
            showDialogMessage(getString(R.string.host_is_unreachable));
            return false;
        }
    }


    @Override
    public void showDialogErrorInput() {
        showDialogMessage(getString(R.string.error_input_data));
    }

    @Override
    public void showDialogRepeat() {
        showDialogMessage(getString(R.string.try_again_later));
    }

    private void showDialogMessage(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(message);
        dialog.setPositiveButton("Ok", (dialog1, which) -> dialog1.dismiss());
        dialog
                .setCancelable(false)
                .create()
                .show();
    }
}