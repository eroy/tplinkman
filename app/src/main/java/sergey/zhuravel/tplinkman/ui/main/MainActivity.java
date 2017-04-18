package sergey.zhuravel.tplinkman.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.ui.base.BaseActivity;
import sergey.zhuravel.tplinkman.ui.client.ClientFragment;
import sergey.zhuravel.tplinkman.ui.info.InfoFragment;
import sergey.zhuravel.tplinkman.ui.setting.SettingFragment;
import sergey.zhuravel.tplinkman.ui.start.StartActivity;


public class MainActivity extends BaseActivity implements MainContract.View, MacDevice {
    private BottomNavigationView bottomNavigationView;

    private MainContract.Presenter mPresenter;

    @Override
    protected void onDestroy() {
        mPresenter.setLogout();
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.management_activity);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mPresenter = new MainPresenter(this, new MainModel(App.getDataManager(this)));


//        start fragment info
        goFragment(new InfoFragment());


//        config bnv
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_info:
                    goFragment(new InfoFragment());
                    return true;

                case R.id.action_wifi:
                    goFragment(new ClientFragment());
                    return true;

                case R.id.action_wan:
                    goFragment(new SettingFragment());
                    return true;

            }


            return false;
        });


    }

    @Override
    public void showLogoutToast() {
        Toast.makeText(this, R.string.logout_success, Toast.LENGTH_SHORT).show();
    }

    private void goFragment(Fragment fragment) {
        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame_man, fragment).commit();
    }

    @Override
    public void navigateToStartActivity() {
        startActivity(new Intent(this, StartActivity.class));
        finish();
    }

    @Override
    public String getMacDevice() {
        @SuppressLint("WifiManagerPotentialLeak")
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo().getMacAddress();
    }
}
