package jxl.write.biff;

import android.support.v7.widget.ActivityChooserView;
import common.Logger;
import jxl.biff.FormatRecord;

public class NumberFormatRecord extends FormatRecord {
    static Class class$jxl$write$biff$NumberFormatRecord;
    private static Logger logger;

    static {
        Class cls;
        if (class$jxl$write$biff$NumberFormatRecord == null) {
            cls = class$("jxl.write.biff.NumberFormatRecord");
            class$jxl$write$biff$NumberFormatRecord = cls;
        } else {
            cls = class$jxl$write$biff$NumberFormatRecord;
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

    protected NumberFormatRecord(String fmt) {
        setFormatString(trimInvalidChars(replace(fmt, "E0", "E+0")));
    }

    private String trimInvalidChars(String fs) {
        int firstHash = fs.indexOf(35);
        int firstZero = fs.indexOf(48);
        if (firstHash == -1 && firstZero == -1) {
            return "#.###";
        }
        if (!(firstHash == 0 || firstZero == 0 || firstHash == 1 || firstZero == 1)) {
            if (firstHash == -1) {
                firstHash = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            } else {
                int i = firstHash;
            }
            if (firstZero == -1) {
                firstZero = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            } else {
                int i2 = firstZero;
            }
            int firstValidChar = Math.min(firstHash, firstZero);
            StringBuffer tmp = new StringBuffer();
            tmp.append(fs.charAt(0));
            tmp.append(fs.substring(firstValidChar));
            fs = tmp.toString();
        }
        int lastHash = fs.lastIndexOf(35);
        int lastZero = fs.lastIndexOf(48);
        if (lastHash == fs.length() || lastZero == fs.length()) {
            return fs;
        }
        int lastValidChar = Math.max(lastHash, lastZero);
        while (fs.length() > lastValidChar + 1 && (fs.charAt(lastValidChar + 1) == ')' || fs.charAt(lastValidChar + 1) == '%')) {
            lastValidChar++;
        }
        return fs.substring(0, lastValidChar + 1);
    }
}
