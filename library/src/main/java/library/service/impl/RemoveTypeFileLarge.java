package library.service.impl;



/**
 * 
 * ClassName: RemoveTypeFileLarge <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:27:14 <br/>
 * 删除类型时，高速缓存已满，缓存的数据类型是字符串，它代表一个文件的路径。
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class RemoveTypeFileLarge implements library.service.CacheFullRemoveType<String> {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(library.entity.CacheObject<String> obj1, library.entity.CacheObject<String> obj2) {
        long sizeOfFile1 = (obj1 == null ? -1 : library.util.FileUtils.getFileSize(obj1.getData()));
        long sizeOfFile2 = (obj2 == null ? -1 : library.util.FileUtils.getFileSize(obj2.getData()));
        if (sizeOfFile1 == sizeOfFile2) {
            if (obj1.getUsedCount() == obj2.getUsedCount()) {
                return (obj1.getEnterTime() > obj2.getEnterTime()) ? 1 : ((obj1.getEnterTime() == obj2.getEnterTime()) ? 0 : -1);
            }
            return (obj1.getUsedCount() > obj2.getUsedCount() ? 1 : -1);
        }
        return (sizeOfFile2 > sizeOfFile1 ? 1 : -1);
    }
}
