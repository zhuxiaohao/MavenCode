package library.service.impl;


/**
 * 
 * ClassName: RemoveTypeDataBig <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:23:48 <br/>
 * 拆卸式缓存时已满。当高速缓存已满，比较对象缓存中的数据，如果数据是大首先删除
 * @author chenhao
 * @version @param <T>
 * @since JDK 1.6
 */
public class RemoveTypeDataBig<T> implements library.service.CacheFullRemoveType<T> {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(library.entity.CacheObject<T> obj1, library.entity.CacheObject<T> obj2) {
        return library.util.ObjectUtils.compare(obj2.getData(), obj1.getData());
    }
}
