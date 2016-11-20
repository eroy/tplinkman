package sergey.zhuravel.tplinkman;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import sergey.zhuravel.tplinkman.fragment.AppFragment;
import sergey.zhuravel.tplinkman.fragment.FragmentInfo;
import sergey.zhuravel.tplinkman.fragment.FragmentWifi;
import sergey.zhuravel.tplinkman.model.WifiInfo;

import static sergey.zhuravel.tplinkman.Const.INFO_WIFI;
import static sergey.zhuravel.tplinkman.Const.INFO_WIFI_SEC;


public class ManagementActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.management_activity);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);


//        get value with previous activity
        data = getIntent().getStringArrayListExtra("arrayData");



//        start fragment info
        goFragmentInfo();


//        config bnv
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_info:
                        goFragmentInfo();

                        break;
                    case R.id.action_wifi:
                        goFragmentWifi();

                        break;


                }


                return false;
            }
        });


    }

    private void goFragmentInfo() {
        FragmentInfo fragmentInfo = new FragmentInfo();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data", data);
        fragmentInfo.setArguments(bundle);
        ManagementActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame_man, fragmentInfo).commit();
    }

    private void goFragmentWifi() {
        FragmentWifi fragmentWifi = new FragmentWifi();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data", data);
        fragmentWifi.setArguments(bundle);
        ManagementActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame_man, fragmentWifi).commit();
    }

}