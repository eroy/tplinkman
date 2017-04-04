package sergey.zhuravel.tplinkman.ui.wireless;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.ui.BaseFragment;


public class WirelessFragment extends BaseFragment implements WirelessContract.View {

    private WirelessContract.Presenter mPresenter;

    private TextView mSsid;
    private TextView mRegional;
    private TextView mChanel;
    private TextView mPassword;

    private ImageView mStatusSec;
    private RelativeLayout mRlPassword;
    private LinearLayout mLlInfoWifi;
    private LinearLayout mLlInfoWifiSec;
    private Toolbar mToolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi, container, false);
        initView(view);
        mPresenter = new WirelessPresenter(this, new WirelessModel(App.getDataManager(getActivity())));


        mPresenter.setWifiParameters();
        mPresenter.setWifiSecurity();

        return view;
    }

    private void initView(View view) {
        mSsid = (TextView) view.findViewById(R.id.ssid);
        mChanel = (TextView) view.findViewById(R.id.textChanel);
        mRegional = (TextView) view.findViewById(R.id.textRegional);
        mStatusSec = (ImageView) view.findViewById(R.id.textStatusSec);
        mPassword = (TextView) view.findViewById(R.id.textPassword);
        mLlInfoWifi = (LinearLayout) view.findViewById(R.id.llInfoWifi);
        mLlInfoWifiSec = (LinearLayout) view.findViewById(R.id.llInfoWifiSec);
        mRlPassword = (RelativeLayout) view.findViewById(R.id.rlPassword);

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        initToolbar(mToolbar, "Wireless Settings", true);
    }

    @Override
    public void setInfoWifiParameters(String ssid, String chanel, String regional) {
        mSsid.setText(ssid);
        mChanel.setText(chanel);
        mRegional.setText(regional);
    }

    @Override
    public void setInfoWifiSecurity(String password) {
        mPassword.setText(password);
    }

    @Override
    public void statusSecurityAccessibility(boolean enable) {
        if (enable) {
            mStatusSec.setImageResource(R.drawable.ic_lock_open_black_24dp);
            mRlPassword.setVisibility(View.GONE);
        } else {
            mStatusSec.setImageResource(R.drawable.ic_lock_outline_black_24dp);
            mRlPassword.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}


