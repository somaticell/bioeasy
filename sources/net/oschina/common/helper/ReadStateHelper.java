package net.oschina.common.helper;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.oschina.common.utils.StreamUtil;

public final class ReadStateHelper {
    private static final Map<String, ReadStateHelper> helperCache = new HashMap();
    private final Map<String, Long> cache = new HashMap();
    private final File file;
    private final int maxPoolSize;

    private ReadStateHelper(File file2, int maxPoolSize2) {
        if (file2 == null || !file2.exists() || !file2.isFile() || !file2.canRead() || !file2.canWrite()) {
            throw new NullPointerException("file not null.");
        }
        this.maxPoolSize = maxPoolSize2;
        this.file = file2;
        read();
    }

    public static ReadStateHelper create(Context context, String fileName, int maxPoolSize2) {
        String fileName2 = fileName + ".json";
        if (helperCache.containsKey(fileName2)) {
            return helperCache.get(fileName2);
        }
        File file2 = new File(context.getDir("read_state", 0), fileName2);
        if (!file2.exists()) {
            File parent = file2.getParentFile();
            if (parent.exists() || parent.mkdirs()) {
                try {
                    if (!file2.createNewFile()) {
                        throw new IOException("can't createNewFile by:" + file2.toString());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException("can't mkdirs by:" + parent.toString());
            }
        }
        ReadStateHelper helper = new ReadStateHelper(file2, maxPoolSize2);
        helperCache.put(fileName2, helper);
        return helper;
    }

    public void put(long key) {
        put(String.valueOf(key));
    }

    public void put(String key) {
        if (!TextUtils.isEmpty(key) && !this.cache.containsKey(key)) {
            if (this.cache.size() >= this.maxPoolSize) {
                adjustCache();
            }
            this.cache.put(key, Long.valueOf(System.currentTimeMillis()));
            save();
        }
    }

    public boolean already(long key) {
        return already(String.valueOf(key));
    }

    public boolean already(String key) {
        return !TextUtils.isEmpty(key) && this.cache.containsKey(key);
    }

    public void adjustCache() {
        if (this.cache.size() != 0) {
            List<Map.Entry<String, Long>> info = new ArrayList<>(this.cache.entrySet());
            Collections.sort(info, new Comparator<Map.Entry<String, Long>>() {
                public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                    long et = o1.getValue().longValue() - o2.getValue().longValue();
                    if (et > 0) {
                        return 1;
                    }
                    return et == 0 ? 0 : -1;
                }
            });
            int deleteSize = (int) (((float) info.size()) * 0.7f);
            if (deleteSize > 0) {
                for (Map.Entry<String, Long> stringLongEntry : info) {
                    this.cache.remove(stringLongEntry.getKey());
                    deleteSize--;
                    if (deleteSize <= 0) {
                        return;
                    }
                }
            }
        }
    }

    private void read() {
        Reader reader = null;
        try {
            Gson gson = new Gson();
            Reader reader2 = new FileReader(this.file);
            try {
                Map<String, Long> data = (Map) gson.fromJson(reader2, new TypeToken<Map<String, Long>>() {
                }.getType());
                if (data != null && data.size() > 0) {
                    this.cache.putAll(data);
                }
                StreamUtil.close(reader2);
                Reader reader3 = reader2;
            } catch (FileNotFoundException e) {
                e = e;
                reader = reader2;
                try {
                    e.printStackTrace();
                    StreamUtil.close(reader);
                } catch (Throwable th) {
                    th = th;
                    StreamUtil.close(reader);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                reader = reader2;
                StreamUtil.close(reader);
                throw th;
            }
        } catch (FileNotFoundException e2) {
            e = e2;
            e.printStackTrace();
            StreamUtil.close(reader);
        }
    }

    private void save() {
        Writer writer = null;
        try {
            Writer writer2 = new FileWriter(this.file);
            try {
                new Gson().toJson((Object) this.cache, (Appendable) writer2);
                StreamUtil.close(writer2);
                Writer writer3 = writer2;
            } catch (IOException e) {
                e = e;
                writer = writer2;
                try {
                    e.printStackTrace();
                    StreamUtil.close(writer);
                } catch (Throwable th) {
                    th = th;
                    StreamUtil.close(writer);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                writer = writer2;
                StreamUtil.close(writer);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            e.printStackTrace();
            StreamUtil.close(writer);
        }
    }
}
