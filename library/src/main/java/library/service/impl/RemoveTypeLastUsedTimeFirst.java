package library.service.impl;



/**
 * 
 * ClassName: RemoveTypeLastUsedTimeFirst <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:28:16 <br/>
 * 拆卸式缓存时已满。当高速缓存已满，比较对象缓存最近使用的时间，如果时间是较小的删除它
 * @author chenhao
 * @version @param <T>
 * @since JDK 1.6
 */
public class RemoveTypeLastUsedTimeFirst<T> implements library.service.CacheFullRemoveType<T> {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(library.entity.CacheObject<T> obj1, library.entity.CacheObject<T> obj2) {
        return (obj1.getLastUsedTime() > obj2.getLastUsedTime()) ? 1 : ((obj1.getLastUsedTime() == obj2.getLastUsedTime()) ? 0 : -1);
    }
}
