package library.util;


import library.service.impl.ImageCache;
import library.service.impl.ImageMemoryCache.OnImageCallbackListener;
import library.service.impl.ImageSDCardCache.OnImageSDCallbackListener;

/**
 * 
 * ClassName: ImageCacheManager <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:39:41 <br/>
 * 图像缓存管理器
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class ImageCacheManager {

    public static final String      TAG              = "ImageCacheManager";
    private static ImageCache imageCache       = null;
    private static library.service.impl.ImageSDCardCache imageSDCardCache = null;

    private ImageCacheManager() {
        throw new AssertionError();
    }

    /**
     * get the singleton instance of {@link ImageCache}
     * 
     * @return
     */
    public static ImageCache getImageCache() {
        if (imageCache == null) {
            synchronized (CacheManager.class) {
                if (imageCache == null) {
                    imageCache = new ImageCache(128, 512);
                    setImageCache();
                }
            }
        }
        return imageCache;
    }

    /**
     * get the singleton instance of {@link ImageSDCardCache}
     * 
     * @return
     */
    public static library.service.impl.ImageSDCardCache getImageSDCardCache() {
        if (imageSDCardCache == null) {
            synchronized (CacheManager.class) {
                if (imageSDCardCache == null) {
                    imageSDCardCache = new library.service.impl.ImageSDCardCache();
                    setImageSDCardCache();
                }
            }
        }
        return imageSDCardCache;
    }

    /**
     * set ImageCache properties
     */
    private static void setImageCache() {
        if (imageCache == null) {
            return;
        }

        OnImageCallbackListener imageCallBack = new OnImageCallbackListener() {

            @Override
            public void onGetSuccess(String imageUrl, android.graphics.Bitmap loadedImage, android.view.View view, boolean isInCache) {
                if (view != null && loadedImage != null) {
                    if (view instanceof android.widget.ImageView) {
                        android.widget.ImageView imageView = (android.widget.ImageView)view;
                        imageView.setImageBitmap(loadedImage);
                        // first time show with animation
                        if (!isInCache) {
                            imageView.startAnimation(getInAlphaAnimation(2000));
                        }
                    } else {
                        android.util.Log.e(TAG,
                                "View is not instance of ImageView, you need to setOnImageCallbackListener() by your self");
                    }
                }
            }

            @Override
            public void onPreGet(String imageUrl, android.view.View view) {}

            @Override
            public void onGetFailed(String imageUrl, android.graphics.Bitmap loadedImage, android.view.View view, library.entity.FailedReason failedReason) {}

            @Override
            public void onGetNotInCache(String imageUrl, android.view.View view) {}
        };
        imageCache.setOnImageCallbackListener(imageCallBack);
        imageCache.setCacheFullRemoveType(new library.service.impl.RemoveTypeLastUsedTimeFirst<android.graphics.Bitmap>());

        imageCache.setHttpReadTimeOut(10000);
        imageCache.setValidTime(-1);
    }

    /**
     * set ImageSDCardCache properties
     */
    private static void setImageSDCardCache() {
        if (imageSDCardCache == null) {
            return;
        }

        OnImageSDCallbackListener imageCallBack = new OnImageSDCallbackListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void onGetSuccess(String imageUrl, String imagePath, android.view.View view, boolean isInCache) {
                if (view != null && view instanceof android.widget.ImageView) {
                    android.widget.ImageView imageView = (android.widget.ImageView)view;

                    // if oom please use BitmapFactory.decodeFile(imagePath, option)
                    android.graphics.Bitmap bm = android.graphics.BitmapFactory.decodeFile(imagePath);
                    if (bm != null) {
                        imageView.setImageBitmap(bm);

                        // first time show with animation
                        if (!isInCache) {
                            imageView.startAnimation(getInAlphaAnimation(2000));
                        }
                    }
                } else {
                    android.util.Log.e(TAG,
                            "View is not instance of ImageView, you need to setOnImageSDCallbackListener() by your self");
                }
            }

            @Override
            public void onPreGet(String imageUrl, android.view.View view) {}

            @Override
            public void onGetNotInCache(String imageUrl, android.view.View view) {}

            @Override
            public void onGetFailed(String imageUrl, String imagePath, android.view.View view, library.entity.FailedReason failedReason) {}
        };
        imageSDCardCache.setOnImageSDCallbackListener(imageCallBack);
        imageSDCardCache.setCacheFullRemoveType(new library.service.impl.RemoveTypeLastUsedTimeFirst<String>());
        imageSDCardCache.setFileNameRule(new library.service.impl.FileNameRuleImageUrl());

        imageSDCardCache.setHttpReadTimeOut(10000);
        imageSDCardCache.setValidTime(-1);
    }

    public static android.view.animation.AlphaAnimation getInAlphaAnimation(long durationMillis) {
        android.view.animation.AlphaAnimation inAlphaAnimation = new android.view.animation.AlphaAnimation(0, 1);
        inAlphaAnimation.setDuration(durationMillis);
        return inAlphaAnimation;
    }

    /**
     * get image from sdcard listener
     * 
     * @return
     */
    public static library.service.impl.PreloadDataCache.OnGetDataListener<String, android.graphics.Bitmap> getImageFromSdcardListener() {
        return new library.service.impl.PreloadDataCache.OnGetDataListener<String, android.graphics.Bitmap>() {

            private static final long serialVersionUID = 1L;

            @Override
            public library.entity.CacheObject<android.graphics.Bitmap> onGetData(String key) {
                if (FileUtils.isFileExist(key)) {
                    // if oom please use BitmapFactory.decodeFile(imagePath, option),like this
                    // BitmapFactory.Options option = new BitmapFactory.Options();
                    // option.inSampleSize = 2;
                    // b = BitmapFactory.decodeFile(key, option);
                    return new library.entity.CacheObject<android.graphics.Bitmap>(android.graphics.BitmapFactory.decodeFile(key));
                } else
                    return null;

            }
        };
    }
}
