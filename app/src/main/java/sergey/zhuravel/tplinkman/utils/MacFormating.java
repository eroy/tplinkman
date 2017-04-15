package sergey.zhuravel.tplinkman.utils;


import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MacFormating {

    public static void automaticMacAddressFormating(AppCompatEditText etMac) {
        etMac.addTextChangedListener(new TextWatcher() {
            String mPreviousMac = null;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String enteredMac = etMac.getText().toString().toUpperCase();
                String cleanMac = clearNonMacCharacters(enteredMac);
                String formattedMac = formatMacAddress(cleanMac);

                int selectionStart = etMac.getSelectionStart();
                formattedMac = handleColonDeletion(enteredMac, formattedMac, selectionStart);
                int lengthDiff = formattedMac.length() - enteredMac.length();

                setMacEdit(cleanMac, formattedMac, selectionStart, lengthDiff);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            /**
             * Strips all characters from a string except A-F and 0-9.
             * @param mac       User input string.
             * @return String containing MAC-allowed characters.
             */
            private String clearNonMacCharacters(String mac) {
                return mac.toString().replaceAll("[^A-Fa-f0-9]", "");
            }

            /**
             * Adds a colon character to an unformatted MAC address after
             * every second character (strips full MAC trailing colon)
             * @param cleanMac      Unformatted MAC address.
             * @return Properly formatted MAC address.
             */
            private String formatMacAddress(String cleanMac) {
                int grouppedCharacters = 0;
                String formattedMac = "";

                for (int i = 0; i < cleanMac.length(); ++i) {
                    formattedMac += cleanMac.charAt(i);
                    ++grouppedCharacters;

                    if (grouppedCharacters == 2) {
                        formattedMac += "-";
                        grouppedCharacters = 0;
                    }
                }

                // Removes trailing colon for complete MAC address
                if (cleanMac.length() == 12)
                    formattedMac = formattedMac.substring(0, formattedMac.length() - 1);

                return formattedMac;
            }

            /**
             * Upon users colon deletion, deletes MAC character preceding deleted colon as well.
             * @param enteredMac            User input MAC.
             * @param formattedMac          Formatted MAC address.
             * @param selectionStart        MAC EditText field cursor position.
             * @return Formatted MAC address.
             */
            private String handleColonDeletion(String enteredMac, String formattedMac, int selectionStart) {
                if (mPreviousMac != null && mPreviousMac.length() > 1) {
                    int previousColonCount = colonCount(mPreviousMac);
                    int currentColonCount = colonCount(enteredMac);

                    if (currentColonCount < previousColonCount) {
                        formattedMac = formattedMac.substring(0, selectionStart - 1) + formattedMac.substring(selectionStart);
                        String cleanMac = clearNonMacCharacters(formattedMac);
                        formattedMac = formatMacAddress(cleanMac);
                    }
                }
                return formattedMac;
            }

            /**
             * Gets MAC address current colon count.
             * @param formattedMac      Formatted MAC address.
             * @return Current number of colons in MAC address.
             */
            private int colonCount(String formattedMac) {
                return formattedMac.replaceAll("[^-]", "").length();
            }

            /**
             * Removes TextChange listener, sets MAC EditText field value,
             * sets new cursor position and re-initiates the listener.
             * @param cleanMac          Clean MAC address.
             * @param formattedMac      Formatted MAC address.
             * @param selectionStart    MAC EditText field cursor position.
             * @param lengthDiff        Formatted/Entered MAC number of characters difference.
             */
            private void setMacEdit(String cleanMac, String formattedMac, int selectionStart, int lengthDiff) {
                etMac.removeTextChangedListener(this);
                if (cleanMac.length() <= 12) {
                    etMac.setText(formattedMac);
                    etMac.setSelection(selectionStart + lengthDiff);
                    mPreviousMac = formattedMac;
                } else {
                    etMac.setText(mPreviousMac);
                    etMac.setSelection(mPreviousMac.length());
                }
                etMac.addTextChangedListener(this);
            }


        });
    }

    public static boolean macValidate(String inputMac) {
        String mac = inputMac.toLowerCase();
        String ch = "0123456789abcdef";

        Pattern p = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");
        Matcher m = p.matcher(mac);
        if (m.find()) {
            if (mac.equals("ff-ff-ff-ff-ff-ff")) {
                return false;
            }
            if (mac.equals("00-00-00-00-00-00")) {
                return false;
            }

            char c = mac.charAt(1);
            if (ch.indexOf(c) % 2 == 1) {
                return false;
            }

            return true;


        } else {
            return false;
        }
    }


}
