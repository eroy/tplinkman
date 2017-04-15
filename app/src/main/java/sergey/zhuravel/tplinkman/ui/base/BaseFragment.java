package sergey.zhuravel.tplinkman.ui.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import sergey.zhuravel.tplinkman.R;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initToolbar(Toolbar mToolbar, String title,boolean homeEnable) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(homeEnable);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(homeEnable);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        }

    }

    public void navigateToNextFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_man, fragment).addToBackStack(null).commit();

    }
}
