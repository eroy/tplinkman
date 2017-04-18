package sergey.zhuravel.tplinkman.utils;

import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;


public class IpFormatting {
    public static void automaticIPAddressFormatting(AppCompatEditText etIp) {
        etIp.setInputType(InputType.TYPE_CLASS_PHONE);

        etIp.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            if (end > start) {
                String destTxt = dest.toString();
                String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);
                if (!resultingTxt.matches("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                    return "";
                } else {
                    String[] splits = resultingTxt.split("\\.");
                    for (int i = 0; i < splits.length; i++) {
                        if (Integer.valueOf(splits[i]) > 255) {
                            return "";
                        }
                    }
                }
            }
            return null;
        }
        });


        etIp.addTextChangedListener(new TextWatcher() {
            boolean deleting = false;
            int lastCount = 0;


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (lastCount < count) {
                    deleting = false;
                } else {
                    deleting = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!deleting) {
                    String working = s.toString();
                    String[] split = working.split("\\.");
                    String string = split[split.length - 1];
                    if (string.length() == 3 || string.equalsIgnoreCase("0")) {
                        s.append('.');
                        return;
                    }
                }
            }


        });
    }
}
