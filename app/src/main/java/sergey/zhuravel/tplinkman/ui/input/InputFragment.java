package sergey.zhuravel.tplinkman.ui.input;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.model.RouterSession;
import sergey.zhuravel.tplinkman.ui.base.BaseFragment;
import sergey.zhuravel.tplinkman.ui.main.MainActivity;
import sergey.zhuravel.tplinkman.utils.IpFormatting;
import sergey.zhuravel.tplinkman.utils.Vendors;

public class InputFragment extends BaseFragment implements InputContract.View {

    private InputContract.Presenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TextView mTextNotFound;
    private TextView mIp;

    private RecyclerView mRvHistory;
    private FloatingActionButton mFabAddNewConnect;

    private RelativeLayout mRlHistory;
    private ProgressDialog mProgressDialog;

    private InputAdapter mInputAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_fragment, container, false);

        initView(view);
        mPresenter = new InputPresenter(this, new InputModel(App.getDataManager(getActivity()), App.getRealmManager()));

        mRvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvHistory.setItemAnimator(new DefaultItemAnimator());
        mInputAdapter = new InputAdapter(mPresenter);
        mRvHistory.setAdapter(mInputAdapter);


        initProgressDialog();

        mPresenter.getSession();

        setLocalIp();
        setRefreshLayout();


        mIp.setOnClickListener(v -> {
            showConnectionDialog(getGateway().get(0));
        });
        mFabAddNewConnect.setOnClickListener(v -> showConnectionDialog(null));

        initSwipe();
        return view;

    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            Paint p = new Paint();

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                RouterSession routerSession = mInputAdapter.getmRouterSessionList().get(position);

                if (direction == ItemTouchHelper.LEFT) {
                    showDeleteDialog(position, routerSession.getIp());
                } else {
                    showEditConnectDialog(routerSession);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_mode_edit_white_24dp);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_sweep_white_24dp);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRvHistory);
    }

    @Override
    public void setVisibleRouter(boolean visibleRouter) {
        mInputAdapter.setVisibleRouter(visibleRouter);
    }

    private void initView(View view) {

        mTextNotFound = (TextView) view.findViewById(R.id.text_not_found);
        mIp = (TextView) view.findViewById(R.id.ip_address);
        mRvHistory = (RecyclerView) view.findViewById(R.id.recycler_history);
        mRlHistory = (RelativeLayout) view.findViewById(R.id.rl_history);
        mFabAddNewConnect = (FloatingActionButton) view.findViewById(R.id.remoute_fab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);


    }

    @Override
    public void sessionHistoryAccessibility(boolean visible) {
        if (visible) {
            mRlHistory.setVisibility(View.VISIBLE);
        } else {
            mRlHistory.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void addSession(List<RouterSession> routerSessions) {
        mInputAdapter.addSession(routerSessions);
    }

    private List<String> getGateway() {
        List<String> result = new ArrayList<>();
        @SuppressLint("WifiManagerPotentialLeak")
        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            result.add(Formatter.formatIpAddress(wifiManager.getDhcpInfo().gateway));
            result.add(wifiInfo.getBSSID());

        }
        return result;
    }

    private void showConnectionDialog(String ip) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        View viewDialog = getActivity().getLayoutInflater().inflate(R.layout.dialog_remote, null);
        dialog.setView(viewDialog);

        AppCompatCheckBox cbSave = (AppCompatCheckBox) viewDialog.findViewById(R.id.cb_save);
        AppCompatEditText etName = (AppCompatEditText) viewDialog.findViewById(R.id.input_name);
        AppCompatEditText etIp = (AppCompatEditText) viewDialog.findViewById(R.id.input_ip);
        AppCompatEditText etUsername = (AppCompatEditText) viewDialog.findViewById(R.id.input_user);
        AppCompatEditText etPassword = (AppCompatEditText) viewDialog.findViewById(R.id.input_password);

        IpFormatting.automaticIPAddressFormatting(etIp);
        if (ip != null) {
            etIp.setText(ip);
        }

        etUsername.setText("admin");
        etPassword.setText("trifle_best");


        dialog.setPositiveButton(R.string.connect, (dialog1, which) -> {

            if (cbSave.isChecked()) {
                String ipHost = etIp.getText().toString();
                String username = etUsername.getText().toString();
                String pass = etPassword.getText().toString();
                String nameConnection = etName.getText().toString();
                mPresenter.saveSession(ipHost, username, pass, nameConnection);
            }

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
        mProgressDialog = new ProgressDialog(getActivity());
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
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    @Override
    public void showDialogHostUnreachable() {
        showDialogMessage(getString(R.string.host_is_unreachable));
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
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setMessage(message);
        dialog.setPositiveButton("Ok", (dialog1, which) -> dialog1.dismiss());
        dialog
                .setCancelable(false)
                .create()
                .show();
    }


    private void showDeleteDialog(int position, String ip) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setMessage(R.string.do_you_want_delete);

        dialog.setPositiveButton(R.string.dialog_yes, (dialog1, which) -> {

            mInputAdapter.removeItem(position);
            mPresenter.deleteSession(ip);

        });

        dialog.setNegativeButton(R.string.dialog_no, (dialog1, which) -> {
            mInputAdapter.notifyDataSetChanged();
            dialog1.dismiss();
        });
        dialog
                .setCancelable(false)
                .create()
                .show();

    }

    private void showEditConnectDialog(RouterSession routerSession) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setTitle(getString(R.string.edit_session) + " " + routerSession.getNameConnection());

        View viewDialog = getActivity().getLayoutInflater().inflate(R.layout.dialog_remote, null);
        dialog.setView(viewDialog);

        AppCompatCheckBox cbSave = (AppCompatCheckBox) viewDialog.findViewById(R.id.cb_save);
        AppCompatEditText etName = (AppCompatEditText) viewDialog.findViewById(R.id.input_name);
        AppCompatEditText etIp = (AppCompatEditText) viewDialog.findViewById(R.id.input_ip);
        AppCompatEditText etUsername = (AppCompatEditText) viewDialog.findViewById(R.id.input_user);
        AppCompatEditText etPassword = (AppCompatEditText) viewDialog.findViewById(R.id.input_password);

        IpFormatting.automaticIPAddressFormatting(etIp);
        cbSave.setVisibility(View.GONE);

        etIp.setText(routerSession.getIp());
        etName.setText(routerSession.getNameConnection());
        etUsername.setText(routerSession.getUsername());
        etPassword.setText(routerSession.getPassword());


        dialog.setPositiveButton(R.string.dialog_save, (dialog1, which) -> {

            String ipHost = etIp.getText().toString();
            String username = etUsername.getText().toString();
            String pass = etPassword.getText().toString();
            String nameConnection = etName.getText().toString();
            mPresenter.saveSession(ipHost, username, pass, nameConnection);

            mPresenter.getSession();

        });

        dialog.setNegativeButton(R.string.cancel, (dialog1, which) ->
        {
            mInputAdapter.notifyDataSetChanged();
            dialog1.dismiss();
        });

        dialog
                .setCancelable(false)
                .create()
                .show();


    }
}