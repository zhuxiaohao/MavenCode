package library.service.impl;


/**
 * 
 * ClassName: FileNameRuleImageUrl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:20:30 <br/>
 *文 件命名规则，用于当保存图像
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class FileNameRuleImageUrl implements library.service.FileNameRule {

    private static final long serialVersionUID = 1L;

    /** default file name if image url is empty **/
    public static final String DEFAULT_FILE_NAME = "ImageSDCardCacheFile.jpg";
    /** max length of file name, not include suffix **/
    public static final int MAX_FILE_NAME_LENGTH = 127;

    private String fileExtension = null;

    @Override
    public String getFileName(String imageUrl) {
        if (library.util.StringUtils.isEmpty(imageUrl)) {
            return DEFAULT_FILE_NAME;
        }

        String ext = (fileExtension == null ? library.util.FileUtils.getFileExtension(imageUrl) : fileExtension);
        String fileName = (imageUrl.length() > MAX_FILE_NAME_LENGTH ? imageUrl.substring(imageUrl.length() - MAX_FILE_NAME_LENGTH, imageUrl.length()) : imageUrl).replaceAll("[\\W]", "_");
        return library.util.StringUtils.isEmpty(ext) ? fileName : (new StringBuilder().append(fileName).append(".").append(ext.replaceAll("[\\W]", "_")).toString());
    }

    public library.service.impl.FileNameRuleImageUrl setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
        return this;
    }
}
