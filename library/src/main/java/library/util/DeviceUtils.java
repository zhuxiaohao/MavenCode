package library.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * ClassName: DeviceUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午5:48:35 <br/>
 * <h2>获取设备信息 <h2>
 * <pre>
 * 设备操作
 * </pre>
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class DeviceUtils {

    /**
     * 
     * <pre>
     * 获取ip地址
     * </pre>
     * 
     * @return
     */
    public static String getIpAddress() {
        String ipaddress = "";
        try {
            for (java.util.Enumeration<java.net.NetworkInterface> en = java.net.NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                java.net.NetworkInterface intf = en.nextElement();
                for (java.util.Enumeration<java.net.InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    java.net.InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ipaddress = ipaddress + ";" + inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (java.net.SocketException ex) {
        }
        return ipaddress;
    }

    /**
     * 
     * <pre>
     * 获取MAC地址
     * </pre>
     * 
     * @param context
     * @return
     */
    public static String getMacAddress(android.content.Context context) {
        android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(android.content.Context.WIFI_SERVICE);
        android.net.wifi.WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 
     * <pre>
     * 获取IMEI号
     * </pre>
     * 
     * @param context
     * @return
     */
    public static String getIMEI(android.content.Context context) {
        android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) context.getSystemService(android.content.Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    /**
     * 
     * <pre>
     * 获取手机型号
     * </pre>
     * 
     * @param context
     * @return
     */
    public static String getMobleInfo() {
        return android.os.Build.MODEL;
    }

    /**
     * 
     * <pre>
     * 获取系统版本
     * </pre>
     * 
     * @param context
     * @return
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 
     * <pre>
     * 获取系统API版本
     * </pre>
     * 
     * @param context
     * @return
     */
    public static int getApiVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备 ID getDeviceId:(这里用一句话描述这个方法的作用). <br/>
     * 
     * @author chenhao
     * @param context
     * @return
     * @since JDK 1.6
     */
    public static String getDeviceId(android.content.Context context) {
        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context.getSystemService(android.content.Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        return DEVICE_ID;

    }
}
