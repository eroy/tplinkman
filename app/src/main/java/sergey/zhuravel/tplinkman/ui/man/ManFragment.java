package sergey.zhuravel.tplinkman.ui.man;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.model.ManRouter;
import sergey.zhuravel.tplinkman.ui.base.BaseFragment;

public class ManFragment extends BaseFragment implements ManContract.View {

    private ManContract.Presenter mPresenter;
    private ExpandableListView mListGroup;
    private ManGroupListAdapter mGroupAdapter;
    private FloatingActionButton mAddFab;


    public ManFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_man, container, false);
        mPresenter = new ManPresenter(this, new ManModel(App.getDataManager(getActivity()), App.getRealmManager()));

        initView(view);
        initAdapter();


        mAddFab.setOnClickListener(v -> showDialogAddGroup());

        return view;
    }

    private void showDialogAddGroup() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(R.string.add_new_group);
        View viewDialog = getActivity().getLayoutInflater().inflate(R.layout.dialog_new_group, null);
        dialog.setView(viewDialog);
        AppCompatEditText name = (AppCompatEditText) viewDialog.findViewById(R.id.et_name);


        dialog.setPositiveButton(R.string.dialog_add, (dialog1, which) -> {
            String groupName = name.getText().toString();
            ManRouter manRouter = mPresenter.getManRouterByName(groupName);
            if (manRouter != null) {
                Toast.makeText(getActivity(), "This name is used", Toast.LENGTH_SHORT).show();
            } else {
                mGroupAdapter.addGroup(groupName);
                mPresenter.saveManRouters(groupName, null);
            }
        });

        dialog.setNegativeButton(R.string.cancel, (dialog1, which) -> {
            dialog1.dismiss();
        });
        dialog
                .setCancelable(false)
                .create()
                .show();

    }

    private void initAdapter() {
        mGroupAdapter = new ManGroupListAdapter(new ArrayList<>(), new HashMap<>(), getActivity(), mPresenter);
        mListGroup.setAdapter(mGroupAdapter);

        mPresenter.getManRouters();
    }


    @Override
    public void addGroup(String groupName) {
        mGroupAdapter.addGroup(groupName);
    }

    @Override
    public void addChildToGroup(ManRouter manRouter) {
        mGroupAdapter.addChildToGroup(manRouter);
    }


    private void initView(View view) {
        mListGroup = (ExpandableListView) view.findViewById(R.id.exListView);
        mAddFab = (FloatingActionButton) view.findViewById(R.id.add_fab);

    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
}
