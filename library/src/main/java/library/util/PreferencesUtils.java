package library.util;

import android.content.Context;

/**
 * 
 * ClassName: PreferencesUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午4:58:14 <br/>
 * 
 * Android SharedPreferences相关工具类，<br/>
 * 可用于方便的向SharedPreferences中读取和写入相关类型数据，<br/>
 * 如：putString(Context, String, String) 保存string类型数据 <br/>
 * putInt(Context, String, int)保存int类型数据 <br/>
 * getString(Context, String) 获取string类型数据<br/>
 * getInt(Context, String)获取int类型数据 <br/>
 * 可通过修改PREFERENCE_NAME变量修改preference name<br/>
 * 
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class PreferencesUtils {

	public static String PREFERENCE_NAME = "TrineaAndroidCommon";

	private PreferencesUtils() {
		throw new AssertionError();
	}

	/**
	 * 将字符串参数
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putString(android.content.Context context, String key, String value) {
		android.content.SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, android.content.Context.MODE_PRIVATE);
		android.content.SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		return editor.commit();
	}

	/**
	 * 得到字符串的preference
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or null. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a string
	 * @see #getString(Context, String, String)
	 */
	public static String getString(android.content.Context context, String key) {
		return getString(context, key, null);
	}

	/**
	 * 得到字符串的preference
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a string
	 */
	public static String getString(android.content.Context context, String key, String defaultValue) {
		android.content.SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, android.content.Context.MODE_PRIVATE);
		return settings.getString(key, defaultValue);
	}

	/**
	 * 把int preference
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putInt(android.content.Context context, String key, int value) {
		android.content.SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, android.content.Context.MODE_PRIVATE);
		android.content.SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	/**
	 * 得到int preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a int
	 * @see #getInt(Context, String, int)
	 */
	public static int getInt(android.content.Context context, String key) {
		return getInt(context, key, -1);
	}

	/**
	 * 得到int preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a int
	 */
	public static int getInt(android.content.Context context, String key, int defaultValue) {
		android.content.SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, android.content.Context.MODE_PRIVATE);
		return settings.getInt(key, defaultValue);
	}

	/**
	 * 存储long类型的preference
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putLong(android.content.Context context, String key, long value) {
		android.content.SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, android.content.Context.MODE_PRIVATE);
		android.content.SharedPreferences.Editor editor = settings.edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	/**
	 * 获得long 类型 preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a long
	 * @see #getLong(Context, String, long)
	 */
	public static long getLong(android.content.Context context, String key) {
		return getLong(context, key, -1);
	}

	/**
	 * 获得long 类型 preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a long
	 */
	public static long getLong(android.content.Context context, String key, long defaultValue) {
		android.content.SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, android.content.Context.MODE_PRIVATE);
		return settings.getLong(key, defaultValue);
	}

	/**
	 * 存储 float preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putFloat(android.content.Context context, String key, float value) {
		android.content.SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, android.content.Context.MODE_PRIVATE);
		android.content.SharedPreferences.Editor editor = settings.edit();
		editor.putFloat(key, value);
		return editor.commit();
	}

	/**
	 * 获取 float preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a float
	 * @see #getFloat(Context, String, float)
	 */
	public static float getFloat(android.content.Context context, String key) {
		return getFloat(context, key, -1);
	}

	/**
	 * 获取 float preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a float
	 */
	public static float getFloat(android.content.Context context, String key, float defaultValue) {
		android.content.SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, android.content.Context.MODE_PRIVATE);
		return settings.getFloat(key, defaultValue);
	}

	/**
	 * 存储 boolean preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putBoolean(android.content.Context context, String key, boolean value) {
		android.content.SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, android.content.Context.MODE_PRIVATE);
		android.content.SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	/**
	 * 获取 boolean preferences, default is false
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or false. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a boolean
	 * @see #getBoolean(Context, String, boolean)
	 */
	public static boolean getBoolean(android.content.Context context, String key) {
		return getBoolean(context, key, false);
	}

	/**
	 * 获取 boolean preferences
	 * 
	 * @param context
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a boolean
	 */
	public static boolean getBoolean(android.content.Context context, String key, boolean defaultValue) {
		android.content.SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, android.content.Context.MODE_PRIVATE);
		return settings.getBoolean(key, defaultValue);
	}
}
