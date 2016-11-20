package sergey.zhuravel.tplinkman.model;

/**
 * Created by serj on 18.11.16.
 */

public class Info {
    private String titleName;
    private String value;

    public Info() {
    }

    public Info(String titleName, String value) {
        this.titleName = titleName;
        this.value = value;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
