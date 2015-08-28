package library.service.impl;



/**
 * 
 * ClassName: RemoveTypeDataSmall <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:24:26 <br/>
 * Remove type when cache is full.when cache is full, compare data of object in cache, if data is smaller remove it first.
 * @author chenhao
 * @version @param <T>
 * @since JDK 1.6
 */
public class RemoveTypeDataSmall<T> implements library.service.CacheFullRemoveType<T> {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(library.entity.CacheObject<T> obj1, library.entity.CacheObject<T> obj2) {
        return library.util.ObjectUtils.compare(obj1.getData(), obj2.getData());
    }
}
