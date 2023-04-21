package cn.com.bioeasy.app.widgets.netstatus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.Locale;

public class NetUtils {

    public enum NetType {
        WIFI,
        CMNET,
        CMWAP,
        NONE
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo[] info = ((ConnectivityManager) context.getSystemService("connectivity")).getAllNetworkInfo();
        if (info != null) {
            for (NetworkInfo state : info) {
                if (state.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNetworkConnected(Context context) {
        NetworkInfo mNetworkInfo;
        if (context == null || (mNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) == null) {
            return false;
        }
        return mNetworkInfo.isAvailable();
    }

    public static boolean isWifiConnected(Context context) {
        NetworkInfo mWiFiNetworkInfo;
        if (context == null || (mWiFiNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(1)) == null) {
            return false;
        }
        return mWiFiNetworkInfo.isAvailable();
    }

    public static boolean isMobileConnected(Context context) {
        NetworkInfo mMobileNetworkInfo;
        if (context == null || (mMobileNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(0)) == null) {
            return false;
        }
        return mMobileNetworkInfo.isAvailable();
    }

    public static int getConnectedType(Context context) {
        NetworkInfo mNetworkInfo;
        if (context == null || (mNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) == null || !mNetworkInfo.isAvailable()) {
            return -1;
        }
        return mNetworkInfo.getType();
    }

    public static NetType getAPNType(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.NONE;
        }
        int nType = networkInfo.getType();
        if (nType == 0) {
            if (networkInfo.getExtraInfo().toLowerCase(Locale.getDefault()).equals("cmnet")) {
                return NetType.CMNET;
            }
            return NetType.CMWAP;
        } else if (nType == 1) {
            return NetType.WIFI;
        } else {
            return NetType.NONE;
        }
    }
}
