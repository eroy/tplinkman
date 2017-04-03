package sergey.zhuravel.tplinkman.ui.wireless;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.ui.BaseFragment;


public class WirelessFragment extends BaseFragment implements WirelessContract.View {

    private WirelessContract.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi, container, false);

        mPresenter = new WirelessPresenter(this, new WirelessModel(App.getDataManager(getActivity())));


        return view;
    }
}
