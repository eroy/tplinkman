package sergey.zhuravel.tplinkman.ui.start;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.ui.base.BaseActivity;
import sergey.zhuravel.tplinkman.ui.input.InputFragment;

public class StartActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initView();

        navigateToInputFragment();

    }


    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.start_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.start_nav);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);


        setNavigationItemSelectedListener();

        initToolbar(mToolbar, getString(R.string.app_name), true);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        mNavigationView.getMenu().getItem(0).setChecked(true);


    }

    private void setNavigationItemSelectedListener() {
        mNavigationView.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            switch (item.getItemId()) {
                case R.id.item_input:
                    navigateToInputFragment();
                    break;
                case R.id.item_management:
                    navigateToManagementFragment();
                    break;

            }
            mDrawerLayout.closeDrawers();
            return false;
        });
    }

    private void navigateToManagementFragment() {
//        to do
    }

    private void navigateToInputFragment() {
        replaceFragment(new InputFragment());
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_start, fragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            default:
                break;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
