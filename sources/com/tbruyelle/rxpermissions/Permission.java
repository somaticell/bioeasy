package com.tbruyelle.rxpermissions;

public class Permission {
    public final boolean granted;
    public final String name;

    public Permission(String name2, boolean granted2) {
        this.name = name2;
        this.granted = granted2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Permission that = (Permission) o;
        if (this.granted == that.granted) {
            return this.name.equals(that.name);
        }
        return false;
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + (this.granted ? 1 : 0);
    }

    public String toString() {
        return "Permission{name='" + this.name + '\'' + ", granted=" + this.granted + '}';
    }
}
