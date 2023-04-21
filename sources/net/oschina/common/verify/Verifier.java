package net.oschina.common.verify;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.PackageManager;
import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import net.oschina.common.utils.NativeUtil;

public final class Verifier {
    private static native String native_getPrivateToken(Application application);

    private static native String native_signStringArray(String[] strArr);

    @SuppressLint({"PackageManagerGetSignatures"})
    public static String getSignatureSha1(Application application) {
        try {
            try {
                try {
                    try {
                        return byteToHexFormatted(MessageDigest.getInstance("SHA1").digest(((X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(application.getPackageManager().getPackageInfo(application.getPackageName(), 64).signatures[0].toByteArray()))).getEncoded()));
                    } catch (NoSuchAlgorithmException | CertificateEncodingException e1) {
                        e1.printStackTrace();
                        return null;
                    }
                } catch (CertificateException e) {
                    e.printStackTrace();
                    return null;
                }
            } catch (CertificateException e2) {
                e2.printStackTrace();
                return null;
            }
        } catch (PackageManager.NameNotFoundException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    private static String byteToHexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1) {
                h = "0" + h;
            }
            if (l > 2) {
                h = h.substring(l - 2, l);
            }
            str.append(h.toUpperCase());
            if (i < arr.length - 1) {
                str.append(':');
            }
        }
        return str.toString();
    }

    public static String getPrivateToken(Application application) {
        try {
            return native_getPrivateToken(application);
        } catch (Exception e) {
            return "";
        }
    }

    public static String signStringArray(String... values) {
        return native_signStringArray(values);
    }

    static {
        NativeUtil.doLoadNative();
    }
}
