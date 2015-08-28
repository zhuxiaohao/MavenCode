package library.entity;

import java.net.URLConnection;


/**
 * 
 * ClassName: HttpRequest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:18:21 <br/>
 * HTTP请求
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class HttpRequest {

    private String url;
    private int connectTimeout;
    private int readTimeout;
    private java.util.Map<String, String> parasMap;
    private java.util.Map<String, String> requestProperties;

    public HttpRequest(String url) {
        this.url = url;
        this.connectTimeout = -1;
        this.readTimeout = -1;
        requestProperties = new java.util.HashMap<String, String>();
    }

    public HttpRequest(String url, java.util.Map<String, String> parasMap) {
        this.url = url;
        this.parasMap = parasMap;
        this.connectTimeout = -1;
        this.readTimeout = -1;
        requestProperties = new java.util.HashMap<String, String>();
    }

    public String getUrl() {
        return url;
    }

    /**
     * @return
     * @see URLConnection#getConnectTimeout()
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * @param timeoutMillis
     * @see URLConnection#setConnectTimeout(int)
     */
    public void setConnectTimeout(int timeoutMillis) {
        if (timeoutMillis < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        connectTimeout = timeoutMillis;
    }

    /**
     * @return
     * @see URLConnection#getReadTimeout()
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * @param timeoutMillis
     * @see URLConnection#setReadTimeout(int)
     */
    public void setReadTimeout(int timeoutMillis) {
        if (timeoutMillis < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        readTimeout = timeoutMillis;
    }

    /**
     * get paras map
     * 
     * @return
     */
    public java.util.Map<String, String> getParasMap() {
        return parasMap;
    }

    /**
     * set paras map
     * 
     * @param parasMap
     */
    public void setParasMap(java.util.Map<String, String> parasMap) {
        this.parasMap = parasMap;
    }

    /**
     * @return paras as string
     */
    public String getParas() {
        return library.util.HttpUtils.joinParasWithEncodedValue(parasMap);
    }

    /**
     * @param field
     * @param newValue
     * @see URLConnection#setRequestProperty(String, String)
     */
    public void setRequestProperty(String field, String newValue) {
        requestProperties.put(field, newValue);
    }

    /**
     * @param field
     * @see URLConnection#getRequestProperty(String)
     */
    public String getRequestProperty(String field) {
        return requestProperties.get(field);
    }

    /**
     * same to {@link #setRequestProperty(String, String)} filed is User-Agent
     * 
     * @param value
     * @see URLConnection#setRequestProperty(String, String)
     */
    public void setUserAgent(String value) {
        requestProperties.put("User-Agent", value);
    }

    /**
     * @return
     */
    public java.util.Map<String, String> getRequestProperties() {
        return requestProperties;
    }

    /**
     * @param requestProperties
     */
    public void setRequestProperties(java.util.Map<String, String> requestProperties) {
        this.requestProperties = requestProperties;
    }
}
