package library.service;

import java.io.Serializable;

/**
 * 
 * ClassName: FileNameRule <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:33:22 <br/>
 * 文件命名规则，用于当保存图像链接
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public interface FileNameRule extends java.io.Serializable {

    /**
     * get file name, include suffix, it's optional to include folder.
     * 
     * @param imageUrl
     *            the url of image
     * @return
     */
    public String getFileName(String imageUrl);
}
