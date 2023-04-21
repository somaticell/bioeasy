package jxl.biff;

import common.Logger;
import java.io.UnsupportedEncodingException;
import jxl.WorkbookSettings;

public final class StringHelper {
    static Class class$jxl$biff$StringHelper;
    private static Logger logger;

    static {
        Class cls;
        if (class$jxl$biff$StringHelper == null) {
            cls = class$("jxl.biff.StringHelper");
            class$jxl$biff$StringHelper = cls;
        } else {
            cls = class$jxl$biff$StringHelper;
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

    private StringHelper() {
    }

    public static byte[] getBytes(String s) {
        return s.getBytes();
    }

    public static byte[] getBytes(String s, WorkbookSettings ws) {
        try {
            return s.getBytes(ws.getEncoding());
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static byte[] getUnicodeBytes(String s) {
        try {
            byte[] b = s.getBytes("UnicodeLittle");
            if (b.length != (s.length() * 2) + 2) {
                return b;
            }
            byte[] b2 = new byte[(b.length - 2)];
            System.arraycopy(b, 2, b2, 0, b2.length);
            return b2;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static void getBytes(String s, byte[] d, int pos) {
        byte[] b = getBytes(s);
        System.arraycopy(b, 0, d, pos, b.length);
    }

    public static void getUnicodeBytes(String s, byte[] d, int pos) {
        byte[] b = getUnicodeBytes(s);
        System.arraycopy(b, 0, d, pos, b.length);
    }

    public static String getString(byte[] d, int length, int pos, WorkbookSettings ws) {
        try {
            byte[] b = new byte[length];
            System.arraycopy(d, pos, b, 0, length);
            return new String(b, ws.getEncoding());
        } catch (UnsupportedEncodingException e) {
            logger.warn(e.toString());
            return "";
        }
    }

    public static String getUnicodeString(byte[] d, int length, int pos) {
        try {
            byte[] b = new byte[(length * 2)];
            System.arraycopy(d, pos, b, 0, length * 2);
            return new String(b, "UnicodeLittle");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
