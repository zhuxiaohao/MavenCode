package library.entity;

/**
 * 
 * ClassName: PatchResult <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:19:17 <br/>
 * Patch Result
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class PatchResult {

    private int status;
    private String message;

    public PatchResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * get status
     * 
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * set status
     * 
     * @param status
     *            the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * get message
     * 
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * set message
     * 
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
