package sergey.zhuravel.tplinkman.ui.client;


import android.app.AlertDialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.model.Client;
import sergey.zhuravel.tplinkman.ui.base.BaseFragment;
import sergey.zhuravel.tplinkman.ui.block.BlockFragment;


public class ClientFragment extends BaseFragment implements ClientContract.View {

    private ClientContract.Presenter mPresenter;
    private Toolbar mToolbar;
    private ClientAdapter mClientAdapter;
    private RecyclerView mRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        Log.e("DESTROY-clients", "onDestroy");
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_client, container, false);

        initView(view);

        mPresenter = new ClientPresenter(this, new ClientModel(App.getDataManager(getActivity())));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mClientAdapter = new ClientAdapter(mPresenter);
        mRecyclerView.setAdapter(mClientAdapter);

        mPresenter.getWifiStationInfo();

        mPresenter.updateClientList();


        initSwipeBlock();
        return view;
    }


    @Override
    public void addClientToList(List<Client> list) {
        mClientAdapter.addClients(list);
    }

    @Override
    public void clearClientList() {
        mClientAdapter.clearClients();
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_client);


        initToolbar(mToolbar, "Client list", false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_client_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.block_list:
                mPresenter.onDestroy();
                navigateToNextFragment(new BlockFragment());
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    private void initSwipeBlock() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            int xMarkMargin;
            Drawable xMark;
            boolean initiated;

            private void init() {
                xMark = ContextCompat.getDrawable(getActivity(), R.drawable.ic_block_white_24dp);
                xMarkMargin = (int) getActivity().getResources().getDimension(R.dimen.ic_clear_margin);
                initiated = true;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want blocked this user ?");

                builder.setPositiveButton("Block", (dialog, which) -> {

                    mPresenter.blockClient(viewHolder.getLayoutPosition());
                    mClientAdapter.notifyDataSetChanged();

                }).setNegativeButton("Cancel", (dialog, which) -> {
                    mClientAdapter.notifyDataSetChanged();
                    dialog.dismiss();

                }).setCancelable(false).show();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;


                if (!initiated) {
                    init();
                }
                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                xMark.draw(c);


                //Setting Swipe Text
                Paint paint = new Paint();
                paint.setColor(Color.parseColor("#327780"));
                paint.setTextSize(40);
                paint.setTextAlign(Paint.Align.CENTER);
                c.drawText("Block", xMarkLeft + 20, xMarkTop + 80, paint);


                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

}
