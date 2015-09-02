package library.util;

import java.util.List;

/**
 * 
 * ClassName: ListUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午5:02:07 <br/>
 * List工具类，可用于List常用操作， 如： isEmpty(List<V> sourceList) 判断List是否为空或长度为0<br/>
 * join(List<String> list, String separator) List转换为字符串，并以固定分隔符分割<br/>
 * addDistinctEntry(List<V> sourceList, V entry) 向list中添加不重复元素<br/>
 * 
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class ListUtils {

	/** 默认加入分隔符 **/
	public static final String DEFAULT_JOIN_SEPARATOR = ",";

	private ListUtils() {
		throw new AssertionError();
	}

	/**
	 * 获取列表的大小
	 * 
	 * <pre>
	 * getSize(null)   =   0;
	 * getSize({})     =   0;
	 * getSize({1})    =   1;
	 * </pre>
	 * 
	 * @param <V>
	 * @param sourceList
	 * @return if list is null or empty, return 0, else return
	 *         {@link List#size()}.
	 */
	public static <V> int getSize(java.util.List<V> sourceList) {
		return sourceList == null ? 0 : sourceList.size();
	}

	/**
	 * 是null或其大小为0
	 * 
	 * <pre>
	 * isEmpty(null)   =   true;
	 * isEmpty({})     =   true;
	 * isEmpty({1})    =   false;
	 * </pre>
	 * 
	 * @param <V>
	 * @param sourceList
	 * @return if list is null or its size is 0, return true, else return false.
	 */
	public static <V> boolean isEmpty(java.util.List<V> sourceList) {
		return (sourceList == null || sourceList.size() == 0);
	}

	/**
	 * 比较两个列表
	 * 
	 * <pre>
	 * isEquals(null, null) = true;
	 * isEquals(new ArrayList&lt;String&gt;(), null) = false;
	 * isEquals(null, new ArrayList&lt;String&gt;()) = false;
	 * isEquals(new ArrayList&lt;String&gt;(), new ArrayList&lt;String&gt;()) = true;
	 * </pre>
	 * 
	 * @param <V>
	 * @param actual
	 * @param expected
	 * @return
	 */
	public static <V> boolean isEquals(java.util.ArrayList<V> actual, java.util.ArrayList<V> expected) {
		if (actual == null) {
			return expected == null;
		}
		if (expected == null) {
			return false;
		}
		if (actual.size() != expected.size()) {
			return false;
		}

		for (int i = 0; i < actual.size(); i++) {
			if (!ObjectUtils.isEquals(actual.get(i), expected.get(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 加入列表字符串分隔符“,”
	 * 
	 * <pre>
	 * join(null)      =   "";
	 * join({})        =   "";
	 * join({a,b})     =   "a,b";
	 * </pre>
	 * 
	 * @param list
	 * @return join list to string, separator is ",". if list is empty, return
	 *         ""
	 */
	public static String join(java.util.List<String> list) {
		return join(list, DEFAULT_JOIN_SEPARATOR);
	}

	/**
	 * 加入列表字符串
	 * 
	 * <pre>
	 * join(null, '#')     =   "";
	 * join({}, '#')       =   "";
	 * join({a,b,c}, ' ')  =   "abc";
	 * join({a,b,c}, '#')  =   "a#b#c";
	 * </pre>
	 * 
	 * @param list
	 * @param separator
	 * @return join list to string. if list is empty, return ""
	 */
	public static String join(java.util.List<String> list, char separator) {
		return join(list, new String(new char[] { separator }));
	}

	/**
	 * 加入列表字符串。如果分隔符为空,则使用 {@link #DEFAULT_JOIN_SEPARATOR}
	 * 
	 * <pre>
	 * join(null, "#")     =   "";
	 * join({}, "#$")      =   "";
	 * join({a,b,c}, null) =   "a,b,c";
	 * join({a,b,c}, "")   =   "abc";
	 * join({a,b,c}, "#")  =   "a#b#c";
	 * join({a,b,c}, "#$") =   "a#$b#$c";
	 * </pre>
	 * 
	 * @param list
	 * @param separator
	 * @return join list to string with separator. if list is empty, return ""
	 */
	public static String join(java.util.List<String> list, String separator) {
		return list == null ? "" : android.text.TextUtils.join(separator, list);
	}

	/**
	 * 截然不同的条目添加到列表中
	 * @param <V>
	 * @param sourceList
	 * @param entry
	 * @return if entry already exist in sourceList, return false, else add it
	 *         and return true.
	 */
	public static <V> boolean addDistinctEntry(java.util.List<V> sourceList, V entry) {
		return (sourceList != null && !sourceList.contains(entry)) ? sourceList.add(entry) : false;
	}

	/**
	 * 从list2用于添加所有不同的条目
	 * @param <V>
	 * @param sourceList
	 * @param entryList
	 * @return the count of entries be added
	 */
	public static <V> int addDistinctList(java.util.List<V> sourceList, java.util.List<V> entryList) {
		if (sourceList == null || isEmpty(entryList)) {
			return 0;
		}

		int sourceCount = sourceList.size();
		for (V entry : entryList) {
			if (!sourceList.contains(entry)) {
				sourceList.add(entry);
			}
		}
		return sourceList.size() - sourceCount;
	}

	/**
	 * 删除重复的条目列表
	 * @param <V>
	 * @param sourceList
	 * @return the count of entries be removed
	 */
	public static <V> int distinctList(java.util.List<V> sourceList) {
		if (isEmpty(sourceList)) {
			return 0;
		}

		int sourceCount = sourceList.size();
		int sourceListSize = sourceList.size();
		for (int i = 0; i < sourceListSize; i++) {
			for (int j = (i + 1); j < sourceListSize; j++) {
				if (sourceList.get(i).equals(sourceList.get(j))) {
					sourceList.remove(j);
					sourceListSize = sourceList.size();
					j--;
				}
			}
		}
		return sourceCount - sourceList.size();
	}

	/**
	 * 非空条目添加到列表中
	 * @param sourceList
	 * @param value
	 * @return <ul>
	 *         <li>if sourceList is null, return false</li>
	 *         <li>if value is null, return false</li>
	 *         <li>return {@link List#add(Object)}</li>
	 *         </ul>
	 */
	public static <V> boolean addListNotNullValue(java.util.List<V> sourceList, V value) {
		return (sourceList != null && value != null) ? sourceList.add(value) : false;
	}

	/**
	 * @see {@link ArrayUtils#getLast(Object[], Object, Object, boolean)}
	 *      defaultValue is null, isCircle is true
	 */
	@SuppressWarnings("unchecked")
	public static <V> V getLast(java.util.List<V> sourceList, V value) {
		return (sourceList == null) ? null : (V) ArrayUtils.getLast(sourceList.toArray(), value, true);
	}

	/**
	 * @see {@link ArrayUtils#getNext(Object[], Object, Object, boolean)}
	 *      defaultValue is null, isCircle is true
	 */
	@SuppressWarnings("unchecked")
	public static <V> V getNext(java.util.List<V> sourceList, V value) {
		return (sourceList == null) ? null : (V) ArrayUtils.getNext(sourceList.toArray(), value, true);
	}

	/**
	 * 转化列表
	 * @param <V>
	 * @param sourceList
	 * @return
	 */
	public static <V> java.util.List<V> invertList(java.util.List<V> sourceList) {
		if (isEmpty(sourceList)) {
			return sourceList;
		}

		java.util.List<V> invertList = new java.util.ArrayList<V>(sourceList.size());
		for (int i = sourceList.size() - 1; i >= 0; i--) {
			invertList.add(sourceList.get(i));
		}
		return invertList;
	}
}
