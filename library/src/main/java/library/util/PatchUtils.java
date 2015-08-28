package library.util;


/**
 * 
 * ClassName: PatchUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:44:22 <br/>
 * 修补工具
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class PatchUtils {

    /**
     * patch old apk and patch file to new apk
     * 
     * @param oldApkPath
     * @param patchPath
     * @param newApkPath
     * @return
     */
    public static native library.entity.PatchResult patch(String oldApkPath, String patchPath, String newApkPath);
}
