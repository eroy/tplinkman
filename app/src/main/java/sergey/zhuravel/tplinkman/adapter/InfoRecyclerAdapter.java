package sergey.zhuravel.tplinkman.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.model.Info;


public class InfoRecyclerAdapter extends RecyclerView.Adapter<InfoRecyclerAdapter.RVHolder> {
    private List<Info> infoList;
    private Context context;

    public InfoRecyclerAdapter(Context context, List<Info> infoList) {
        this.context = context;
        this.infoList = infoList;
    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_info, null);

        return new RVHolder(view);
    }

    @Override
    public void onBindViewHolder(RVHolder holder, int position) {
        final Info info = infoList.get(position);


        holder.titleName.setText(info.getTitleName());
        holder.value.setText(info.getValue());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,info.getValue(),Snackbar.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class RVHolder extends RecyclerView.ViewHolder {
        TextView titleName;
        TextView value;
        RelativeLayout rl;

        public RVHolder(View view) {
            super(view);
            titleName = (TextView) view.findViewById(R.id.name);
            value = (TextView) view.findViewById(R.id.value);
            rl = (RelativeLayout) view.findViewById(R.id.rl);



        }
    }
}
