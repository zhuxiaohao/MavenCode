package library.entity;

/**
 * 
 * ClassName: FailedReason <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:17:52 <br/>
 * 获取数据失败的原因
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class FailedReason {

    private library.entity.FailedReason.FailedType failedType;
    private Throwable cause;

    public FailedReason(library.entity.FailedReason.FailedType failedType, String cause) {
        this.failedType = failedType;
        this.cause = new Throwable(cause);
    }

    public FailedReason(library.entity.FailedReason.FailedType failedType, Throwable cause) {
        this.failedType = failedType;
        this.cause = cause;
    }

    /**
     * get failedType
     * 
     * @return the failedType
     */
    public library.entity.FailedReason.FailedType getFailedType() {
        return failedType;
    }

    /**
     * get cause
     * 
     * @return the cause
     */
    public Throwable getCause() {
        return cause;
    }

    public static enum FailedType {
        /** get image from network or save image to sdcard error **/
        ERROR_IO,
        /** get image with out of memory error **/
        ERROR_OUT_OF_MEMORY,
        /** reserved field, it's no use now, waiting to be perfect^_^ **/
        ERROR_UNKNOWN,
    }
}
