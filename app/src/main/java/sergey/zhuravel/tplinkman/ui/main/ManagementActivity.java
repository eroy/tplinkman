package sergey.zhuravel.tplinkman.ui.main;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import sergey.zhuravel.tplinkman.Const;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.fragment.AppFragment;
import sergey.zhuravel.tplinkman.ui.info.InfoFragment;
import sergey.zhuravel.tplinkman.fragment.FragmentWan;
import sergey.zhuravel.tplinkman.fragment.FragmentWifi;
import sergey.zhuravel.tplinkman.ui.setting.SettingFragment;


public class ManagementActivity extends AppCompatActivity implements Const {
    private BottomNavigationView bottomNavigationView;
    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppFragment appFragment = new AppFragment();
        appFragment.setSettingsRouter(getApplicationContext(), data, TYPE_LOGOUT, new ArrayList<String>());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.management_activity);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);




//        get value with previous activity
        data = getIntent().getStringArrayListExtra("arrayData");


//        start fragment info
        goFragment(new InfoFragment());


//        config bnv
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_info:
                    goFragment(new InfoFragment());
                    break;
                case R.id.action_wifi:
                    goFragment(new FragmentWifi());
                    break;
                case R.id.action_wan:
                    goFragment(new SettingFragment());
                    break;

            }


            return false;
        });


    }


    private void goFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data", data);
        fragment.setArguments(bundle);
        ManagementActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame_man, fragment).commit();
    }
}
