package cn.com.bioeasy.healty.app.healthapp.modules.test.bean;

import java.io.Serializable;

public class TestStripCategory implements Serializable {
    private String color;
    private int icon;
    private int id;
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setIcon(int icon2) {
        this.icon = icon2;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color2) {
        this.color = color2;
    }
}
