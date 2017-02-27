package wang.yuchao.android.library.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * 网络帮助类
 * Created by wangyuchao on 15/9/6.
 */
public class NetworkUtil {

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return 网络是否连接
     */
    public static boolean isNetworkConnected() {
        return isNetworkConnected(APP.getContext());
    }

    /**
     * 是否为wifi环境
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null
                        && networkInfo.isConnected()
                        && networkInfo.isAvailable()
                        && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return 是否是wifi连接
     */
    public static boolean isWifiConnected() {
        return isWifiConnected(APP.getContext());
    }

    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null
                        && networkInfo.isConnected()
                        && networkInfo.isAvailable()
                        && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return 是否是wifi连接
     */
    public static boolean isMobileConnected() {
        return isMobileConnected(APP.getContext());
    }
}
