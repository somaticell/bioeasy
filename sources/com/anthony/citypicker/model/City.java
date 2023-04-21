package com.anthony.citypicker.model;

public class City {
    private String name;
    private String pinyin;

    public City() {
    }

    public City(String name2, String pinyin2) {
        this.name = name2;
        this.pinyin = pinyin2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String pinyin2) {
        this.pinyin = pinyin2;
    }
}
