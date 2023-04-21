package cn.com.bioeasy.healty.app.healthapp.modules.home.bean;

public class SecondClassItem {
    private int id;
    private String name;

    public SecondClassItem() {
    }

    public SecondClassItem(int id2, String name2) {
        this.id = id2;
        this.name = name2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String toString() {
        return "SecondClassItem{id=" + this.id + ", name='" + this.name + '\'' + '}';
    }
}
