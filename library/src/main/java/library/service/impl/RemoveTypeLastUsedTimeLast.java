package library.service.impl;



/**
 * 
 * ClassName: RemoveTypeLastUsedTimeLast <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:28:49 <br/>
 * 删除类型时，高速缓存，当缓存满了，比较对象缓存最近使用的时间，如果时间大首先删除。
 * @author chenhao
 * @version @param <T>
 * @since JDK 1.6
 */
public class RemoveTypeLastUsedTimeLast<T> implements library.service.CacheFullRemoveType<T> {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(library.entity.CacheObject<T> obj1, library.entity.CacheObject<T> obj2) {
        return (obj2.getLastUsedTime() > obj1.getLastUsedTime()) ? 1 : ((obj2.getLastUsedTime() == obj1.getLastUsedTime()) ? 0 : -1);
    }
}
