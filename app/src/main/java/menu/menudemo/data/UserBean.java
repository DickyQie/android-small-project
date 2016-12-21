package menu.menudemo.data;

import java.io.Serializable;

/**
 * Created by zq on 2016/10/23.
 * 33项对象
 */

public class UserBean implements Serializable {

    private int img;
    private String title;
    private boolean check;//复选框是否选中
    private boolean isvisibility;//是否显示复选框
    public UserBean() {
        // TODO Auto-generated constructor stub
    }

    public UserBean(int img, String title, boolean check, boolean isvisibility) {
        super();
        this.img = img;
        this.title = title;
        this.check=check;
        this.isvisibility=isvisibility;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isvisibility() {
        return isvisibility;
    }

    public void setIsvisibility(boolean isvisibility) {
        this.isvisibility = isvisibility;
    }

}
