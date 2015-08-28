package library.service.impl;


import library.entity.FailedReason.FailedType;

/**
 * 
 * ClassName: ImageMemoryCache <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:21:33 <br/>
 * 图像内存缓存
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class ImageMemoryCache extends PreloadDataCache<String, android.graphics.Bitmap> {

    private static final long serialVersionUID = 1L;

    private static final String TAG = "ImageCache";

    /** callback interface when getting image **/
    private library.service.impl.ImageMemoryCache.OnImageCallbackListener onImageCallbackListener;
    /** http read image time out, if less than 0, not set. default is not set **/
    private int httpReadTimeOut = -1;
    /**
     * whether open waiting queue, default is true. If true, save all view
     * waiting for image loaded, else only save the newest one
     **/
    private boolean isOpenWaitingQueue = true;
    /** http request properties **/
    private java.util.Map<String, String> requestProperties = null;

    /** recommend default max cache size according to dalvik max memory **/
    public static final int DEFAULT_MAX_SIZE = getDefaultMaxSize();
    /** message what for get image successfully **/
    private static final int WHAT_GET_IMAGE_SUCCESS = 1;
    /** message what for get image failed **/
    private static final int WHAT_GET_IMAGE_FAILED = 2;

    /**
     * thread pool whose wait for data got, attention, not the get data thread
     * pool
     **/
    private transient java.util.concurrent.ExecutorService threadPool = java.util.concurrent.Executors.newFixedThreadPool(library.util.SystemUtils.DEFAULT_THREAD_POOL_SIZE);
    /**
     * key is image url, value is the newest view which waiting for image
     * loaded, used when {@link #isOpenWaitingQueue} is false
     **/
    private transient java.util.Map<String, android.view.View> viewMap;
    /**
     * key is image url, value is view set those waiting for image loaded, used
     * when {@link #isOpenWaitingQueue} is true
     **/
    private transient java.util.Map<String, java.util.HashSet<android.view.View>> viewSetMap;

    private transient android.os.Handler handler;

    /**
     * get image asynchronous. when get image success, it will pass to
     * {@link OnImageCallbackListener#onGetSuccess(String, Bitmap, View, boolean)}
     * 
     * @param imageUrl
     * @param view
     * @return whether image already in cache or not
     */
    public boolean get(String imageUrl, android.view.View view) {
        return get(imageUrl, null, view);
    }

    /**
     * get image asynchronous and preload other images asynchronous according to
     * urlList
     * 
     * @param imageUrl
     * @param urlList
     *            url list, if is null, not preload, else preload forward by
     *            {@link PreloadDataCache#preloadDataForward(Object, List, int)}
     *            , preload backward by
     *            {@link PreloadDataCache#preloadDataBackward(Object, List, int)}
     * @param view
     * @return whether image already in cache or not
     */
    public boolean get(final String imageUrl, final java.util.List<String> urlList, final android.view.View view) {
        if (onImageCallbackListener != null) {
            onImageCallbackListener.onPreGet(imageUrl, view);
        }

        if (library.util.StringUtils.isEmpty(imageUrl)) {
            if (onImageCallbackListener != null) {
                onImageCallbackListener.onGetNotInCache(imageUrl, view);
            }
            return false;
        }

        /**
         * if already in cache, call onImageSDCallbackListener, else new thread
         * to wait for it
         */
        library.entity.CacheObject<android.graphics.Bitmap> object = getFromCache(imageUrl, urlList);
        if (object != null) {
            android.graphics.Bitmap bitmap = object.getData();
            if (bitmap != null) {
                onGetSuccess(imageUrl, bitmap, view, true);
                return true;
            } else {
                remove(imageUrl);
            }
        }

        if (isOpenWaitingQueue) {
            synchronized (viewSetMap) {
                java.util.HashSet<android.view.View> viewSet = viewSetMap.get(imageUrl);
                if (viewSet == null) {
                    viewSet = new java.util.HashSet<android.view.View>();
                    viewSetMap.put(imageUrl, viewSet);
                }
                viewSet.add(view);
            }
        } else {
            viewMap.put(imageUrl, view);
        }

        if (onImageCallbackListener != null) {
            onImageCallbackListener.onGetNotInCache(imageUrl, view);
        }
        if (isExistGettingDataThread(imageUrl)) {
            return false;
        }

        startGetImageThread(imageUrl, urlList);
        return false;
    }

    /**
     * get callback interface when getting image
     * 
     * @return the onImageCallbackListener
     */
    public library.service.impl.ImageMemoryCache.OnImageCallbackListener getOnImageCallbackListener() {
        return onImageCallbackListener;
    }

    /**
     * set callback interface when getting image
     * 
     * @param onImageCallbackListener
     */
    public void setOnImageCallbackListener(library.service.impl.ImageMemoryCache.OnImageCallbackListener onImageCallbackListener) {
        this.onImageCallbackListener = onImageCallbackListener;
    }

    /**
     * get http read image time out, if less than 0, not set. default is not set
     * 
     * @return the httpReadTimeOut
     */
    public int getHttpReadTimeOut() {
        return httpReadTimeOut;
    }

    /**
     * set http read image time out, if less than 0, not set. default is not
     * set, in mills
     * 
     * @param readTimeOutMillis
     */
    public void setHttpReadTimeOut(int readTimeOutMillis) {
        this.httpReadTimeOut = readTimeOutMillis;
    }

    /**
     * get whether open waiting queue, default is true. If true, save all view
     * waiting for image loaded, else only save the newest one
     * 
     * @return
     */
    public boolean isOpenWaitingQueue() {
        return isOpenWaitingQueue;
    }

    /**
     * set whether open waiting queue, default is true. If true, save all view
     * waiting for image loaded, else only save the newest one
     * 
     * @param isOpenWaitingQueue
     */
    public void setOpenWaitingQueue(boolean isOpenWaitingQueue) {
        this.isOpenWaitingQueue = isOpenWaitingQueue;
    }

    /**
     * set http request properties
     * <ul>
     * <li>If image is from the different server,
     * setRequestProperty("Connection", "false") is recommended. If image is
     * from the same server, true is recommended, and this is the default value</li>
     * </ul>
     * 
     * @param requestProperties
     */
    public void setRequestProperties(java.util.Map<String, String> requestProperties) {
        this.requestProperties = requestProperties;
    }

    /**
     * get http request properties
     * 
     * @return
     */
    public java.util.Map<String, String> getRequestProperties() {
        return requestProperties;
    }

    /**
     * Sets the value of the http request header field
     * 
     * @param field
     *            the request header field to be set
     * @param newValue
     *            the new value of the specified property
     * @see {@link #setRequestProperties(Map)}
     */
    public void setRequestProperty(String field, String newValue) {
        if (library.util.StringUtils.isEmpty(field)) {
            return;
        }

        if (requestProperties == null) {
            requestProperties = new java.util.HashMap<String, String>();
        }
        requestProperties.put(field, newValue);
    }

    /**
     * <ul>
     * <li>Get data listener is {@link #getDefaultOnGetImageListener()}</li>
     * <li>callback interface when getting image is null, can set by
     * {@link #setOnImageCallbackListener(OnImageCallbackListener)}</li>
     * <li>Maximum size of the cache is {@link #DEFAULT_MAX_SIZE}</li>
     * <li>Elements of the cache will not invalid</li>
     * <li>Remove type is {@link RemoveTypeUsedCountSmall} when cache is full</li>
     * </ul>
     * 
     * @see PreloadDataCache#PreloadDataCache()
     */
    public ImageMemoryCache() {
        this(DEFAULT_MAX_SIZE, PreloadDataCache.DEFAULT_THREAD_POOL_SIZE);
    }

    /**
     * <ul>
     * <li>Get data listener is {@link #getDefaultOnGetImageListener()}</li>
     * <li>callback interface when getting image is null, can set by
     * {@link #setOnImageCallbackListener(OnImageCallbackListener)}</li>
     * <li>Elements of the cache will not invalid</li>
     * <li>Remove type is {@link RemoveTypeUsedCountSmall} when cache is full</li>
     * </ul>
     * 
     * @param maxSize
     *            maximum size of the cache
     * @see PreloadDataCache#PreloadDataCache(int)
     */
    public ImageMemoryCache(int maxSize) {
        this(maxSize, PreloadDataCache.DEFAULT_THREAD_POOL_SIZE);
    }

    /**
     * <ul>
     * <li>Get data listener is {@link #getDefaultOnGetImageListener()}</li>
     * <li>callback interface when getting image is null, can set by
     * {@link #setOnImageCallbackListener(OnImageCallbackListener)}</li>
     * <li>Elements of the cache will not invalid</li>
     * <li>Remove type is {@link RemoveTypeUsedCountSmall} when cache is full</li>
     * </ul>
     * 
     * @param maxSize
     *            maximum size of the cache
     * @param threadPoolSize
     *            getting data thread pool size
     * @see PreloadDataCache#PreloadDataCache(int, int)
     */
    public ImageMemoryCache(int maxSize, int threadPoolSize) {
        super(maxSize, threadPoolSize);

        super.setOnGetDataListener(getDefaultOnGetImageListener());
        super.setCacheFullRemoveType(new RemoveTypeUsedCountSmall<android.graphics.Bitmap>());
        this.viewMap = new java.util.concurrent.ConcurrentHashMap<String, android.view.View>();
        this.viewSetMap = new java.util.HashMap<String, java.util.HashSet<android.view.View>>();
        this.handler = new library.service.impl.ImageMemoryCache.MyHandler();
        if (android.os.Looper.myLooper() == null) {
            android.os.Looper.prepare();
        }
    }

    /**
     * callback interface when getting image
     * 
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a>
     *         2012-4-5
     */
    public interface OnImageCallbackListener {

        /**
         * callback function before get image, run on ui thread
         * 
         * @param imageUrl
         *            imageUrl
         * @param view
         *            view need the image
         */
        public void onPreGet(String imageUrl, android.view.View view);

        /**
         * callback function when get image but image not in cache, run on ui
         * thread.<br/>
         * Will be called after {@link #onPreGet(String, View)}, before
         * {@link #onGetSuccess(String, String, View, boolean)} and
         * {@link #onGetFailed(String, String, View, FailedReason)}
         * 
         * @param imageUrl
         *            imageUrl
         * @param view
         *            view need the image
         */
        public void onGetNotInCache(String imageUrl, android.view.View view);

        /**
         * callback function after get image successfully, run on ui thread
         * 
         * @param imageUrl
         *            imageUrl
         * @param loadedImage
         *            loaded image bitmap
         * @param view
         *            view need the image
         * @param isInCache
         *            whether already in cache or got realtime
         */
        public void onGetSuccess(String imageUrl, android.graphics.Bitmap loadedImage, android.view.View view, boolean isInCache);

        /**
         * callback function after get image failed, run on ui thread
         * 
         * @param imageUrl
         *            imageUrl
         * @param loadedImage
         *            loaded image bitmap
         * @param view
         *            view need the image
         * @param failedReason
         *            failed reason for get image
         */
        public void onGetFailed(String imageUrl, android.graphics.Bitmap loadedImage, android.view.View view, library.entity.FailedReason failedReason);
    }

    /**
     * @see ExecutorService#shutdown()
     */
    protected void shutdown() {
        threadPool.shutdown();
        super.shutdown();
    }

    /**
     * @see ExecutorService#shutdownNow()
     */
    public java.util.List<Runnable> shutdownNow() {
        threadPool.shutdownNow();
        return super.shutdownNow();
    }

    /**
     * My handler
     * 
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a>
     *         2012-11-20
     */
    private class MyHandler extends android.os.Handler {

        public void handleMessage(android.os.Message message) {
            switch (message.what) {
            case WHAT_GET_IMAGE_SUCCESS:
            case WHAT_GET_IMAGE_FAILED:
                library.service.impl.ImageMemoryCache.MessageObject object = (library.service.impl.ImageMemoryCache.MessageObject) message.obj;
                if (object == null) {
                    break;
                }

                String imageUrl = object.imageUrl;
                android.graphics.Bitmap bitmap = object.bitmap;
                if (onImageCallbackListener != null) {
                    if (isOpenWaitingQueue) {
                        synchronized (viewSetMap) {
                            java.util.HashSet<android.view.View> viewSet = viewSetMap.get(imageUrl);
                            if (viewSet != null) {
                                for (android.view.View view : viewSet) {
                                    if (view != null) {
                                        if (WHAT_GET_IMAGE_SUCCESS == message.what) {
                                            onGetSuccess(imageUrl, bitmap, view, false);
                                        } else {
                                            onImageCallbackListener.onGetFailed(imageUrl, bitmap, view, object.failedReason);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        android.view.View view = viewMap.get(imageUrl);
                        if (view != null) {
                            if (WHAT_GET_IMAGE_SUCCESS == message.what) {
                                onGetSuccess(imageUrl, bitmap, view, false);
                            } else {
                                onImageCallbackListener.onGetFailed(imageUrl, bitmap, view, object.failedReason);
                            }
                        }
                    }
                }

                if (isOpenWaitingQueue) {
                    synchronized (viewSetMap) {
                        viewSetMap.remove(imageUrl);
                    }
                } else {
                    viewMap.remove(imageUrl);
                }
                break;
            }
        }
    };

    private void onGetSuccess(String imageUrl, android.graphics.Bitmap loadedImage, android.view.View view, boolean isInCache) {
        if (onImageCallbackListener == null) {
            return;
        }

        try {
            onImageCallbackListener.onGetSuccess(imageUrl, loadedImage, view, isInCache);
        } catch (OutOfMemoryError e) {
            onImageCallbackListener.onGetFailed(imageUrl, loadedImage, view, new library.entity.FailedReason(FailedType.ERROR_OUT_OF_MEMORY, e));
        }
    }

    /**
     * message object
     * 
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a>
     *         2013-1-14
     */
    private class MessageObject {

        String imageUrl;
        android.graphics.Bitmap bitmap;
        library.entity.FailedReason failedReason;

        public MessageObject(String imageUrl, android.graphics.Bitmap bitmap) {
            this.imageUrl = imageUrl;
            this.bitmap = bitmap;
        }

        public MessageObject(String imageUrl, android.graphics.Bitmap bitmap, library.entity.FailedReason failedReason) {
            this.imageUrl = imageUrl;
            this.bitmap = bitmap;
            this.failedReason = failedReason;
        }

    }

    /**
     * start thread to wait for image get
     * 
     * @param imageUrl
     * @param urlList
     *            url list, if is null, not preload, else preload forward by
     *            {@link PreloadDataCache#preloadDataForward(Object, List, int)}
     *            , preload backward by
     *            {@link PreloadDataCache#preloadDataBackward(Object, List, int)}
     */
    private void startGetImageThread(final String imageUrl, final java.util.List<String> urlList) {
        // wait for image be got success and send message
        threadPool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    library.entity.CacheObject<android.graphics.Bitmap> object = get(imageUrl, urlList);
                    android.graphics.Bitmap bitmap = (object == null ? null : object.getData());
                    if (bitmap == null) {
                        // if bitmap is null, remove it
                        remove(imageUrl);
                        String failedException = "get image from network or save image to sdcard error. please make sure you have added permission android.permission.WRITE_EXTERNAL_STORAGE and android.permission.ACCESS_NETWORK_STATE";
                        library.entity.FailedReason failedReason = new library.entity.FailedReason(FailedType.ERROR_IO, failedException);
                        handler.sendMessage(handler.obtainMessage(WHAT_GET_IMAGE_FAILED, new library.service.impl.ImageMemoryCache.MessageObject(imageUrl, bitmap, failedReason)));
                    } else {
                        handler.sendMessage(handler.obtainMessage(WHAT_GET_IMAGE_SUCCESS, new library.service.impl.ImageMemoryCache.MessageObject(imageUrl, bitmap)));
                    }
                } catch (OutOfMemoryError e) {
                    library.service.impl.ImageMemoryCache.MessageObject msg = new library.service.impl.ImageMemoryCache.MessageObject(imageUrl, null, new library.entity.FailedReason(FailedType.ERROR_OUT_OF_MEMORY, e));
                    handler.sendMessage(handler.obtainMessage(WHAT_GET_IMAGE_FAILED, msg));
                }
            }
        });
    }

    /**
     * default get image from network listener
     * 
     * @return
     */
    public OnGetDataListener<String, android.graphics.Bitmap> getDefaultOnGetImageListener() {
        return new OnGetDataListener<String, android.graphics.Bitmap>() {

            private static final long serialVersionUID = 1L;

            @Override
            public library.entity.CacheObject<android.graphics.Bitmap> onGetData(String key) {
                android.graphics.Bitmap d = null;
                try {
                    d = library.util.ImageUtils.getBitmapFromUrl(key, httpReadTimeOut, requestProperties);
                } catch (Exception e) {
                    android.util.Log.e(TAG, "get image exception, imageUrl is:" + key, e);
                }
                return (d == null ? null : new library.entity.CacheObject<android.graphics.Bitmap>(d));
            }
        };
    }

    /**
     * get recommend default max cache size according to dalvik max memory
     * 
     * @return
     */
    static int getDefaultMaxSize() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        if (maxMemory > library.util.SizeUtils.GB_2_BYTE) {
            return 512;
        }

        int mb = (int) (maxMemory / library.util.SizeUtils.MB_2_BYTE);
        return mb > 16 ? mb * 2 : 16;
    }
}
