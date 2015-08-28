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
    public static int dp2px(android.content.Context context, float dpVal)
    {  
        return (int) android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());  
    }  
  
    /** 
     * sp转px 
     *  
     * @param context 
     * @param val 
     * @return 
     */  
    public static int sp2px(android.content.Context context, float spVal)
    {  
        return (int) android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());  
    }  
  
    /** 
     * px转dp 
     *  
     * @param context 
     * @param pxVal 
     * @return 
     */  
    public static float px2dp(android.content.Context context, float pxVal)
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
    public static float px2sp(android.content.Context context, float pxVal)
    {  
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);  
    }  
  
} 

