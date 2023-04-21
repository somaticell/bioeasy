package cn.com.bioeasy.healty.app.healthapp.cache;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CacheManager {
    private Context context;
    private SharedPreferences.Editor editor;
    private boolean isChanged;
    private int saveMode;
    private SharedPreferences spf;
    private Type type;

    public enum Type {
        LOGIN("login_data");
        
        /* access modifiers changed from: private */
        public String mValue;

        private Type(String value) {
            this.mValue = value;
        }
    }

    @Inject
    public CacheManager(Context context2) {
        this(context2, 0);
    }

    public CacheManager(Context context2, int saveMode2) {
        this(context2, saveMode2, Type.LOGIN);
    }

    public CacheManager(Context context2, int saveMode2, Type dataType) {
        this.isChanged = false;
        this.context = context2;
        this.saveMode = saveMode2;
        this.type = dataType;
        initSpf();
    }

    public void removeCache(String key) {
        if (this.isChanged || this.spf == null) {
            initSpf();
        }
        if (this.spf.contains(key)) {
            this.editor.remove(key);
            this.editor.apply();
        }
    }

    public String getString(String name) {
        if (this.isChanged || this.spf == null) {
            initSpf();
        }
        return this.spf.getString(name, (String) null);
    }

    public float getFloat(String name) {
        if (this.isChanged || this.spf == null) {
            initSpf();
        }
        return this.spf.getFloat(name, 0.0f);
    }

    public int getInt(String name) {
        if (this.isChanged || this.spf == null) {
            initSpf();
        }
        return this.spf.getInt(name, 0);
    }

    public long getLong(String name) {
        if (this.isChanged || this.spf == null) {
            initSpf();
        }
        return this.spf.getLong(name, 0);
    }

    public Set<String> getStringSet(String name) {
        if (this.isChanged || this.spf == null) {
            initSpf();
        }
        return this.spf.getStringSet(name, (Set) null);
    }

    public void saveString(String name, String value) {
        if (this.isChanged || this.spf == null) {
            initSpf();
        }
        this.editor.putString(name, value);
        this.editor.apply();
    }

    public void saveFloat(String name, float value) {
        if (this.isChanged || this.spf == null) {
            initSpf();
        }
        this.editor.putFloat(name, value);
        this.editor.apply();
    }

    public void saveInt(String name, int value) {
        if (this.isChanged || this.spf == null) {
            initSpf();
        }
        this.editor.putInt(name, value);
        this.editor.apply();
    }

    public void saveLong(String name, long value) {
        if (this.isChanged || this.spf == null) {
            initSpf();
        }
        this.editor.putLong(name, value);
        this.editor.apply();
    }

    public void saveStringSet(String name, Set<String> value) {
        if (this.isChanged || this.spf == null) {
            initSpf();
        }
        this.editor.putStringSet(name, value);
        this.editor.apply();
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0049 A[SYNTHETIC, Splitter:B:22:0x0049] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x004e A[Catch:{ IOException -> 0x0052 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x005a A[SYNTHETIC, Splitter:B:30:0x005a] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x005f A[Catch:{ IOException -> 0x0063 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void saveObject(java.lang.String r8, java.lang.Object r9) {
        /*
            r7 = this;
            boolean r5 = r7.isChanged
            if (r5 != 0) goto L_0x0008
            android.content.SharedPreferences r5 = r7.spf
            if (r5 != 0) goto L_0x000b
        L_0x0008:
            r7.initSpf()
        L_0x000b:
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            r3 = 0
            java.io.ObjectOutputStream r4 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x0043 }
            r4.<init>(r0)     // Catch:{ IOException -> 0x0043 }
            r4.writeObject(r9)     // Catch:{ IOException -> 0x006b, all -> 0x0068 }
            java.lang.String r2 = new java.lang.String     // Catch:{ IOException -> 0x006b, all -> 0x0068 }
            byte[] r5 = r0.toByteArray()     // Catch:{ IOException -> 0x006b, all -> 0x0068 }
            r6 = 0
            byte[] r5 = android.util.Base64.encode(r5, r6)     // Catch:{ IOException -> 0x006b, all -> 0x0068 }
            r2.<init>(r5)     // Catch:{ IOException -> 0x006b, all -> 0x0068 }
            android.content.SharedPreferences$Editor r5 = r7.editor     // Catch:{ IOException -> 0x006b, all -> 0x0068 }
            r5.putString(r8, r2)     // Catch:{ IOException -> 0x006b, all -> 0x0068 }
            android.content.SharedPreferences$Editor r5 = r7.editor     // Catch:{ IOException -> 0x006b, all -> 0x0068 }
            r5.apply()     // Catch:{ IOException -> 0x006b, all -> 0x0068 }
            if (r0 == 0) goto L_0x0036
            r0.close()     // Catch:{ IOException -> 0x003d }
        L_0x0036:
            if (r4 == 0) goto L_0x003b
            r4.close()     // Catch:{ IOException -> 0x003d }
        L_0x003b:
            r3 = r4
        L_0x003c:
            return
        L_0x003d:
            r1 = move-exception
            r1.printStackTrace()
            r3 = r4
            goto L_0x003c
        L_0x0043:
            r1 = move-exception
        L_0x0044:
            r1.printStackTrace()     // Catch:{ all -> 0x0057 }
            if (r0 == 0) goto L_0x004c
            r0.close()     // Catch:{ IOException -> 0x0052 }
        L_0x004c:
            if (r3 == 0) goto L_0x003c
            r3.close()     // Catch:{ IOException -> 0x0052 }
            goto L_0x003c
        L_0x0052:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x003c
        L_0x0057:
            r5 = move-exception
        L_0x0058:
            if (r0 == 0) goto L_0x005d
            r0.close()     // Catch:{ IOException -> 0x0063 }
        L_0x005d:
            if (r3 == 0) goto L_0x0062
            r3.close()     // Catch:{ IOException -> 0x0063 }
        L_0x0062:
            throw r5
        L_0x0063:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0062
        L_0x0068:
            r5 = move-exception
            r3 = r4
            goto L_0x0058
        L_0x006b:
            r1 = move-exception
            r3 = r4
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.bioeasy.healty.app.healthapp.cache.CacheManager.saveObject(java.lang.String, java.lang.Object):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0053 A[SYNTHETIC, Splitter:B:34:0x0053] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0058 A[Catch:{ IOException -> 0x005c }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0067 A[SYNTHETIC, Splitter:B:44:0x0067] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x006c A[Catch:{ IOException -> 0x0070 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0078 A[SYNTHETIC, Splitter:B:52:0x0078] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x007d A[Catch:{ IOException -> 0x0081 }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:41:0x0062=Splitter:B:41:0x0062, B:21:0x003a=Splitter:B:21:0x003a, B:31:0x004e=Splitter:B:31:0x004e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T getObject(java.lang.String r9, java.lang.Class<T> r10) {
        /*
            r8 = this;
            r6 = 0
            boolean r7 = r8.isChanged
            if (r7 != 0) goto L_0x0009
            android.content.SharedPreferences r7 = r8.spf
            if (r7 != 0) goto L_0x000c
        L_0x0009:
            r8.initSpf()
        L_0x000c:
            android.content.SharedPreferences r7 = r8.spf
            java.lang.String r3 = r7.getString(r9, r6)
            if (r3 != 0) goto L_0x0015
        L_0x0014:
            return r6
        L_0x0015:
            r7 = 0
            byte[] r1 = android.util.Base64.decode(r3, r7)
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream
            r0.<init>(r1)
            r4 = 0
            java.io.ObjectInputStream r5 = new java.io.ObjectInputStream     // Catch:{ StreamCorruptedException -> 0x0039, IOException -> 0x004d, ClassNotFoundException -> 0x0061 }
            r5.<init>(r0)     // Catch:{ StreamCorruptedException -> 0x0039, IOException -> 0x004d, ClassNotFoundException -> 0x0061 }
            java.lang.Object r6 = r5.readObject()     // Catch:{ StreamCorruptedException -> 0x008f, IOException -> 0x008c, ClassNotFoundException -> 0x0089, all -> 0x0086 }
            if (r0 == 0) goto L_0x002e
            r0.close()     // Catch:{ IOException -> 0x0034 }
        L_0x002e:
            if (r5 == 0) goto L_0x0014
            r5.close()     // Catch:{ IOException -> 0x0034 }
            goto L_0x0014
        L_0x0034:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0014
        L_0x0039:
            r2 = move-exception
        L_0x003a:
            r2.printStackTrace()     // Catch:{ all -> 0x0075 }
            if (r0 == 0) goto L_0x0042
            r0.close()     // Catch:{ IOException -> 0x0048 }
        L_0x0042:
            if (r4 == 0) goto L_0x0014
            r4.close()     // Catch:{ IOException -> 0x0048 }
            goto L_0x0014
        L_0x0048:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0014
        L_0x004d:
            r2 = move-exception
        L_0x004e:
            r2.printStackTrace()     // Catch:{ all -> 0x0075 }
            if (r0 == 0) goto L_0x0056
            r0.close()     // Catch:{ IOException -> 0x005c }
        L_0x0056:
            if (r4 == 0) goto L_0x0014
            r4.close()     // Catch:{ IOException -> 0x005c }
            goto L_0x0014
        L_0x005c:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0014
        L_0x0061:
            r2 = move-exception
        L_0x0062:
            r2.printStackTrace()     // Catch:{ all -> 0x0075 }
            if (r0 == 0) goto L_0x006a
            r0.close()     // Catch:{ IOException -> 0x0070 }
        L_0x006a:
            if (r4 == 0) goto L_0x0014
            r4.close()     // Catch:{ IOException -> 0x0070 }
            goto L_0x0014
        L_0x0070:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0014
        L_0x0075:
            r7 = move-exception
        L_0x0076:
            if (r0 == 0) goto L_0x007b
            r0.close()     // Catch:{ IOException -> 0x0081 }
        L_0x007b:
            if (r4 == 0) goto L_0x0080
            r4.close()     // Catch:{ IOException -> 0x0081 }
        L_0x0080:
            throw r7
        L_0x0081:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0080
        L_0x0086:
            r7 = move-exception
            r4 = r5
            goto L_0x0076
        L_0x0089:
            r2 = move-exception
            r4 = r5
            goto L_0x0062
        L_0x008c:
            r2 = move-exception
            r4 = r5
            goto L_0x004e
        L_0x008f:
            r2 = move-exception
            r4 = r5
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.bioeasy.healty.app.healthapp.cache.CacheManager.getObject(java.lang.String, java.lang.Class):java.lang.Object");
    }

    public void setSaveMode(int saveMode2) {
        this.saveMode = saveMode2;
        this.isChanged = true;
    }

    public void setType(Type type2) {
        this.type = type2;
        this.isChanged = true;
    }

    private void initSpf() {
        if (this.context == null) {
            throw new NullPointerException("context 不能为Null");
        }
        this.spf = this.context.getSharedPreferences(this.type.mValue, this.saveMode);
        this.editor = this.spf.edit();
    }
}
