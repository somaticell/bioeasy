package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;
import java.util.List;

public class CategoryNode implements Serializable {
    private List<CategoryNode> children;
    private Integer id;
    private Integer level;
    private String name;
    private Integer navi;
    private Integer parentId;
    private String pathName;
    private Integer status;

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level2) {
        this.level = level2;
    }

    public Integer getNavi() {
        return this.navi;
    }

    public void setNavi(Integer navi2) {
        this.navi = navi2;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status2) {
        this.status = status2;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId2) {
        this.parentId = parentId2;
    }

    public List<CategoryNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<CategoryNode> children2) {
        this.children = children2;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id2) {
        this.id = id2;
    }

    public String getPathName() {
        return this.pathName;
    }

    public void setPathName(String pathName2) {
        this.pathName = pathName2;
    }
}
