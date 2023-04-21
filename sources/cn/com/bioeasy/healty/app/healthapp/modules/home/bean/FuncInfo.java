package cn.com.bioeasy.healty.app.healthapp.modules.home.bean;

public class FuncInfo {
    private int icon;
    private int index;
    private String name;
    private Class page;

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setIcon(int icon2) {
        this.icon = icon2;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index2) {
        this.index = index2;
    }

    public Class getPage() {
        return this.page;
    }

    public void setPage(Class page2) {
        this.page = page2;
    }

    public static FuncInfo create(int index2, String name2, int icon2, Class page2) {
        FuncInfo info = new FuncInfo();
        info.index = index2;
        info.name = name2;
        info.icon = icon2;
        info.page = page2;
        return info;
    }
}
