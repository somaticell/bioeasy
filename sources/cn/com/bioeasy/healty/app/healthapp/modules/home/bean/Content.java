package cn.com.bioeasy.healty.app.healthapp.modules.home.bean;

import java.io.Serializable;

public class Content implements Serializable {
    private int access;
    private String category;
    private Object committee;
    private long ctime;
    private String icon;
    private int id;
    private Object industry;
    private long mtime;
    private String refer;
    private Object referName;
    private Object region;
    private long releaseTime;
    private Object source;
    private int status;
    private String summary;
    private String title;

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public long getCtime() {
        return this.ctime;
    }

    public void setCtime(long ctime2) {
        this.ctime = ctime2;
    }

    public long getMtime() {
        return this.mtime;
    }

    public void setMtime(long mtime2) {
        this.mtime = mtime2;
    }

    public long getReleaseTime() {
        return this.releaseTime;
    }

    public void setReleaseTime(long releaseTime2) {
        this.releaseTime = releaseTime2;
    }

    public Object getSource() {
        return this.source;
    }

    public void setSource(Object source2) {
        this.source = source2;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category2) {
        this.category = category2;
    }

    public Object getCommittee() {
        return this.committee;
    }

    public void setCommittee(Object committee2) {
        this.committee = committee2;
    }

    public Object getRegion() {
        return this.region;
    }

    public void setRegion(Object region2) {
        this.region = region2;
    }

    public Object getIndustry() {
        return this.industry;
    }

    public void setIndustry(Object industry2) {
        this.industry = industry2;
    }

    public String getRefer() {
        return this.refer;
    }

    public void setRefer(String refer2) {
        this.refer = refer2;
    }

    public Object getReferName() {
        return this.referName;
    }

    public void setReferName(Object referName2) {
        this.referName = referName2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary2) {
        this.summary = summary2;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon2) {
        this.icon = icon2;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status2) {
        this.status = status2;
    }

    public int getAccess() {
        return this.access;
    }

    public void setAccess(int access2) {
        this.access = access2;
    }
}
