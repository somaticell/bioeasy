package cn.com.bioeasy.healty.app.healthapp.modules.home.bean;

import java.util.List;

public class FirstClassItem {
    private int id;
    private String name;
    private List<SecondClassItem> secondList;

    public FirstClassItem() {
    }

    public FirstClassItem(int id2, String name2, List<SecondClassItem> secondList2) {
        this.id = id2;
        this.name = name2;
        this.secondList = secondList2;
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

    public List<SecondClassItem> getSecondList() {
        return this.secondList;
    }

    public void setSecondList(List<SecondClassItem> secondList2) {
        this.secondList = secondList2;
    }

    public String toString() {
        return "FirstClassItem{id=" + this.id + ", name='" + this.name + '\'' + ", secondList=" + this.secondList + '}';
    }
}
