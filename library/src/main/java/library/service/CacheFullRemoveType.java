package library.service;

/**
 * 
 * ClassName: CacheFullRemoveType <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:33:00 <br/>
 *拆卸式缓存时已满。当高速缓存已满，比较对象是与这类高速缓存，删除您可以实现此接口的最小。
 * @author chenhao
 * @version @param <V>
 * @since JDK 1.6
 */
public interface CacheFullRemoveType<V> extends java.io.Serializable {

    /**
     * compare object <br/>
     * <ul>
     * <strong>About result</strong>
     * <li>if obj1 > obj2, return 1</li>
     * <li>if obj1 = obj2, return 0</li>
     * <li>if obj1 < obj2, return -1</li>
     * </ul>
     * 
     * @param obj1
     * @param obj2
     * @return
     */
    public int compare(library.entity.CacheObject<V> obj1, library.entity.CacheObject<V> obj2);
}
