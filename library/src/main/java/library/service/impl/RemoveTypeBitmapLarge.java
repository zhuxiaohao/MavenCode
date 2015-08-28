package library.service.impl;


/**
 * 
 * ClassName: RemoveTypeBitmapLarge <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:22:58 <br/>
 * 删除类型时，高速缓存已满，缓存的数据类型是位图
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class RemoveTypeBitmapLarge implements library.service.CacheFullRemoveType<android.graphics.Bitmap> {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(library.entity.CacheObject<android.graphics.Bitmap> obj1, library.entity.CacheObject<android.graphics.Bitmap> obj2) {
        long sizeOfFile1 = getSize(obj1);
        long sizeOfFile2 = getSize(obj2);
        if (sizeOfFile1 == sizeOfFile2) {
            if (obj1.getUsedCount() == obj2.getUsedCount()) {
                return (obj1.getEnterTime() > obj2.getEnterTime()) ? 1 : ((obj1.getEnterTime() == obj2.getEnterTime()) ? 0 : -1);
            }
            return (obj1.getUsedCount() > obj2.getUsedCount() ? 1 : -1);
        }
        return (sizeOfFile2 > sizeOfFile1 ? 1 : -1);
    }

    /**
     * get size of bitmap
     * 
     * @param o
     * @return
     */
    private long getSize(library.entity.CacheObject<android.graphics.Bitmap> o) {
        if (o == null) {
            return -1;
        }

        // TODO is there any more efficient way?
        byte[] b = library.util.ImageUtils.bitmapToByte(o.getData());
        return (b == null ? -1 : b.length);
    }
}
