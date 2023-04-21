package cn.com.bioeasy.app.utils;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class PinyinHelper {
    private static PinyinHelper instance;
    private Properties properties = null;

    public static String[] getUnformattedHanyuPinyinStringArray(char ch) {
        return getInstance().getHanyuPinyinStringArray(ch);
    }

    private PinyinHelper() {
        initResource();
    }

    public static PinyinHelper getInstance() {
        if (instance == null) {
            instance = new PinyinHelper();
        }
        return instance;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x002f A[SYNTHETIC, Splitter:B:15:0x002f] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x003e A[SYNTHETIC, Splitter:B:23:0x003e] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x004a A[SYNTHETIC, Splitter:B:29:0x004a] */
    /* JADX WARNING: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:20:0x0039=Splitter:B:20:0x0039, B:12:0x002a=Splitter:B:12:0x002a} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initResource() {
        /*
            r7 = this;
            r0 = 0
            java.lang.String r4 = "/assets/unicode_to_simple_pinyin.txt"
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ FileNotFoundException -> 0x0029, IOException -> 0x0038 }
            java.lang.Class<cn.com.bioeasy.app.utils.PinyinHelper> r5 = cn.com.bioeasy.app.utils.PinyinHelper.class
            java.lang.String r6 = "/assets/unicode_to_simple_pinyin.txt"
            java.io.InputStream r5 = r5.getResourceAsStream(r6)     // Catch:{ FileNotFoundException -> 0x0029, IOException -> 0x0038 }
            r1.<init>(r5)     // Catch:{ FileNotFoundException -> 0x0029, IOException -> 0x0038 }
            java.util.Properties r5 = new java.util.Properties     // Catch:{ FileNotFoundException -> 0x0059, IOException -> 0x0056, all -> 0x0053 }
            r5.<init>()     // Catch:{ FileNotFoundException -> 0x0059, IOException -> 0x0056, all -> 0x0053 }
            r7.properties = r5     // Catch:{ FileNotFoundException -> 0x0059, IOException -> 0x0056, all -> 0x0053 }
            java.util.Properties r5 = r7.properties     // Catch:{ FileNotFoundException -> 0x0059, IOException -> 0x0056, all -> 0x0053 }
            r5.load(r1)     // Catch:{ FileNotFoundException -> 0x0059, IOException -> 0x0056, all -> 0x0053 }
            if (r1 == 0) goto L_0x005c
            r1.close()     // Catch:{ IOException -> 0x0023 }
            r0 = r1
        L_0x0022:
            return
        L_0x0023:
            r2 = move-exception
            r2.printStackTrace()
            r0 = r1
            goto L_0x0022
        L_0x0029:
            r3 = move-exception
        L_0x002a:
            r3.printStackTrace()     // Catch:{ all -> 0x0047 }
            if (r0 == 0) goto L_0x0022
            r0.close()     // Catch:{ IOException -> 0x0033 }
            goto L_0x0022
        L_0x0033:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0022
        L_0x0038:
            r3 = move-exception
        L_0x0039:
            r3.printStackTrace()     // Catch:{ all -> 0x0047 }
            if (r0 == 0) goto L_0x0022
            r0.close()     // Catch:{ IOException -> 0x0042 }
            goto L_0x0022
        L_0x0042:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0022
        L_0x0047:
            r5 = move-exception
        L_0x0048:
            if (r0 == 0) goto L_0x004d
            r0.close()     // Catch:{ IOException -> 0x004e }
        L_0x004d:
            throw r5
        L_0x004e:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x004d
        L_0x0053:
            r5 = move-exception
            r0 = r1
            goto L_0x0048
        L_0x0056:
            r3 = move-exception
            r0 = r1
            goto L_0x0039
        L_0x0059:
            r3 = move-exception
            r0 = r1
            goto L_0x002a
        L_0x005c:
            r0 = r1
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.bioeasy.app.utils.PinyinHelper.initResource():void");
    }

    private String[] getHanyuPinyinStringArray(char ch) {
        String pinyinRecord = getHanyuPinyinRecordFromChar(ch);
        if (pinyinRecord == null) {
            return null;
        }
        int indexOfLeftBracket = pinyinRecord.indexOf("(");
        return pinyinRecord.substring("(".length() + indexOfLeftBracket, pinyinRecord.lastIndexOf(")")).split(ListUtils.DEFAULT_JOIN_SEPARATOR);
    }

    private String getHanyuPinyinRecordFromChar(char ch) {
        return this.properties.getProperty(Integer.toHexString(ch).toUpperCase());
    }

    class Field {
        static final String COMMA = ",";
        static final String LEFT_BRACKET = "(";
        static final String RIGHT_BRACKET = ")";

        Field() {
        }
    }

    public static String makeStringByStringSet(Set<String> stringSet) {
        StringBuilder str = new StringBuilder();
        int i = 0;
        for (String s : stringSet) {
            if (i == stringSet.size() - 1) {
                str.append(s);
            } else {
                str.append(s + ListUtils.DEFAULT_JOIN_SEPARATOR);
            }
            i++;
        }
        return str.toString().toLowerCase();
    }

    public static Set<String> getPinyin(String src) {
        char[] srcChar = src.toCharArray();
        String[][] temp = new String[src.length()][];
        for (int i = 0; i < srcChar.length; i++) {
            char c = srcChar[i];
            if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
                String[] t = getUnformattedHanyuPinyinStringArray(c);
                temp[i] = new String[t.length];
                for (int j = 0; j < t.length; j++) {
                    temp[i][j] = t[j];
                }
            } else if ((c < 'A' || c > 'Z') && ((c < 'a' || c > 'z') && ((c < '0' || c > '9') && c != '*'))) {
                temp[i] = new String[]{"null!"};
            } else {
                temp[i] = new String[]{String.valueOf(srcChar[i])};
            }
        }
        return paiLie(temp);
    }

    private static Set<String> paiLie(String[][] str) {
        int max = 1;
        for (String[] length : str) {
            max *= length.length;
        }
        Set<String> result = new HashSet<>();
        for (int i = 0; i < max; i++) {
            String s = "";
            int temp = 1;
            for (int j = 0; j < str.length; j++) {
                temp *= str[j].length;
                s = s + str[j][(i / (max / temp)) % str[j].length];
            }
            result.add(s);
        }
        return result;
    }
}
