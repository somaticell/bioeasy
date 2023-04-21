package cn.com.bioeasy.app.page;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {
    private List<T> rows;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total2) {
        this.total = total2;
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> rows2) {
        this.rows = rows2;
    }
}
