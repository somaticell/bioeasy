package jxl.biff;

import common.Logger;

public final class IndexMapping {
    static Class class$jxl$biff$IndexMapping;
    private static Logger logger;
    private int[] newIndices;

    static {
        Class cls;
        if (class$jxl$biff$IndexMapping == null) {
            cls = class$("jxl.biff.IndexMapping");
            class$jxl$biff$IndexMapping = cls;
        } else {
            cls = class$jxl$biff$IndexMapping;
        }
        logger = Logger.getLogger(cls);
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    IndexMapping(int size) {
        this.newIndices = new int[size];
    }

    /* access modifiers changed from: package-private */
    public void setMapping(int oldIndex, int newIndex) {
        this.newIndices[oldIndex] = newIndex;
    }

    public int getNewIndex(int oldIndex) {
        return this.newIndices[oldIndex];
    }
}
