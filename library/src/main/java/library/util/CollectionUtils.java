package library.util;

/**
 * 
 * ClassName: CollectionUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午5:52:09 <br/>
 * 收藏
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class CollectionUtils {

    /** default join separator **/
    public static final CharSequence DEFAULT_JOIN_SEPARATOR = ",";

    private CollectionUtils() {
        throw new AssertionError();
    }

    /**
     * is null or its size is 0
     * 
     * <pre>
     * isEmpty(null)   =   true;
     * isEmpty({})     =   true;
     * isEmpty({1})    =   false;
     * </pre>
     * 
     * @param <V>
     * @param c
     * @return if collection is null or its size is 0, return true, else return
     *         false.
     */
    public static <V> boolean isEmpty(java.util.Collection<V> c) {
        return (c == null || c.size() == 0);
    }

    /**
     * join collection to string, separator is {@link #DEFAULT_JOIN_SEPARATOR}
     * 
     * <pre>
     * join(null)      =   "";
     * join({})        =   "";
     * join({a,b})     =   "a,b";
     * </pre>
     * 
     * @param collection
     * @return join collection to string, separator is
     *         {@link #DEFAULT_JOIN_SEPARATOR}. if collection is empty, return
     *         ""
     */
    public static String join(@SuppressWarnings("rawtypes") Iterable collection) {
        return collection == null ? "" : android.text.TextUtils.join(DEFAULT_JOIN_SEPARATOR, collection);
    }
}
