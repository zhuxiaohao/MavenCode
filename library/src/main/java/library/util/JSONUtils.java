package library.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * ClassName: JSONUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午4:58:38 <br/>
 * JSONUtils工具类，可用于方便的向Json中读取和写入相关类型数据，如： String
 * getString(JSONObjectjsonObject, String key, String defaultValue)
 * 得到string类型value <br/>
 * String getString(String jsonData, String key, String defaultValue)
 * 得到string类型value<br/>
 * 表示从json中读取某个String类型key的值getMap(JSONObject jsonObject, String key) 得到map<br/>
 * getMap(String jsonData, String key) 得到map 表示从json中读取某个Map类型key的值<br/>
 * 
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class JSONUtils {

	public static boolean isPrintException = true;

	private JSONUtils() {
		throw new AssertionError();
	}

	/**
	 * 得到jsonObject长度
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getLong(String)} exception, return
	 *         defaultValue</li>
	 *         <li>return {@link JSONObject#getLong(String)}</li>
	 *         </ul>
	 */
	public static Long getLong(org.json.JSONObject jsonObject, String key, Long defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getLong(key);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * 从jsonData获得长度
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONUtils#getLong(JSONObject, String, JSONObject)}</li>
	 *         </ul>
	 */
	public static Long getLong(String jsonData, String key, Long defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(jsonData);
			return getLong(jsonObject, key, defaultValue);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * @param json对象
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JSONUtils#getLong(JSONObject, String, Long)
	 */
	public static long getLong(org.json.JSONObject jsonObject, String key, long defaultValue) {
		return getLong(jsonObject, key, (Long) defaultValue);
	}

	/**
	 * @param json数据
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JSONUtils#getLong(String, String, Long)
	 */
	public static long getLong(String jsonData, String key, long defaultValue) {
		return getLong(jsonData, key, (Long) defaultValue);
	}

	/**
	 * 从json对象得到Int
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getInt(String)} exception, return
	 *         defaultValue</li>
	 *         <li>return {@link JSONObject#getInt(String)}</li>
	 *         </ul>
	 */
	public static Integer getInt(org.json.JSONObject jsonObject, String key, Integer defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getInt(key);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * 从json数据得到Int
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONUtils#getInt(JSONObject, String, JSONObject)}</li>
	 *         </ul>
	 */
	public static Integer getInt(String jsonData, String key, Integer defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(jsonData);
			return getInt(jsonObject, key, defaultValue);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * @param json对象
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JSONUtils#getInt(JSONObject, String, Integer)
	 */
	public static int getInt(org.json.JSONObject jsonObject, String key, int defaultValue) {
		return getInt(jsonObject, key, (Integer) defaultValue);
	}

	/**
	 * @param json对象
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JSONUtils#getInt(String, String, Integer)
	 */
	public static int getInt(String jsonData, String key, int defaultValue) {
		return getInt(jsonData, key, (Integer) defaultValue);
	}

	/**
	 * 从json对象获得double
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getDouble(String)} exception, return
	 *         defaultValue</li>
	 *         <li>return {@link JSONObject#getDouble(String)}</li>
	 *         </ul>
	 */
	public static Double getDouble(org.json.JSONObject jsonObject, String key, Double defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getDouble(key);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * 从json数据得到双
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONUtils#getDouble(JSONObject, String, JSONObject)}</li>
	 *         </ul>
	 */
	public static Double getDouble(String jsonData, String key, Double defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(jsonData);
			return getDouble(jsonObject, key, defaultValue);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * @param json对象
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JSONUtils#getDouble(JSONObject, String, Double)
	 */
	public static double getDouble(org.json.JSONObject jsonObject, String key, double defaultValue) {
		return getDouble(jsonObject, key, (Double) defaultValue);
	}

	/**
	 * @param json对象
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JSONUtils#getDouble(String, String, Double)
	 */
	public static double getDouble(String jsonData, String key, double defaultValue) {
		return getDouble(jsonData, key, (Double) defaultValue);
	}

	/**
	 * 获取 jsono对象
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getString(String)} exception, return
	 *         defaultValue</li>
	 *         <li>return {@link JSONObject#getString(String)}</li>
	 *         </ul>
	 */
	public static String getString(org.json.JSONObject jsonObject, String key, String defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getString(key);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * 从json数据获取字符串
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONUtils#getString(JSONObject, String, JSONObject)}</li>
	 *         </ul>
	 */
	public static String getString(String jsonData, String key, String defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(jsonData);
			return getString(jsonObject, key, defaultValue);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * 从json对象得到的字符串
	 * 
	 * @param jsonObject
	 * @param defaultValue
	 * @param keyArray
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if keyArray is null or empty, return defaultValue</li>
	 *         <li>get {@link #getJSONObject(JSONObject, String, JSONObject)} by
	 *         recursion, return it. if anyone is null, return directly</li>
	 *         </ul>
	 */
	public static String getStringCascade(org.json.JSONObject jsonObject, String defaultValue, String... keyArray) {
		if (jsonObject == null || ArrayUtils.isEmpty(keyArray)) {
			return defaultValue;
		}

		String data = jsonObject.toString();
		for (String key : keyArray) {
			data = getStringCascade(data, key, defaultValue);
			if (data == null) {
				return defaultValue;
			}
		}
		return data;
	}

	/**
	 * 从json数据获取字符串
	 * 
	 * @param jsonData
	 * @param defaultValue
	 * @param keyArray
	 * @return <ul>
	 *         <li>if jsonData is null, return defaultValue</li>
	 *         <li>if keyArray is null or empty, return defaultValue</li>
	 *         <li>get {@link #getJSONObject(JSONObject, String, JSONObject)} by
	 *         recursion, return it. if anyone is null, return directly</li>
	 *         </ul>
	 */
	public static String getStringCascade(String jsonData, String defaultValue, String... keyArray) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		String data = jsonData;
		for (String key : keyArray) {
			data = getString(data, key, defaultValue);
			if (data == null) {
				return defaultValue;
			}
		}
		return data;
	}

	/**
	 * 从json对象获取字符串数组
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getJSONArray(String)} exception, return
	 *         defaultValue</li>
	 *         <li>if {@link JSONArray#getString(int)} exception, return
	 *         defaultValue</li>
	 *         <li>return string array</li>
	 *         </ul>
	 */
	public static String[] getStringArray(org.json.JSONObject jsonObject, String key, String[] defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			org.json.JSONArray statusArray = jsonObject.getJSONArray(key);
			if (statusArray != null) {
				String[] value = new String[statusArray.length()];
				for (int i = 0; i < statusArray.length(); i++) {
					value[i] = statusArray.getString(i);
				}
				return value;
			}
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
		return defaultValue;
	}

	/**
	 * 从json数据获取字符串数组
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONUtils#getStringArray(JSONObject, String, JSONObject)}</li>
	 *         </ul>
	 */
	public static String[] getStringArray(String jsonData, String key, String[] defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(jsonData);
			return getStringArray(jsonObject, key, defaultValue);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * 从json对象得到字符串列表
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getJSONArray(String)} exception, return
	 *         defaultValue</li>
	 *         <li>if {@link JSONArray#getString(int)} exception, return
	 *         defaultValue</li>
	 *         <li>return string array</li>
	 *         </ul>
	 */
	public static java.util.List<String> getStringList(org.json.JSONObject jsonObject, String key, java.util.List<String> defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			org.json.JSONArray statusArray = jsonObject.getJSONArray(key);
			if (statusArray != null) {
				java.util.List<String> list = new java.util.ArrayList<String>();
				for (int i = 0; i < statusArray.length(); i++) {
					list.add(statusArray.getString(i));
				}
				return list;
			}
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
		return defaultValue;
	}

	/**
	 * 从json数据获取字符串列表
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONUtils#getStringList(JSONObject, String, List)}</li>
	 *         </ul>
	 */
	public static java.util.List<String> getStringList(String jsonData, String key, java.util.List<String> defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(jsonData);
			return getStringList(jsonObject, key, defaultValue);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * 得到JSONObject从json对象
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getJSONObject(String)} exception, return
	 *         defaultValue</li>
	 *         <li>return {@link JSONObject#getJSONObject(String)}</li>
	 *         </ul>
	 */
	public static org.json.JSONObject getJSONObject(org.json.JSONObject jsonObject, String key, org.json.JSONObject defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getJSONObject(key);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * 从JSON数据获取JSON对象
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonData is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONUtils#getJSONObject(JSONObject, String, JSONObject)}</li>
	 *         </ul>
	 */
	public static org.json.JSONObject getJSONObject(String jsonData, String key, org.json.JSONObject defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(jsonData);
			return getJSONObject(jsonObject, key, defaultValue);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * 得到JSONO bject从json对象
	 * 
	 * @param jsonObject
	 * @param defaultValue
	 * @param keyArray
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if keyArray is null or empty, return defaultValue</li>
	 *         <li>get {@link #getJSONObject(JSONObject, String, JSONObject)} by
	 *         recursion, return it. if anyone is null, return directly</li>
	 *         </ul>
	 */
	public static org.json.JSONObject getJSONObjectCascade(org.json.JSONObject jsonObject, org.json.JSONObject defaultValue, String... keyArray) {
		if (jsonObject == null || ArrayUtils.isEmpty(keyArray)) {
			return defaultValue;
		}

		org.json.JSONObject js = jsonObject;
		for (String key : keyArray) {
			js = getJSONObject(js, key, defaultValue);
			if (js == null) {
				return defaultValue;
			}
		}
		return js;
	}

	/**
	 * 从JSON数据获取JSON对象
	 * 
	 * @param jsonData
	 * @param defaultValue
	 * @param keyArray
	 * @return <ul>
	 *         <li>if jsonData is null, return defaultValue</li>
	 *         <li>if keyArray is null or empty, return defaultValue</li>
	 *         <li>get {@link #getJSONObject(JSONObject, String, JSONObject)} by
	 *         recursion, return it. if anyone is null, return directly</li>
	 *         </ul>
	 */
	public static org.json.JSONObject getJSONObjectCascade(String jsonData, org.json.JSONObject defaultValue, String... keyArray) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(jsonData);
			return getJSONObjectCascade(jsonObject, defaultValue, keyArray);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * 从JSON对象获取JSON数组
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getJSONArray(String)} exception, return
	 *         defaultValue</li>
	 *         <li>return {@link JSONObject#getJSONArray(String)}</li>
	 *         </ul>
	 */
	public static org.json.JSONArray getJSONArray(org.json.JSONObject jsonObject, String key, org.json.JSONArray defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getJSONArray(key);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * 从JSON数据得到JSON数组
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONUtils#getJSONArray(JSONObject, String, JSONObject)}</li>
	 *         </ul>
	 */
	public static org.json.JSONArray getJSONArray(String jsonData, String key, org.json.JSONArray defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(jsonData);
			return getJSONArray(jsonObject, key, defaultValue);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * 布尔从json Object
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>return {@link JSONObject#getBoolean(String)}</li>
	 *         </ul>
	 */
	public static boolean getBoolean(org.json.JSONObject jsonObject, String key, Boolean defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getBoolean(key);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * 从json数据得到布尔
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONUtils#getBoolean(JSONObject, String, Boolean)}</li>
	 *         </ul>
	 */
	public static boolean getBoolean(String jsonData, String key, Boolean defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(jsonData);
			return getBoolean(jsonObject, key, defaultValue);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * 从json对象获取地图。
	 * 
	 * @param jsonObject
	 *            key-value pairs json
	 * @param key
	 * @return <ul>
	 *         <li>if jsonObject is null, return null</li>
	 *         <li>return {@link JSONUtils#parseKeyAndValueToMap(String)}</li>
	 *         </ul>
	 */
	public static java.util.Map<String, String> getMap(org.json.JSONObject jsonObject, String key) {
		return library.util.JSONUtils.parseKeyAndValueToMap(library.util.JSONUtils.getString(jsonObject, key, null));
	}

	/**
	 * 从json数据获取地图
	 * 
	 * @param jsonData
	 *            key-value pairs string
	 * @param key
	 * @return <ul>
	 *         <li>if jsonData is null, return null</li>
	 *         <li>if jsonData length is 0, return empty map</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)}
	 *         exception,return null</li>
	 *         <li>return {@link JSONUtils#getMap(JSONObject, String)}</li>
	 *         </ul>
	 */
	public static java.util.Map<String, String> getMap(String jsonData, String key) {

		if (jsonData == null) {
			return null;
		}
		if (jsonData.length() == 0) {
			return new java.util.HashMap<String, String>();
		}

		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(jsonData);
			return getMap(jsonObject, key);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * 解析映射的键值对。忽略空的键,如果getValue异常,把空值
	 * 
	 * @param sourceObj
	 *            key-value pairs json
	 * @return <ul>
	 *         <li>if sourceObj is null, return null</li>
	 *         <li>else parse entry by
	 *         {@link MapUtils#putMapNotEmptyKey(Map, String, String)} one byone
	 *         </li>
	 *         </ul>
	 */
	@SuppressWarnings("rawtypes")
	public static java.util.Map<String, String> parseKeyAndValueToMap(org.json.JSONObject sourceObj) {
		if (sourceObj == null) {
			return null;
		}

		java.util.Map<String, String> keyAndValueMap = new java.util.HashMap<String, String>();
		for (java.util.Iterator iter = sourceObj.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			MapUtils.putMapNotEmptyKey(keyAndValueMap, key, getString(sourceObj, key, ""));

		}
		return keyAndValueMap;
	}

	/**
	 * 解析映射的键值对。忽略空的键,如果getValue异常,把空值
	 * 
	 * @param 源键
	 *            -值对json
	 * @return <ul>
	 *         <li>if source is null or source's length is 0, return empty map</li>
	 *         <li>if source {@link JSONObject#JSONObject(String)} exception,
	 *         return null</li>
	 *         <li>return {@link JSONUtils#parseKeyAndValueToMap(JSONObject)}</li>
	 *         </ul>
	 */
	public static java.util.Map<String, String> parseKeyAndValueToMap(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}

		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(source);
			return parseKeyAndValueToMap(jsonObject);
		} catch (org.json.JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
