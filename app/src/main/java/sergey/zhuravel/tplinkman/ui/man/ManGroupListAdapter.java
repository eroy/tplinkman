package sergey.zhuravel.tplinkman.ui.man;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.model.RouterSession;
import sergey.zhuravel.tplinkman.utils.IpFormatting;


public class ManGroupListAdapter extends BaseExpandableListAdapter {

    private List<String> mListGroupName;
    private HashMap<String, List<RouterSession>> mMapItem;


    private Context mContext;
    private ManContract.Presenter mPresenter;

    public ManGroupListAdapter(List<String> mListGroupName, HashMap<String, List<RouterSession>> mMapItem, Context mContext, ManContract.Presenter mPresenter) {
        this.mListGroupName = mListGroupName;
        this.mMapItem = mMapItem;
        this.mContext = mContext;
        this.mPresenter = mPresenter;
    }


    private void setChildGroup(int groupId, List<RouterSession> list) {
        mMapItem.put(mListGroupName.get(groupId), list);
        notifyDataSetChanged();
    }


    public int getGroupPositionByName(String name) {
        return mListGroupName.indexOf(name);
    }

    @Override
    public int getGroupCount() {
        return mListGroupName.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mMapItem.get(mListGroupName.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListGroupName.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mMapItem.get(mListGroupName.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.man_group_view, null);
        }
        String headerTitle = (String) getGroup(groupPosition);

        TextView textGroup = (TextView) view.findViewById(R.id.textGroup);
        ImageView imAdd = (ImageView) view.findViewById(R.id.imAdd);
        ImageView imMenu = (ImageView) view.findViewById(R.id.imMenu);
        textGroup.setTypeface(null, Typeface.BOLD);
        textGroup.setText(headerTitle);


        if (isExpanded) {
            imAdd.setVisibility(View.VISIBLE);
            imMenu.setVisibility(View.VISIBLE);

        } else {
            imAdd.setVisibility(View.GONE);
            imMenu.setVisibility(View.GONE);
        }

        imAdd.setOnClickListener(v -> showDialogAddChildren(groupPosition));

        imMenu.setOnClickListener(v -> showMenu(v, groupPosition));
        return view;
    }

    private void showMenu(View v, int groupId) {
        PopupMenu popup = new PopupMenu(mContext, v);
        popup.inflate(R.menu.group_menu);

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.reboot:

                    return true;
                case R.id.block_mac:

                    return true;
                case R.id.delete_group:
                    removeGroup(groupId);
                    return true;
                default:
                    return false;
            }
        });
        popup.show();
    }

    private void showDialogAddChildren(int groupId) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);

        String groupName = (String) getGroup(groupId);
        dialog.setTitle(mContext.getString(R.string.add_host_to_group) + " " + groupName);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewDialog = inflater.inflate(R.layout.dialog_remote, null);
        dialog.setView(viewDialog);

        AppCompatCheckBox cbSave = (AppCompatCheckBox) viewDialog.findViewById(R.id.cb_save);
        AppCompatEditText etName = (AppCompatEditText) viewDialog.findViewById(R.id.input_name);
        AppCompatEditText etIp = (AppCompatEditText) viewDialog.findViewById(R.id.input_ip);
        AppCompatEditText etUsername = (AppCompatEditText) viewDialog.findViewById(R.id.input_user);
        AppCompatEditText etPassword = (AppCompatEditText) viewDialog.findViewById(R.id.input_password);

        IpFormatting.automaticIPAddressFormatting(etIp);
        cbSave.setVisibility(View.GONE);


        dialog.setPositiveButton(R.string.dialog_save, (dialog1, which) -> {

            String ipHost = etIp.getText().toString();
            String username = etUsername.getText().toString();
            String pass = etPassword.getText().toString();
            String nameConnection = etName.getText().toString();
            RouterSession routerSession = new RouterSession(ipHost, username, pass, nameConnection);

            addChild(groupId, routerSession);

        });

        dialog.setNegativeButton(R.string.cancel, (dialog1, which) -> {
            notifyDataSetChanged();
            dialog1.dismiss();
        });

        dialog
                .setCancelable(false)
                .create()
                .show();

    }

    public void addGroup(String name) {
        mListGroupName.add(name);
        mMapItem.put(name, new ArrayList<>());
        notifyDataSetChanged();
    }

    public void addChildToGroup(String nameGroup, List<RouterSession> routerList) {
        int groupId = getGroupPositionByName(nameGroup);
        List<RouterSession> newList = new ArrayList<>();
        newList.addAll(mMapItem.get(mListGroupName.get(groupId)));
        newList.addAll(routerList);
        setChildGroup(groupId, newList);
        notifyDataSetChanged();
    }

    private void removeGroup(int groupId) {
        mMapItem.remove(mListGroupName.get(groupId));
        mListGroupName.remove(groupId);
        notifyDataSetChanged();

    }

    private void addChild(int groupId, RouterSession newRouteSession) {
        List<RouterSession> newList = new ArrayList<>();
        newList.addAll(mMapItem.get(mListGroupName.get(groupId)));
        newList.add(newRouteSession);
        setChildGroup(groupId, newList);
        notifyDataSetChanged();
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.man_child_view, null);
        }

        RouterSession routerSession = (RouterSession) getChild(groupPosition, childPosition);

        TextView textGroup = (TextView) convertView.findViewById(R.id.textChild);
        LinearLayout llChild = (LinearLayout) convertView.findViewById(R.id.ll_child);
        textGroup.setTypeface(null, Typeface.BOLD);
        textGroup.setText(routerSession.getIp());
        llChild.setOnClickListener(v -> {
            removeChild(groupPosition, routerSession);

        });


        return convertView;
    }

    private void removeChild(int groupPosition, RouterSession routerSession) {
        List<RouterSession> newList = new ArrayList<>();
        List<RouterSession> list = mMapItem.get(mListGroupName.get(groupPosition));
        for (RouterSession r : list) {
            if (!r.equals(routerSession)) {
                newList.add(r);
            }
        }
        setChildGroup(groupPosition, newList);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
