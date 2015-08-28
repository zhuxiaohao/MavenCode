package library.service.impl;


/**
 * 
 * ClassName: RemoveTypeNotRemove <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:29:15 <br/>
 * 拆卸式缓存时已满。没有删除任何一个，这意味着没有什么可以把后
 * @author chenhao
 * @version @param <T>
 * @since JDK 1.6
 */
public class RemoveTypeNotRemove<T> implements library.service.CacheFullRemoveType<T> {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(library.entity.CacheObject<T> obj1, library.entity.CacheObject<T> obj2) {
        return 0;
    }
}
