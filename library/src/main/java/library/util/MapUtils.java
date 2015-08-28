package library.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * ClassName: MapUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午5:02:33 <br/>
 * Map工具类，可用于Map常用操作， 如： isEmpty(Map<K, V> sourceMap) 判断map是否为空或长度为0<br/>
 * parseKeyAndValueToMap(String source, String keyAndValueSeparator,StringkeyAndValuePairSeparator, boolean ignoreSpace) 字符串解析为map <br/>
 * toJson(Map<String,String> map) map转换为json格式<br/>
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class MapUtils {

	/** 默认的键和值之间的分隔符 **/
	public static final String DEFAULT_KEY_AND_VALUE_SEPARATOR = ":";
	/** 默认的分隔符之间的键-值对 **/
	public static final String DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR = ",";

	private MapUtils() {
		throw new AssertionError();
	}

	/**
	 * 是null或其大小为0
	 * 
	 * <pre>
	 * isEmpty(null)   =   true;
	 * isEmpty({})     =   true;
	 * isEmpty({1, 2})    =   false;
	 * </pre>
	 * 
	 * @param sourceMap
	 * @return if map is null or its size is 0, return true, else return false.
	 */
	public static <K, V> boolean isEmpty(java.util.Map<K, V> sourceMap) {
		return (sourceMap == null || sourceMap.size() == 0);
	}

	/**
	 * 键-值对添加到map,关键不需要null或空
	 * 
	 * @param map
	 * @param key
	 * @param value
	 * @return <ul>
	 *         <li>if map is null, return false</li>
	 *         <li>if key is null or empty, return false</li>
	 *         <li>return {@link Map#put(Object, Object)}</li>
	 *         </ul>
	 */
	public static boolean putMapNotEmptyKey(java.util.Map<String, String> map, String key, String value) {
		if (map == null || StringUtils.isEmpty(key)) {
			return false;
		}

		map.put(key, value);
		return true;
	}

	/**
	 *向map添加键值对,这两个键和值不需要null或空
	 * 
	 * @param map
	 * @param key
	 * @param value
	 * @return <ul>
	 *         <li>if map is null, return false</li>
	 *         <li>if key is null or empty, return false</li>
	 *         <li>if value is null or empty, return false</li>
	 *         <li>return {@link Map#put(Object, Object)}</li>
	 *         </ul>
	 */
	public static boolean putMapNotEmptyKeyAndValue(java.util.Map<String, String> map, String key, String value) {
		if (map == null || StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
			return false;
		}

		map.put(key, value);
		return true;
	}

	/**
	 * 　　向map添加键-值对,关键不需要null或空
	 * @param map
	 * @param key
	 * @param value
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if map is null, return false</li>
	 *         <li>if key is null or empty, return false</li>
	 *         <li>if value is null or empty, put defaultValue, return true</li>
	 *         <li>if value is neither null nor empty，put value, return true</li>
	 *         </ul>
	 */
	public static boolean putMapNotEmptyKeyAndValue(java.util.Map<String, String> map, String key, String value, String defaultValue) {
		if (map == null || StringUtils.isEmpty(key)) {
			return false;
		}

		map.put(key, StringUtils.isEmpty(value) ? defaultValue : value);
		return true;
	}

	/**
	 * 向map添加键-值对,关键不需要null
	 * 
	 * @param map
	 * @param key
	 * @param value
	 * @return <ul>
	 *         <li>if map is null, return false</li>
	 *         <li>if key is null, return false</li>
	 *         <li>return {@link Map#put(Object, Object)}</li>
	 *         </ul>
	 */
	public static <K, V> boolean putMapNotNullKey(java.util.Map<K, V> map, K key, V value) {
		if (map == null || key == null) {
			return false;
		}

		map.put(key, value);
		return true;
	}

	/**
	 * 向map添加键值对,这两个键和值不需要null
	 * 
	 * @param map
	 * @param key
	 * @param value
	 * @return <ul>
	 *         <li>if map is null, return false</li>
	 *         <li>if key is null, return false</li>
	 *         <li>if value is null, return false</li>
	 *         <li>return {@link Map#put(Object, Object)}</li>
	 *         </ul>
	 */
	public static <K, V> boolean putMapNotNullKeyAndValue(java.util.Map<K, V> map, K key, V value) {
		if (map == null || key == null || value == null) {
			return false;
		}

		map.put(key, value);
		return true;
	}

	/**
	 * 键的值,匹配第一个条目前面
	 * <ul>
	 * <strong>Attentions:</strong>
	 * <li>for HashMap, the order of entry not same to put order, so you may
	 * need to use TreeMap</li>
	 * </ul>
	 * 
	 * @param <V>
	 * @param map
	 * @param value
	 * @return <ul>
	 *         <li>if map is null, return null</li>
	 *         <li>if value exist, return key</li>
	 *         <li>return null</li>
	 *         </ul>
	 */
	public static <K, V> K getKeyByValue(java.util.Map<K, V> map, V value) {
		if (isEmpty(map)) {
			return null;
		}

		for (java.util.Map.Entry<K, V> entry : map.entrySet()) {
			if (ObjectUtils.isEquals(entry.getValue(), value)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * 键值解析映射,忽略空的关键
	 * 
	 * <pre>
	 * parseKeyAndValueToMap("","","",true)=null
	 * parseKeyAndValueToMap(null,"","",true)=null
	 * parseKeyAndValueToMap("a:b,:","","",true)={(a,b)}
	 * parseKeyAndValueToMap("a:b,:d","","",true)={(a,b)}
	 * parseKeyAndValueToMap("a:b,c:d","","",true)={(a,b),(c,d)}
	 * parseKeyAndValueToMap("a=b, c = d","=",",",true)={(a,b),(c,d)}
	 * parseKeyAndValueToMap("a=b, c = d","=",",",false)={(a, b),( c , d)}
	 * parseKeyAndValueToMap("a=b, c=d","=", ",", false)={(a,b),( c,d)}
	 * parseKeyAndValueToMap("a=b; c=d","=", ";", false)={(a,b),( c,d)}
	 * parseKeyAndValueToMap("a=b, c=d", ",", ";", false)={(a=b, c=d)}
	 * </pre>
	 * 
	 * @param source
	 *            key-value pairs
	 * @param keyAndValueSeparator
	 *            separator between key and value
	 * @param keyAndValuePairSeparator
	 *            separator between key-value pairs
	 * @param ignoreSpace
	 *            whether ignore space at the begging or end of key and value
	 * @return
	 */
	public static java.util.Map<String, String> parseKeyAndValueToMap(String source, String keyAndValueSeparator, String keyAndValuePairSeparator, boolean ignoreSpace) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}

		if (StringUtils.isEmpty(keyAndValueSeparator)) {
			keyAndValueSeparator = DEFAULT_KEY_AND_VALUE_SEPARATOR;
		}
		if (StringUtils.isEmpty(keyAndValuePairSeparator)) {
			keyAndValuePairSeparator = DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR;
		}
		java.util.Map<String, String> keyAndValueMap = new java.util.HashMap<String, String>();
		String[] keyAndValueArray = source.split(keyAndValuePairSeparator);
		if (keyAndValueArray == null) {
			return null;
		}

		int seperator;
		for (String valueEntity : keyAndValueArray) {
			if (!StringUtils.isEmpty(valueEntity)) {
				seperator = valueEntity.indexOf(keyAndValueSeparator);
				if (seperator != -1) {
					if (ignoreSpace) {
						library.util.MapUtils.putMapNotEmptyKey(keyAndValueMap, valueEntity.substring(0, seperator).trim(), valueEntity.substring(seperator + 1).trim());
					} else {
						library.util.MapUtils.putMapNotEmptyKey(keyAndValueMap, valueEntity.substring(0, seperator), valueEntity.substring(seperator + 1));
					}
				}
			}
		}
		return keyAndValueMap;
	}

	/**
	 * 键值解析映射,忽略空的关键
	 * 
	 * @param source
	 *            key-value pairs
	 * @param ignoreSpace
	 *            whether ignore space at the begging or end of key and value
	 * @return
	 * @see {@link MapUtils#parseKeyAndValueToMap(String, String, String, boolean)}
	 *      , keyAndValueSeparator is {@link #DEFAULT_KEY_AND_VALUE_SEPARATOR},
	 *      keyAndValuePairSeparator is
	 *      {@link #DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR}
	 */
	public static java.util.Map<String, String> parseKeyAndValueToMap(String source, boolean ignoreSpace) {
		return parseKeyAndValueToMap(source, DEFAULT_KEY_AND_VALUE_SEPARATOR, DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR, ignoreSpace);
	}

	/**
	 * 键值解析映射,忽略空键,忽略空间在乞讨或键和值
	 * 
	 * @param source
	 *            key-value pairs
	 * @return
	 * @see {@link MapUtils#parseKeyAndValueToMap(String, String, String, boolean)}
	 *      , keyAndValueSeparator is {@link #DEFAULT_KEY_AND_VALUE_SEPARATOR},
	 *      keyAndValuePairSeparator is
	 *      {@link #DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR}, ignoreSpace is true
	 */
	public static java.util.Map<String, String> parseKeyAndValueToMap(String source) {
		return parseKeyAndValueToMap(source, DEFAULT_KEY_AND_VALUE_SEPARATOR, DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR, true);
	}

	/**
	 * 加入map
	 * 
	 * @param map
	 * @return
	 */
	public static String toJson(java.util.Map<String, String> map) {
		if (map == null || map.size() == 0) {
			return null;
		}

		StringBuilder paras = new StringBuilder();
		paras.append("{");
		java.util.Iterator<java.util.Map.Entry<String, String>> ite = map.entrySet().iterator();
		while (ite.hasNext()) {
			java.util.Map.Entry<String, String> entry = (java.util.Map.Entry<String, String>) ite.next();
			paras.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");
			if (ite.hasNext()) {
				paras.append(",");
			}
		}
		paras.append("}");
		return paras.toString();
	}
}
