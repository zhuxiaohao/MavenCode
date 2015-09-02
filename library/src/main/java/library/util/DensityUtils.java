/**
 * Project Name:android-common-tools
 * File Name:DensityUtils.java
 * Package Name:com.android.common.util
 * Date:2015年3月2日上午10:31:46
 * Copyright (c) 2015, zhuxiaohao All Rights Reserved.
 *
*/

package library.util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.TypedValue;

/**
 * ClassName:DensityUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年3月2日 上午10:31:46 <br/>
 * @author   chenhao
 * @version  
 * @since    JDK 1.6
 * @see 	 常用单位转换的辅助类 
 */
public class DensityUtils  
{  
    private DensityUtils()  
    {  
        /* cannot be instantiated */  
        throw new UnsupportedOperationException("cannot be instantiated");  
    }  
  
    /** 
     * dp转px 
     *  
     * @param context 
     * @param val 
     * @return 
     */  
    public static int dp2px(Context context, float dpVal)
    {  
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());  
    }  
  
    /** 
     * sp转px 
     *  
     * @param context 
     * @param val 
     * @return 
     */  
    public static int sp2px(Context context, float spVal)
    {  
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());  
    }  
  
    /** 
     * px转dp 
     *  
     * @param context 
     * @param pxVal 
     * @return 
     */  
    public static float px2dp(Context context, float pxVal)
    {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (pxVal / scale);  
    }  
  
    /** 
     * px转sp 
     *  
     * @param fontScale 
     * @param pxVal 
     * @return 
     */  
    public static float px2sp(Context context, float pxVal)
    {  
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);  
    }


    private static final float DOT_FIVE = 0.5f;


    /**
     * dip to px
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dip2px(Context context, float dip) {
        float density = getDensity(context);
        return (int) (dip * density + DensityUtils.DOT_FIVE);
    }

    /**
     * px to dip
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2dip(Context context, float px) {
        float density = getDensity(context);
        return (int) (px / density + DOT_FIVE);
    }

    private static android.util.DisplayMetrics sDisplayMetrics;

    /**
     * get screen width
     *
     * @param context
     * @return
     */
    public static int getDisplayWidth(Context context) {
        initDisplayMetrics(context);
        return sDisplayMetrics.widthPixels;
    }

    /**
     * get screen height
     *
     * @param context
     * @return
     */
    public static int getDisplayHeight(Context context) {
        initDisplayMetrics(context);
        return sDisplayMetrics.heightPixels;
    }

    /**
     * get screen density
     *
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        initDisplayMetrics(context);
        return sDisplayMetrics.density;
    }


    /**
     * get screen density dpi
     *
     * @param context
     * @return
     */
    public static int getDensityDpi(Context context) {
        initDisplayMetrics(context);
        return sDisplayMetrics.densityDpi;
    }

    /**
     * init display metrics
     *
     * @param context
     */
    private static synchronized void initDisplayMetrics(Context context) {
        sDisplayMetrics = context.getResources().getDisplayMetrics();
    }

    /**
     * is landscape
     *
     * @param context
     * @return
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * is portrait
     *
     * @param context
     * @return
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
  
} 

