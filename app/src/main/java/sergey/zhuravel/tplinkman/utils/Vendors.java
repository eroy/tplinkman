package sergey.zhuravel.tplinkman.utils;

import java.util.ArrayList;
import java.util.List;


public class Vendors {

    public static boolean isTpLink(String mac) {
        String searchMac = getMacForSearchVendor(mac);
        for (String macVendor : getVendorMacs()) {
            if (macVendor.equals(searchMac)) {
                return true;
            }
        }
        return false;
    }

    private static String getMacForSearchVendor(String mac) {
        return mac.replace(":", "").substring(0, 6).toUpperCase();
    }

    private static List<String> getVendorMacs() {

        List<String> vendorList = new ArrayList<>();
        vendorList.add("E005C5");
        vendorList.add("A0F3C1");
        vendorList.add("8C210A");
        vendorList.add("EC172F");
        vendorList.add("EC888F");
        vendorList.add("14CF92");
        vendorList.add("645601");
        vendorList.add("14CC20");
        vendorList.add("BC4699");
        vendorList.add("3C46D8");
        vendorList.add("54C80F");
        vendorList.add("CC3429");
        vendorList.add("D4016D");
        vendorList.add("EC086B");
        vendorList.add("18A6F7");
        vendorList.add("246968");
        vendorList.add("30B49E");
        vendorList.add("000AEB");
        vendorList.add("C025E9");
        vendorList.add("704F57");
        vendorList.add("B04E26");
        vendorList.add("001D0F");
        vendorList.add("5C63BF");
        vendorList.add("B0487A");
        vendorList.add("388345");
        vendorList.add("14E6E4");
        vendorList.add("647002");
        vendorList.add("6466B3");
        vendorList.add("6CE873");
        vendorList.add("30B5C2");
        vendorList.add("9C216A");
        vendorList.add("E8DE27");
        vendorList.add("148692");
        vendorList.add("1CFA68");
        vendorList.add("BCD177");
        vendorList.add("C04A00");
        vendorList.add("081F71");
        vendorList.add("D46E0E");
        vendorList.add("C0E42D");
        vendorList.add("18D6C7");
        vendorList.add("B8F883");
        vendorList.add("DCFE18");
        vendorList.add("206BE7");
        vendorList.add("0019E0");
        vendorList.add("0023CD");
        vendorList.add("002719");
        vendorList.add("40169F");
        vendorList.add("940C6D");
        vendorList.add("74EA3A");
        vendorList.add("90F652");
        vendorList.add("10FEED");
        vendorList.add("C46E1F");
        vendorList.add("50FA84");
        vendorList.add("F483CD");
        vendorList.add("882593");
        vendorList.add("808917");
        vendorList.add("5C899A");
        vendorList.add("50BD5F");
        vendorList.add("147590");
        vendorList.add("60E327");
        vendorList.add("E4D332");
        vendorList.add("085700");
        vendorList.add("D8150D");
        vendorList.add("0C722C");
        vendorList.add("A8154D");
        vendorList.add("78A106");
        vendorList.add("282CB2");
        vendorList.add("8CA6DF");
        vendorList.add("001478");
        vendorList.add("28EE52");
        vendorList.add("349672");
        vendorList.add("487D2E");
        vendorList.add("FCD733");
        vendorList.add("002127");
        vendorList.add("54E6FC");
        vendorList.add("D85D4C");
        vendorList.add("F81A67");
        vendorList.add("F0F336");
        vendorList.add("44B32D");
        vendorList.add("90AE1B");
        vendorList.add("C4E984");
        vendorList.add("E894F6");
        vendorList.add("DC0077");
        vendorList.add("B0958E");
        vendorList.add("7C8BCA");
        vendorList.add("A42BB0");
        vendorList.add("EC26CA");
        vendorList.add("002586");
        vendorList.add("F8D111");
        vendorList.add("F4EC38");
        vendorList.add("20DCE6");
        vendorList.add("F4F26D");
        vendorList.add("50C7BF");
        vendorList.add("C06118");
        vendorList.add("D0C7C0");
        vendorList.add("A8574E");
        vendorList.add("0C8268");
        vendorList.add("1C4419");
        vendorList.add("403F8C");
        vendorList.add("98DED0");
        vendorList.add("30FC68");
        vendorList.add("8416F9");
        return vendorList;

    }

}
