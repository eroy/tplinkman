package sergey.zhuravel.tplinkman.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import sergey.zhuravel.tplinkman.MainActivity;
import sergey.zhuravel.tplinkman.R;

public class ManagementDevice extends AppFragment {

    ArrayList<String> data =new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_managment_device, container, false);

        data = getArguments().getStringArrayList("data");
        Log.e("Sergey", String.valueOf(data));
        Log.e("Sergey", data.get(0));
        Log.e("Sergey", data.get(1));
        Log.e("Sergey", data.get(2));
        Log.e("Sergey", data.get(3));

        Button button = (Button) view.findViewById(R.id.btnReboot);
        final TextView text = (TextView) view.findViewById(R.id.text12);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = asyncWifi(data,TYPE_REBBOT,"");
                if (t.equals("error")) {
                    text.setText("Error code");
                }
                else {
                    text.setText("Successes reboot " + t);

                    try {
                        Thread.sleep(7000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }
            }
        });






        return view;
    }

}
