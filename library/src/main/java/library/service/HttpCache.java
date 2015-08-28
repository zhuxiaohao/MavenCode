package library.service;



import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import library.entity.HttpRequest;
import library.entity.HttpResponse;

/**
 * 
 * ClassName: HttpCache <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:33:52 <br/>
 * 它适用于把数据从服务器和缓存API，如JSON或XML等。它适用于应用像威信，微博，推特，淘宝等。如果要缓存的图像，请使用
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class HttpCache {

    @SuppressWarnings("unused")
    private android.content.Context context;

    /** http memory cache **/
    private java.util.Map<String, HttpResponse> cache;
    /** dao to get data from http db cache **/
    private library.dao.HttpCacheDao httpCacheDao;
    private int type = -1;

    /** Default {@link Executor} that be used to execute tasks in parallel. **/
    public static final java.util.concurrent.Executor THREAD_POOL_EXECUTOR = java.util.concurrent.Executors.newFixedThreadPool(library.util.SystemUtils.DEFAULT_THREAD_POOL_SIZE);

    public HttpCache(android.content.Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The context can not be null.");
        }
        this.context = context;
        cache = new ConcurrentHashMap<String, HttpResponse>();
        httpCacheDao = new library.dao.impl.HttpCacheDaoImpl(library.util.SqliteUtils.getInstance(context));
    }

    /**
     * waiting to be perfect^_^
     * 
     * @param context
     * @param type
     *            get httpResponse whose type is type into memory as primary
     *            cache to improve performance
     */
    private HttpCache(android.content.Context context, int type) {
        this(context);
        this.type = type;
        initData(type);
    }

    /**
     * get httpResponse whose type is type into memory as primary cache to
     * improve performance
     * 
     * @param type
     */
    private void initData(int type) {
        this.cache = httpCacheDao.getHttpResponsesByType(type);
        if (cache == null) {
            cache = new HashMap<String, HttpResponse>();
        }
    }

    /**
     * http get
     * <ul>
     * <strong>Attentions:</strong>
     * <li>Don't call this on the ui thread, it may costs some times. Becaust if
     * not in cache, it get from network synchronous.</li>
     * <li>If you want get data asynchronous, use
     * {@link HttpCache#httpGet(HttpRequest, HttpCacheListener)}</li>
     * </ul>
     * 
     * @param httpRequest
     * @return the response of the url, if null represents http error
     */
    public HttpResponse httpGet(HttpRequest request) {
        String url;
        if (request == null || library.util.StringUtils.isEmpty(url = request.getUrl())) {
            return null;
        }

        HttpResponse cacheResponse = null;
        boolean isNoCache = false, isNoStore = false;
        String requestCacheControl = request.getRequestProperty(library.controller.HttpConstants.CACHE_CONTROL);
        if (!library.util.StringUtils.isEmpty(requestCacheControl)) {
            String[] requestCacheControls = requestCacheControl.split(",");
            if (!library.util.ArrayUtils.isEmpty(requestCacheControls)) {
                java.util.List<String> requestCacheControlList = new java.util.ArrayList<String>();
                for (String s : requestCacheControls) {
                    if (s == null) {
                        continue;
                    }
                    requestCacheControlList.add(s.trim());
                }
                if (requestCacheControlList.contains("no-cache")) {
                    isNoCache = true;
                }
                if (requestCacheControlList.contains("no-store")) {
                    isNoStore = true;
                }
            }
        }
        if (!isNoCache) {
            cacheResponse = getFromCache(url);
        }
        return cacheResponse == null ? (isNoStore ? library.util.HttpUtils.httpGet(url) : putIntoCache(library.util.HttpUtils.httpGet(url))) : cacheResponse;
    }

    /**
     * http get
     * <ul>
     * <li>It gets data from cache or network asynchronous.</li>
     * <li>If you want get data synchronous, use
     * {@link HttpCache#httpGet(HttpRequest)} or
     * {@link HttpCache#httpGetString(HttpRequest)}</li>
     * </ul>
     * 
     * @param url
     * @param listener
     *            listener which can do something before or after HttpGet. this
     *            can be null if you not want to do something
     */
    public void httpGet(String url, library.service.HttpCache.HttpCacheListener listener) {
        // if bigger than android 4.0 use executeOnExecutor, else use execute
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            new library.service.HttpCache.HttpCacheStringAsyncTask(listener).executeOnExecutor(THREAD_POOL_EXECUTOR, url);
        } else {
            new library.service.HttpCache.HttpCacheStringAsyncTask(listener).execute(url);
        }
    }

    /**
     * http get
     * <ul>
     * <li>It gets data from cache or network asynchronous.</li>
     * <li>If you want get data synchronous, use
     * {@link HttpCache#httpGet(HttpRequest)} or
     * {@link HttpCache#httpGetString(HttpRequest)}</li>
     * </ul>
     * 
     * @param request
     * @param listener
     *            listener which can do something before or after HttpGet. this
     *            can be null if you not want to do something
     */
    public void httpGet(HttpRequest request, library.service.HttpCache.HttpCacheListener listener) {
        // if bigger than android 4.0 use executeOnExecutor, else use execute
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            new library.service.HttpCache.HttpCacheRequestAsyncTask(listener).executeOnExecutor(THREAD_POOL_EXECUTOR, request);
        } else {
            new library.service.HttpCache.HttpCacheRequestAsyncTask(listener).execute(request);
        }
    }

    /**
     * http get
     * <ul>
     * <strong>Attentions:</strong>
     * <li>Don't call this on the ui thread, it may costs some times. Becaust if
     * not in cache, it get from network synchronous.</li>
     * <li>If you want get data asynchronous, use
     * {@link HttpCache#httpGet(HttpRequest, HttpCacheListener)}</li>
     * </ul>
     * 
     * @param url
     * @return the response of the url, if null represents http error
     */
    public HttpResponse httpGet(String url) {
        return httpGet(new HttpRequest(url));
    }

    /**
     * http get
     * <ul>
     * <strong>Attentions:</strong>
     * <li>Don't call this on the ui thread, it may costs some times. Becaust if
     * not in cache, it get from network synchronous.</li>
     * <li>If you want get data asynchronous, use
     * {@link HttpCache#httpGet(String, HttpCacheListener)}</li>
     * </ul>
     * 
     * @param url
     * @return the response body of the url, if null represents http error
     */
    public String httpGetString(String url) {
        HttpResponse cacheResponse = httpGet(new HttpRequest(url));
        return cacheResponse == null ? null : cacheResponse.getResponseBody();
    }

    /**
     * http get
     * <ul>
     * <strong>Attentions:</strong>
     * <li>Don't call this on the ui thread, it may costs some times. Becaust if
     * not in cache, it get from network synchronous.</li>
     * <li>If you want get data asynchronous, use
     * {@link HttpCache#httpGet(HttpRequest, HttpCacheListener)}</li>
     * </ul>
     * 
     * @param httpRequest
     * @return the response body of the url, if null represents http error
     */
    public HttpResponse httpGetString(HttpRequest httpRequest) {
        return httpGet(httpRequest);
    }

    /**
     * whether this cache contains the specified url.
     * 
     * @param url
     * @return true if this cache contains the specified url and the element is
     *         valid, false otherwise.
     */
    public boolean containsKey(String url) {
        return getFromCache(url) != null;
    }

    /**
     * whether the element of the specified url has invalided
     * 
     * @param url
     * @return true if the element of the specified url has invalided, false
     *         otherwise.
     */
    protected boolean isExpired(String url) {
        return getFromCache(url) == null;
    }

    /**
     * Removes all elements from this cache, leaving it empty.
     */
    public void clear() {
        cache.clear();
        httpCacheDao.deleteAllHttpResponse();
    }

    /**
     * HttpCacheListener, can do something before or after HttpGet
     * 
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a>
     *         2013-11-15
     */
    public static abstract class HttpCacheListener {

        /**
         * Runs on the UI thread before httpGet.<br/>
         * <ul>
         * <li>this can be null if you not want to do something</li>
         * </ul>
         */
        protected void onPreGet() {
        }

        /**
         * Runs on the UI thread after httpGet. The httpResponse is returned by
         * httpGet.
         * <ul>
         * <li>this can be null if you not want to do something</li>
         * </ul>
         * 
         * @param httpResponse
         *            get by the url
         * @param isInCache
         *            the data responsed to the url whether is in cache
         */
        protected void onPostGet(HttpResponse httpResponse, boolean isInCache) {
        }
    }

    /**
     * get type, waiting to be perfect^_^
     * 
     * @return the type
     */
    private int getType() {
        return type;
    }

    /**
     * put response into cache
     * <ul>
     * <li>put response to db, if {@link HttpResponse#getType()} ==
     * {@link HttpCache#getType()}, also put into memory cache</li>
     * </ul>
     * 
     * @param httpResponse
     * @return if insert into db error, return null, otherwise return
     *         HttpResponse
     */
    private HttpResponse putIntoCache(HttpResponse httpResponse) {
        String url;
        if (httpResponse == null || (url = httpResponse.getUrl()) == null) {
            return null;
        }

        if (type != -1 && type == httpResponse.getType()) {
            cache.put(url, httpResponse);
        }
        return (httpCacheDao.insertHttpResponse(httpResponse) == -1) ? null : httpResponse;
    }

    /**
     * get from memory cache first, if not exist in memory cache, get from db
     * 
     * @param url
     * @return <ul>
     *         <li>if neither exit in memory cache nor db, return null</li>
     *         <li>if is expired, return null, otherwise return cache response</li>
     *         </ul>
     */
    public HttpResponse getFromCache(String url) {
        if (library.util.StringUtils.isEmpty(url)) {
            return null;
        }

        HttpResponse cacheResponse = cache.get(url);
        if (cacheResponse == null) {
            cacheResponse = httpCacheDao.getHttpResponse(url);
        }
        return (cacheResponse == null || cacheResponse.isExpired()) ? null : cacheResponse.setInCache(true);
    }

    /**
     * AsyncTask to get data by String url
     * 
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a>
     *         2013-11-15
     */
    private class HttpCacheStringAsyncTask extends android.os.AsyncTask<String, Void, HttpResponse> {

        private library.service.HttpCache.HttpCacheListener listener;

        public HttpCacheStringAsyncTask(library.service.HttpCache.HttpCacheListener listener) {
            this.listener = listener;
        }

        protected HttpResponse doInBackground(String... url) {
            if (library.util.ArrayUtils.isEmpty(url)) {
                return null;
            }
            return httpGet(url[0]);
        }

        protected void onPreExecute() {
            if (listener != null) {
                listener.onPreGet();
            }
        }

        protected void onPostExecute(HttpResponse httpResponse) {
            if (listener != null) {
                listener.onPostGet(httpResponse, httpResponse == null ? false : httpResponse.isInCache());
            }
        }
    }

    /**
     * AsyncTask to get data by HttpRequest
     * 
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a>
     *         2013-11-15
     */
    private class HttpCacheRequestAsyncTask extends android.os.AsyncTask<HttpRequest, Void, HttpResponse> {

        private library.service.HttpCache.HttpCacheListener listener;

        public HttpCacheRequestAsyncTask(library.service.HttpCache.HttpCacheListener listener) {
            this.listener = listener;
        }

        protected HttpResponse doInBackground(HttpRequest... httpRequest) {
            if (library.util.ArrayUtils.isEmpty(httpRequest)) {
                return null;
            }
            return httpGet(httpRequest[0]);
        }

        protected void onPreExecute() {
            if (listener != null) {
                listener.onPreGet();
            }
        }

        protected void onPostExecute(HttpResponse httpResponse) {
            if (listener != null) {
                listener.onPostGet(httpResponse, httpResponse == null ? false : httpResponse.isInCache());
            }
        }
    }
}
