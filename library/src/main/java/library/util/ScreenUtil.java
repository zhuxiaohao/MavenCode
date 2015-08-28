package library.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/***
 * 
 * ClassName: ScreenUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:45:48 <br/>
 * 屏幕的工具
 * 
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class ScreenUtil extends DeviceUtils {
    /**
     * dip/dp transform px
     * 
     * @param context
     *            activity
     * @param dpValue
     *            dip/dp value
     * @return px value
     */
    public static int dip2px(android.content.Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px transform dp/dip
     * 
     * @param context
     *            activity
     * @param pxValue
     *            px value
     * @return dp/dip value
     */
    public static int px2dip(android.content.Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * get the width of screen
     * 
     * @param activity
     * @return width in px
     */
    public static int getScreenWith(android.app.Activity activity) {
        int width = 0;
        android.util.DisplayMetrics metric = new android.util.DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;
        return width;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * 
     * @param pxValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(float pxValue, android.content.Context context) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue, android.content.Context context) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getStatusBarHeight(android.app.Activity activity) {
        android.graphics.Rect frame = new android.graphics.Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        return statusBarHeight;
    }

    /**
     * 
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public static int getStatusHeight(android.app.Activity activity) {
        int statusHeight = 0;
        android.graphics.Rect localRect = new android.graphics.Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * get the height of screen
     * 
     * @param activity
     * @return height in px
     */
    public static int getScreenHeight(android.app.Activity activity) {
        int height = 0;
        android.util.DisplayMetrics metric = new android.util.DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        height = metric.heightPixels;
        return height;
    }

    /**
     * get the sum of lines with modules
     * 
     * @param activity
     * @param pageIndex
     *            0：include today topic;>0：normal
     * @return sum of lines
     */
    public static int getScreenItemlines(android.app.Activity activity, int pageIndex) {
        int h = getScreenHeight(activity);
        int ch = 0;
        if (pageIndex == 0) {
            ch = h - dip2px(activity, 190);
        } else {
            ch = h - dip2px(activity, 80);
        }
        int lines = ch / (dip2px(activity, 135));
        return lines;
    }

    /**
     * 获得屏幕高度
     * 
     * @param context
     * @return
     */
    public static int getScreenWidth(android.content.Context context) {
        android.view.WindowManager wm = (android.view.WindowManager) context.getSystemService(android.content.Context.WINDOW_SERVICE);
        android.util.DisplayMetrics outMetrics = new android.util.DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     * 
     * @param context
     * @return
     */
    public static int getScreenHeight(android.content.Context context) {
        android.view.WindowManager wm = (android.view.WindowManager) context.getSystemService(android.content.Context.WINDOW_SERVICE);
        android.util.DisplayMetrics outMetrics = new android.util.DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     * 
     * @param context
     * @return
     */
    public static int getStatusHeight(android.content.Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     * 
     * @param activity
     * @return
     */
    public static android.graphics.Bitmap snapShotWithStatusBar(android.app.Activity activity) {
        android.view.View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        android.graphics.Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        android.graphics.Bitmap bp = null;
        bp = android.graphics.Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     * 
     * @param activity
     * @return
     */
    public static android.graphics.Bitmap snapShotWithoutStatusBar(android.app.Activity activity) {
        android.view.View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        android.graphics.Bitmap bmp = view.getDrawingCache();
        android.graphics.Rect frame = new android.graphics.Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        android.graphics.Bitmap bp = null;
        bp = android.graphics.Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    public static float dpToPx(android.content.Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static float pxToDp(android.content.Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float dpToPxInt(android.content.Context context, float dp) {
        return (int) (dpToPx(context, dp) + 0.5f);
    }

    public static float pxToDpCeilInt(android.content.Context context, float px) {
        return (int) (pxToDp(context, px) + 0.5f);
    }
}
