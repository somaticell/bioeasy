package cn.com.bioeasy.app.event;

public class NormalEvent {
    String id;
    String name;

    public NormalEvent(String name2) {
        this.id = "defaultId";
        this.name = name2;
    }

    public NormalEvent(String id2, String name2) {
        this.id = id2;
        this.name = name2;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public void setName(String name2) {
        this.name = name2;
    }
}
