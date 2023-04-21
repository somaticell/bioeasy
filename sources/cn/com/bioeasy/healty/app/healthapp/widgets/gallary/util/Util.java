package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.util;

import android.content.Context;
import android.os.Environment;
import android.view.WindowManager;
import cn.com.bioeasy.app.gallery.Image;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Util {
    public static boolean hasSDCard() {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return false;
        }
        return true;
    }

    public static String getCameraPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/";
    }

    public static String getSaveImageFullName() {
        return "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
    }

    public static ArrayList<String> toArrayList(List<Image> images) {
        ArrayList<String> strings = new ArrayList<>();
        for (Image i : images) {
            strings.add(i.getPath());
        }
        return strings;
    }

    public static String[] toArray(List<Image> images) {
        int len;
        String[] strings = null;
        if (!(images == null || (len = images.size()) == 0)) {
            strings = new String[len];
            int i = 0;
            for (Image image : images) {
                strings[i] = image.getPath();
                i++;
            }
        }
        return strings;
    }

    public static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getHeight();
    }

    public static int dipTopx(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static float pxTodip(Context context, float pxValue) {
        return (pxValue / context.getResources().getDisplayMetrics().density) + 0.5f;
    }

    public static String toJson(Object object) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new UtilDateSerializer());
        return builder.create().toJson(object);
    }

    static class UtilDateSerializer implements JsonSerializer<Date> {
        UtilDateSerializer() {
        }

        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive("/Date(" + src.getTime() + "+0800)/");
        }
    }
}
