package sergey.zhuravel.tplinkman.ui.man;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.model.RouterSession;
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
        mPresenter = new ManPresenter(this, new ManModel());

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
            mGroupAdapter.addGroup(name.getText().toString());

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
        HashMap<String, List<RouterSession>> mapItem = new HashMap<>();
        mapItem.put(getString(R.string.default_group), new ArrayList<>());
        List<String> groupNameList = new ArrayList<>(mapItem.keySet());

        mGroupAdapter = new ManGroupListAdapter(groupNameList, mapItem, getActivity(), mPresenter);
        mListGroup.setAdapter(mGroupAdapter);
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
