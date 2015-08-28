package library.dao;


/**
 * 
 * ClassName: HttpCacheDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月21日 下午2:18:34 <br/>
 * Http接口
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public interface HttpCacheDao {

    /**
     * insert HttpResponse
     * 
     * @param httpResponse
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insertHttpResponse(library.entity.HttpResponse httpResponse);

    /**
     * get HttpResponse by url
     * 
     * @param url
     * @return
     */
    public library.entity.HttpResponse getHttpResponse(String url);

    /**
     * get HttpResponses by type
     * 
     * @param type
     * @return
     */
    public java.util.Map<String, library.entity.HttpResponse> getHttpResponsesByType(int type);

    /**
     * delete all http response
     * 
     * @return
     */
    public int deleteAllHttpResponse();
}
