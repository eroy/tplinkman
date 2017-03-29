package sergey.zhuravel.tplinkman.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
}
