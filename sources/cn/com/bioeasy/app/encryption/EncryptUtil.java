package cn.com.bioeasy.app.encryption;

import android.util.Base64;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {
    public static String md5Encode(String inStr) {
        try {
            byte[] md5Bytes = MessageDigest.getInstance("MD5").digest(inStr.getBytes("UTF-8"));
            StringBuffer hexValue = new StringBuffer();
            for (byte b : md5Bytes) {
                int val = b & 255;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
    }

    public static String shaEncode(String inStr) {
        try {
            byte[] md5Bytes = MessageDigest.getInstance("SHA").digest(inStr.getBytes("UTF-8"));
            StringBuffer hexValue = new StringBuffer();
            for (byte b : md5Bytes) {
                int val = b & 255;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
    }

    public static byte[] base64Encode(String inStr) throws Exception {
        return Base64.encode(inStr.getBytes(), 0);
    }

    public static String base64Decode(byte[] inByte) throws Exception {
        return new String(Base64.decode(inByte, 0));
    }

    private static Key getKey(String strKey) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            generator.init(new SecureRandom(strKey.getBytes()));
            return generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        }
    }

    public static byte[] desEncode(byte[] byteS, String key) {
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(1, getKey(key));
            return cipher.doFinal(byteS);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        } catch (Throwable th) {
            throw th;
        }
    }

    public static byte[] desDecode(byte[] byteD, String key) {
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, getKey(key));
            return cipher.doFinal(byteD);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        } catch (Throwable th) {
            throw th;
        }
    }

    public static String desEncode(String strMing, String key) {
        try {
            return Base64.encodeToString(desEncode(strMing.getBytes("UTF8"), key), 0);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        } catch (Throwable th) {
            throw th;
        }
    }

    public static String desDecode(String strMi, String key) {
        try {
            return new String(desDecode(Base64.decode(strMi.getBytes(), 0), key), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        } catch (Throwable th) {
            throw th;
        }
    }

    public static void desEncode(String filePath, String destPath, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1, getKey(key));
        InputStream is = new FileInputStream(filePath);
        OutputStream out = new FileOutputStream(destPath);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        byte[] buffer = new byte[1024];
        while (true) {
            int r = cis.read(buffer);
            if (r > 0) {
                out.write(buffer, 0, r);
            } else {
                cis.close();
                is.close();
                out.close();
                return;
            }
        }
    }

    public static void desDecode(String filePath, String destPath, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, getKey(key));
        InputStream is = new FileInputStream(filePath);
        OutputStream out = new FileOutputStream(destPath);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        byte[] buffer = new byte[1024];
        while (true) {
            int r = is.read(buffer);
            if (r >= 0) {
                cos.write(buffer, 0, r);
            } else {
                cos.close();
                out.close();
                is.close();
                return;
            }
        }
    }

    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
        Key deskey = keyGenerator(new String(key));
        Cipher cipher = Cipher.getInstance("desede/CBC/NoPadding");
        cipher.init(1, deskey, new IvParameterSpec(keyiv));
        return cipher.doFinal(data);
    }

    private static Key keyGenerator(String keyStr) throws Exception {
        return SecretKeyFactory.getInstance("desede").generateSecret(new DESedeKeySpec(HexString2Bytes(keyStr)));
    }

    private static int parse(char c) {
        if (c >= 'a') {
            return ((c - 'a') + 10) & 15;
        }
        if (c >= 'A') {
            return ((c - 'A') + 10) & 15;
        }
        return (c - '0') & 15;
    }

    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[(hexstr.length() / 2)];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            int j2 = j + 1;
            char c0 = hexstr.charAt(j);
            j = j2 + 1;
            b[i] = (byte) ((parse(c0) << 4) | parse(hexstr.charAt(j2)));
        }
        return b;
    }

    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
        Key deskey = keyGenerator(new String(key));
        Cipher cipher = Cipher.getInstance("desede/CBC/NoPadding");
        cipher.init(2, deskey, new IvParameterSpec(keyiv));
        return cipher.doFinal(data);
    }

    public static byte[] aesEncode(String data, String key) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKeySpec mKey = new SecretKeySpec(kgen.generateKey().getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = data.getBytes("utf-8");
            cipher.init(1, mKey);
            return cipher.doFinal(byteContent);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] aesDecode(byte[] data, String key) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKeySpec mKey = new SecretKeySpec(kgen.generateKey().getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, mKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & BLEServiceApi.CONNECTED_STATUS);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[(hexStr.length() / 2)];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, (i * 2) + 1), 16);
            result[i] = (byte) ((high * 16) + Integer.parseInt(hexStr.substring((i * 2) + 1, (i * 2) + 2), 16));
        }
        return result;
    }
}
