package sergey.zhuravel.tplinkman.ui.main;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.fragment.FragmentWifi;
import sergey.zhuravel.tplinkman.ui.info.InfoFragment;
import sergey.zhuravel.tplinkman.ui.setting.SettingFragment;


public class ManagementActivity extends AppCompatActivity implements MainContract.View {
    private BottomNavigationView bottomNavigationView;
    private ArrayList<String> data = new ArrayList<>();
    private MainContract.Presenter mPresenter;

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.e("SERJ","ManagementActivity -- onStop");
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.management_activity);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mPresenter = new MainPresenter(this, new MainModel(App.getDataManager(this)));

//        get value with previous activity
        data = getIntent().getStringArrayListExtra("arrayData");


//        start fragment info
        goFragment(new InfoFragment());


//        config bnv
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_info:
                    goFragment(new InfoFragment());
                    return true;

                case R.id.action_wifi:
                    goFragment(new FragmentWifi());
                    return true;

                case R.id.action_wan:
                    goFragment(new SettingFragment());
                    return true;

            }


            return false;
        });


    }

    @Override
    public void setToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void goFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data", data);
        fragment.setArguments(bundle);
        ManagementActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame_man, fragment).commit();
    }
}
