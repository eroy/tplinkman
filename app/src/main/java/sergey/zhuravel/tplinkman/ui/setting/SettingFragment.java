package sergey.zhuravel.tplinkman.ui.setting;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.ui.base.BaseFragment;
import sergey.zhuravel.tplinkman.ui.internet.InternetFragment;
import sergey.zhuravel.tplinkman.ui.start.StartActivity;
import sergey.zhuravel.tplinkman.ui.wireless.WirelessFragment;

public class SettingFragment extends BaseFragment implements SettingContract.View {

    private SettingContract.Presenter mPresenter;
    private RelativeLayout mRlWireless;
    private RelativeLayout mRlWan;
    private RelativeLayout mRlDhcp;
    private RelativeLayout mRlRemote;
    private RelativeLayout mRlReboot;
    private Toolbar mToolbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        initView(view);

        mPresenter = new SettingPresenter(this, new SettingModel(App.getDataManager(getActivity())));

        mRlWireless.setOnClickListener(v -> navigateToNextFragment(new WirelessFragment()));
        mRlReboot.setOnClickListener(v -> showAlertDialog());
        mRlWan.setOnClickListener(v -> navigateToNextFragment(new InternetFragment()));

        return view;
    }

    private void initView(View view) {
        mRlWireless = (RelativeLayout) view.findViewById(R.id.rl_wireless);
        mRlWan = (RelativeLayout) view.findViewById(R.id.rl_wan);
        mRlDhcp = (RelativeLayout) view.findViewById(R.id.rl_dhcp);
        mRlRemote = (RelativeLayout) view.findViewById(R.id.rl_remote);
        mRlReboot = (RelativeLayout) view.findViewById(R.id.rl_reboot);

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        initToolbar(mToolbar, "Settings", false);
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.alert_message);

        builder
                .setPositiveButton(R.string.alert_yes, (dialog, which) -> mPresenter.setRebootRouter())
                .setNegativeButton(R.string.alert_no, (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }



    @Override
    public void navigateToStartActivity() {
        startActivity(new Intent(getActivity(), StartActivity.class));
        getActivity().finish();
    }

    @Override
    public void showRebootSuccessToast() {
        Toast.makeText(getActivity(), R.string.reboot_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRebootErrorToast() {
        Toast.makeText(getActivity(), R.string.reboot_error, Toast.LENGTH_SHORT).show();
    }
}
