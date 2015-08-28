package library.dao;


/**
 * 
 * ClassName: ImageSDCardCacheDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:16:52 <br/>
 * ImageSDCardCacheDao  接口实现
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public interface ImageSDCardCacheDao {

    /**
     * 把所有行imageSDCardCache db的标签是相同的标签
     * <ul>
     * <strong>Attentions:</strong>
     * <li>If imageSDCardCache is null, do nothing</li>
     * <li>If tag is null or empty, do nothing</li>
     * </ul>
     * 
     * @param 图像SDCardCache
     * @param tag
     *            tag used to mark this cache when save to and load from db,
     *            should be unique and cannot be null or empty
     * @return
     */
    public boolean putIntoImageSDCardCache(library.service.impl.ImageSDCardCache imageSDCardCache, String tag);

    /**
     * 删除所有行db的标签是相同的标签,并插入
     * 数据在imageSDCardCache db
     * <ul>
     * <strong>Attentions:</strong>
     * <li>如果imageSDCardCache是空的,什么也不做</li>
     * <li>如果标签是null或空,什么也不做</li>
     * <li>将删除所有行db的在第一个标签是相同的标签吗</li>
     * </ul>
     * 
     * @param 图像SDCardCache
     * @return
     */
    public boolean deleteAndInsertImageSDCardCache(library.service.impl.ImageSDCardCache imageSDCardCache, String tag);
}
