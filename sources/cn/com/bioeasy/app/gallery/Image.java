package cn.com.bioeasy.app.gallery;

import java.io.Serializable;

public class Image implements Serializable {
    private long date;
    private String folderName;
    private int id;
    private boolean isSelect;
    private String name;
    private String path;
    private String thumbPath;

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path2) {
        this.path = path2;
    }

    public String getThumbPath() {
        return this.thumbPath;
    }

    public void setThumbPath(String thumbPath2) {
        this.thumbPath = thumbPath2;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setSelect(boolean select) {
        this.isSelect = select;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String folderName2) {
        this.folderName = folderName2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date2) {
        this.date = date2;
    }

    public boolean equals(Object o) {
        if (o instanceof Image) {
            return this.path.equals(((Image) o).getPath());
        }
        return false;
    }
}
