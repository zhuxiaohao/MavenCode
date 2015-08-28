package library.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * 
 * ClassName: NetWorkUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:42:26 <br/>
 * 网络判断
 * 
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class NetWorkUtils {

	public static final String NETWORK_TYPE_WIFI = "wifi";
	public static final String NETWORK_TYPE_3G = "eg";
	public static final String NETWORK_TYPE_2G = "2g";
	public static final String NETWORK_TYPE_WAP = "wap";
	public static final String NETWORK_TYPE_UNKNOWN = "unknown";
	public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

	/**
	 * 判断网络是否连接
	 * 
	 * @param context
	 *            场景
	 * @return true:网络已经连接;false:网络断开
	 */
	public static boolean checkNetWork(android.content.Context context) {
		// 获得手机所有连接管理对象（包括对wi-fi等连接的管理）
		try {
			android.net.ConnectivityManager connectivity = (android.net.ConnectivityManager) context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获得网络连接管理的对象
				android.net.NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					return true;
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 获取网络类型
	 * 
	 * @param context
	 * @return
	 */
	public static String getNetWorkType(android.content.Context context) {
		android.net.ConnectivityManager manager = (android.net.ConnectivityManager) context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
		android.net.NetworkInfo networkInfo;
		String type = NETWORK_TYPE_DISCONNECT;
		if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null) {
			return type;
		}
		;

		if (networkInfo.isConnected()) {
			String typeName = networkInfo.getTypeName();
			if ("WIFI".equalsIgnoreCase(typeName)) {
				type = NETWORK_TYPE_WIFI;
			} else if ("MOBILE".equalsIgnoreCase(typeName)) {
				@SuppressWarnings("deprecation")
				String proxyHost = android.net.Proxy.getDefaultHost();
				type = android.text.TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORK_TYPE_3G : NETWORK_TYPE_2G) : NETWORK_TYPE_WAP;
			} else {
				type = NETWORK_TYPE_UNKNOWN;
			}
		}
		return type;
	}

	/**
	 * 计算网速
	 * 
	 * @return
	 */
	public static long getNetworkSpeed() {
		ProcessBuilder cmd;
		long readBytes = 0;
		java.io.BufferedReader rd = null;
		try {
			String[] args = { "/system/bin/cat", "/proc/net/dev" };
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			rd = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
			String line;
			@SuppressWarnings("unused")
			int linecount = 0;
			while ((line = rd.readLine()) != null) {
				linecount++;
				if (line.contains("wlan0") || line.contains("eth0")) {
					// L.E("获取流量整条字符串",line);
					String[] delim = line.split(":");
					if (delim.length >= 2) {
						String[] numbers = delim[1].trim().split(" ");// 提取数据
						readBytes = Long.parseLong(numbers[0].trim());//
						break;
					}
				}
			}
			rd.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rd != null) {
				try {
					rd.close();
				} catch (java.io.IOException e) {
					e.printStackTrace();
				}
			}
		}
		return readBytes;
	}

	/**
	 * 判断是否是wifi连接
	 */
	public static boolean isWifi(android.content.Context context) {
		android.net.ConnectivityManager cm = (android.net.ConnectivityManager) context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE);

		if (cm == null)
			return false;
		return cm.getActiveNetworkInfo().getType() == android.net.ConnectivityManager.TYPE_WIFI;

	}

	/**
	 * 打开网络设置界面
	 */
	public static void openSetting(android.app.Activity activity) {
		android.content.Intent intent = new android.content.Intent("/");
		android.content.ComponentName cm = new android.content.ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
		intent.setComponent(cm);
		intent.setAction("android.intent.action.VIEW");
		activity.startActivityForResult(intent, 0);
	}

	/**
	 * 是否快速的移动网络
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isFastMobileNetwork(android.content.Context context) {
		android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) context.getSystemService(android.content.Context.TELEPHONY_SERVICE);
		if (telephonyManager == null) {
			return false;
		}

		switch (telephonyManager.getNetworkType()) {
		case android.telephony.TelephonyManager.NETWORK_TYPE_1xRTT:
			return false;
		case android.telephony.TelephonyManager.NETWORK_TYPE_CDMA:
			return false;
		case android.telephony.TelephonyManager.NETWORK_TYPE_EDGE:
			return false;
		case android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_0:
			return true;
		case android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_A:
			return true;
		case android.telephony.TelephonyManager.NETWORK_TYPE_GPRS:
			return false;
		case android.telephony.TelephonyManager.NETWORK_TYPE_HSDPA:
			return true;
		case android.telephony.TelephonyManager.NETWORK_TYPE_HSPA:
			return true;
		case android.telephony.TelephonyManager.NETWORK_TYPE_HSUPA:
			return true;
		case android.telephony.TelephonyManager.NETWORK_TYPE_UMTS:
			return true;
		case android.telephony.TelephonyManager.NETWORK_TYPE_EHRPD:
			return true;
		case android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_B:
			return true;
		case android.telephony.TelephonyManager.NETWORK_TYPE_HSPAP:
			return true;
		case android.telephony.TelephonyManager.NETWORK_TYPE_IDEN:
			return false;
		case android.telephony.TelephonyManager.NETWORK_TYPE_LTE:
			return true;
		case android.telephony.TelephonyManager.NETWORK_TYPE_UNKNOWN:
			return false;
		default:
			return false;
		}
	}
}
