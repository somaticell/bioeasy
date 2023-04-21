package cn.com.bioeasy.app.gallery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImageFolder implements Serializable {
    private String albumPath;
    private List<Image> images = new ArrayList();
    private String name;
    private String path;

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path2) {
        this.path = path2;
    }

    public List<Image> getImages() {
        return this.images;
    }

    public String getAlbumPath() {
        return this.albumPath;
    }

    public void setAlbumPath(String albumPath2) {
        this.albumPath = albumPath2;
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof ImageFolder)) {
            return false;
        }
        if (((ImageFolder) o).getPath() != null || this.path == null) {
            return ((ImageFolder) o).getPath().toLowerCase().equals(this.path.toLowerCase());
        }
        return false;
    }
}
