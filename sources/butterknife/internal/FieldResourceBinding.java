package butterknife.internal;

final class FieldResourceBinding {
    private final int id;
    private final String method;
    private final String name;

    FieldResourceBinding(int id2, String name2, String method2) {
        this.id = id2;
        this.name = name2;
        this.method = method2;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getMethod() {
        return this.method;
    }
}
