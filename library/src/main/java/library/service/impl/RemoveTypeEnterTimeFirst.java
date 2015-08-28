package library.service.impl;


/**
 * 
 * ClassName: RemoveTypeEnterTimeFirst <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:24:58 <br/>
 * 删除类型时，高速缓存，当高速缓存已满，进入时间的高速缓存中的对象进行比较，如果时间是较小的删除它。同时FIFO
 * @author chenhao
 * @version @param <T>
 * @since JDK 1.6
 */
public class RemoveTypeEnterTimeFirst<T> implements library.service.CacheFullRemoveType<T> {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(library.entity.CacheObject<T> obj1, library.entity.CacheObject<T> obj2) {
        return (obj1.getEnterTime() > obj2.getEnterTime()) ? 1 : ((obj1.getEnterTime() == obj2.getEnterTime()) ? 0 : -1);
    }
}
