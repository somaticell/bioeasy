package jxl.biff;

import android.support.v7.widget.ActivityChooserView;
import common.Logger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import jxl.WorkbookSettings;
import jxl.format.Format;
import jxl.read.biff.Record;

public class FormatRecord extends WritableRecordData implements DisplayFormat, Format {
    public static final BiffType biff7 = new BiffType((AnonymousClass1) null);
    public static final BiffType biff8 = new BiffType((AnonymousClass1) null);
    static Class class$jxl$biff$FormatRecord;
    private static String[] dateStrings = {"dd", "mm", "yy", "hh", "ss", "m/", "/d"};
    public static Logger logger;
    private byte[] data;
    private boolean date;
    private java.text.Format format;
    private String formatString;
    private int indexCode;
    private boolean initialized = false;
    private boolean number;
    private WorkbookSettings settings;

    /* renamed from: jxl.biff.FormatRecord$1  reason: invalid class name */
    static class AnonymousClass1 {
    }

    static {
        Class cls;
        if (class$jxl$biff$FormatRecord == null) {
            cls = class$("jxl.biff.FormatRecord");
            class$jxl$biff$FormatRecord = cls;
        } else {
            cls = class$jxl$biff$FormatRecord;
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

    private static class BiffType {
        private BiffType() {
        }

        BiffType(AnonymousClass1 x0) {
            this();
        }
    }

    FormatRecord(String fmt, int refno) {
        super(Type.FORMAT);
        this.formatString = fmt;
        this.indexCode = refno;
    }

    protected FormatRecord() {
        super(Type.FORMAT);
    }

    protected FormatRecord(FormatRecord fr) {
        super(Type.FORMAT);
        this.formatString = fr.formatString;
        this.date = fr.date;
        this.number = fr.number;
    }

    public FormatRecord(Record t, WorkbookSettings ws, BiffType biffType) {
        super(t);
        byte[] data2 = getRecord().getData();
        this.indexCode = IntegerHelper.getInt(data2[0], data2[1]);
        if (biffType == biff8) {
            int numchars = IntegerHelper.getInt(data2[2], data2[3]);
            if (data2[4] == 0) {
                this.formatString = StringHelper.getString(data2, numchars, 5, ws);
            } else {
                this.formatString = StringHelper.getUnicodeString(data2, numchars, 5);
            }
        } else {
            byte[] chars = new byte[data2[2]];
            System.arraycopy(data2, 3, chars, 0, chars.length);
            this.formatString = new String(chars);
        }
        this.date = false;
        this.number = false;
        int i = 0;
        while (true) {
            if (i >= dateStrings.length) {
                break;
            }
            String dateString = dateStrings[i];
            if (this.formatString.indexOf(dateString) == -1 && this.formatString.indexOf(dateString.toUpperCase()) == -1) {
                i++;
            } else {
                this.date = true;
            }
        }
        if (this.date) {
            return;
        }
        if (this.formatString.indexOf(35) != -1 || this.formatString.indexOf(48) != -1) {
            this.number = true;
        }
    }

    public byte[] getData() {
        this.data = new byte[((this.formatString.length() * 2) + 3 + 2)];
        IntegerHelper.getTwoBytes(this.indexCode, this.data, 0);
        IntegerHelper.getTwoBytes(this.formatString.length(), this.data, 2);
        this.data[4] = 1;
        StringHelper.getUnicodeBytes(this.formatString, this.data, 5);
        return this.data;
    }

    public int getFormatIndex() {
        return this.indexCode;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void initialize(int pos) {
        this.indexCode = pos;
        this.initialized = true;
    }

    /* access modifiers changed from: protected */
    public final String replace(String input, String search, String replace) {
        String fmtstr = input;
        int pos = fmtstr.indexOf(search);
        while (pos != -1) {
            StringBuffer tmp = new StringBuffer(fmtstr.substring(0, pos));
            tmp.append(replace);
            tmp.append(fmtstr.substring(search.length() + pos));
            fmtstr = tmp.toString();
            pos = fmtstr.indexOf(search);
        }
        return fmtstr;
    }

    /* access modifiers changed from: protected */
    public final void setFormatString(String s) {
        this.formatString = s;
    }

    public final boolean isDate() {
        return this.date;
    }

    public final boolean isNumber() {
        return this.number;
    }

    public final NumberFormat getNumberFormat() {
        if (this.format != null && (this.format instanceof NumberFormat)) {
            return (NumberFormat) this.format;
        }
        try {
            this.format = new DecimalFormat(replace(replace(replace(replace(replace(this.formatString, "E+", "E"), "_)", ""), "_", ""), "[Red]", ""), "\\", ""));
        } catch (IllegalArgumentException e) {
            this.format = new DecimalFormat("#.###");
        }
        return (NumberFormat) this.format;
    }

    public final DateFormat getDateFormat() {
        char ind;
        int end;
        if (this.format != null && (this.format instanceof DateFormat)) {
            return (DateFormat) this.format;
        }
        String fmt = this.formatString;
        int pos = fmt.indexOf("AM/PM");
        while (pos != -1) {
            StringBuffer sb = new StringBuffer(fmt.substring(0, pos));
            sb.append('a');
            sb.append(fmt.substring(pos + 5));
            fmt = sb.toString();
            pos = fmt.indexOf("AM/PM");
        }
        int pos2 = fmt.indexOf("ss.0");
        while (pos2 != -1) {
            StringBuffer sb2 = new StringBuffer(fmt.substring(0, pos2));
            sb2.append("ss.SSS");
            int pos3 = pos2 + 4;
            while (pos3 < fmt.length() && fmt.charAt(pos3) == '0') {
                pos3++;
            }
            sb2.append(fmt.substring(pos3));
            fmt = sb2.toString();
            pos2 = fmt.indexOf("ss.0");
        }
        StringBuffer sb3 = new StringBuffer();
        for (int i = 0; i < fmt.length(); i++) {
            if (fmt.charAt(i) != '\\') {
                sb3.append(fmt.charAt(i));
            }
        }
        String fmt2 = sb3.toString();
        if (fmt2.charAt(0) == '[' && (end = fmt2.indexOf(93)) != -1) {
            fmt2 = fmt2.substring(end + 1);
        }
        char[] formatBytes = replace(fmt2, ";@", "").toCharArray();
        for (int i2 = 0; i2 < formatBytes.length; i2++) {
            if (formatBytes[i2] == 'm') {
                if (i2 <= 0 || !(formatBytes[i2 - 1] == 'm' || formatBytes[i2 - 1] == 'M')) {
                    int minuteDist = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                    int j = i2 - 1;
                    while (true) {
                        if (j <= 0) {
                            break;
                        } else if (formatBytes[j] == 'h') {
                            minuteDist = i2 - j;
                            break;
                        } else {
                            j--;
                        }
                    }
                    int j2 = i2 + 1;
                    while (true) {
                        if (j2 >= formatBytes.length) {
                            break;
                        } else if (formatBytes[j2] == 'h') {
                            minuteDist = Math.min(minuteDist, j2 - i2);
                            break;
                        } else {
                            j2++;
                        }
                    }
                    int j3 = i2 - 1;
                    while (true) {
                        if (j3 <= 0) {
                            break;
                        } else if (formatBytes[j3] == 'H') {
                            minuteDist = i2 - j3;
                            break;
                        } else {
                            j3--;
                        }
                    }
                    int j4 = i2 + 1;
                    while (true) {
                        if (j4 >= formatBytes.length) {
                            break;
                        } else if (formatBytes[j4] == 'H') {
                            minuteDist = Math.min(minuteDist, j4 - i2);
                            break;
                        } else {
                            j4++;
                        }
                    }
                    int j5 = i2 - 1;
                    while (true) {
                        if (j5 <= 0) {
                            break;
                        } else if (formatBytes[j5] == 's') {
                            minuteDist = Math.min(minuteDist, i2 - j5);
                            break;
                        } else {
                            j5--;
                        }
                    }
                    int j6 = i2 + 1;
                    while (true) {
                        if (j6 >= formatBytes.length) {
                            break;
                        } else if (formatBytes[j6] == 's') {
                            minuteDist = Math.min(minuteDist, j6 - i2);
                            break;
                        } else {
                            j6++;
                        }
                    }
                    int monthDist = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                    int j7 = i2 - 1;
                    while (true) {
                        if (j7 <= 0) {
                            break;
                        } else if (formatBytes[j7] == 'd') {
                            monthDist = i2 - j7;
                            break;
                        } else {
                            j7--;
                        }
                    }
                    int j8 = i2 + 1;
                    while (true) {
                        if (j8 >= formatBytes.length) {
                            break;
                        } else if (formatBytes[j8] == 'd') {
                            monthDist = Math.min(monthDist, j8 - i2);
                            break;
                        } else {
                            j8++;
                        }
                    }
                    int j9 = i2 - 1;
                    while (true) {
                        if (j9 <= 0) {
                            break;
                        } else if (formatBytes[j9] == 'y') {
                            monthDist = Math.min(monthDist, i2 - j9);
                            break;
                        } else {
                            j9--;
                        }
                    }
                    int j10 = i2 + 1;
                    while (true) {
                        if (j10 >= formatBytes.length) {
                            break;
                        } else if (formatBytes[j10] == 'y') {
                            monthDist = Math.min(monthDist, j10 - i2);
                            break;
                        } else {
                            j10++;
                        }
                    }
                    if (monthDist < minuteDist) {
                        formatBytes[i2] = Character.toUpperCase(formatBytes[i2]);
                    } else if (monthDist == minuteDist && monthDist != Integer.MAX_VALUE && ((ind = formatBytes[i2 - monthDist]) == 'y' || ind == 'd')) {
                        formatBytes[i2] = Character.toUpperCase(formatBytes[i2]);
                    }
                } else {
                    formatBytes[i2] = formatBytes[i2 - 1];
                }
            }
        }
        try {
            this.format = new SimpleDateFormat(new String(formatBytes));
        } catch (IllegalArgumentException e) {
            this.format = new SimpleDateFormat("dd MM yyyy hh:mm:ss");
        }
        return (DateFormat) this.format;
    }

    public int getIndexCode() {
        return this.indexCode;
    }

    public String getFormatString() {
        return this.formatString;
    }

    public boolean isBuiltIn() {
        return false;
    }

    public int hashCode() {
        return this.formatString.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FormatRecord)) {
            return false;
        }
        FormatRecord fr = (FormatRecord) o;
        if (!this.initialized || !fr.initialized) {
            return this.formatString.equals(fr.formatString);
        }
        if (this.date == fr.date && this.number == fr.number) {
            return this.formatString.equals(fr.formatString);
        }
        return false;
    }
}
