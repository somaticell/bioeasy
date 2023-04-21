package cn.com.bioeasy.app.storage.ext;

import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SDCardUtil {
    public static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean isFileExistsInSDCard(String filePath, String fileName) {
        if (!checkSDCardAvailable() || !new File(filePath, fileName).exists()) {
            return false;
        }
        return true;
    }

    public static boolean saveFileToSDCard(String filePath, String filename, String content) throws Exception {
        if (!checkSDCardAvailable()) {
            return false;
        }
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        FileOutputStream outStream = new FileOutputStream(new File(filePath + filename));
        outStream.write(content.getBytes());
        outStream.close();
        return true;
    }

    public static byte[] readFileFromSDCard(String filePath, String fileName) {
        try {
            if (!checkSDCardAvailable()) {
                return null;
            }
            FileInputStream fin = new FileInputStream(filePath + "/" + fileName);
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer);
            fin.close();
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteSDFile(String filePath, String fileName) {
        File file = new File(filePath + "/" + fileName);
        if (file == null || !file.exists() || file.isDirectory()) {
            return false;
        }
        return file.delete();
    }

    public static File getTemFile(String filePath, String fileName) throws IOException {
        if (!checkSDCardAvailable()) {
            return File.createTempFile((String) null, ".jpg");
        }
        File file = new File(filePath, fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        return file;
    }

    public static String getRootPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
}
