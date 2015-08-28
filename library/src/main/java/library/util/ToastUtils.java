/**
 * Project Name:android-common-tools
 * File Name:Toast.java
 * Package Name:com.android.common.util
 * Date:2015年3月2日上午10:27:16
 * Copyright (c) 2015, zhuxiaohao All Rights Reserved.
 *
 */

package library.util;

import android.content.Context;
import android.widget.Toast;

/**
 * ClassName:Toast <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年3月2日 上午10:27:16 <br/>
 * 
 * @author chenhao
 * @version
 * @since JDK 1.6
 * @see Toast统一管理类 
 */
public class ToastUtils {

    private ToastUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     * 
     * @param context
     * @param message
     */
    public static void showShort(android.content.Context context, CharSequence message) {
        if (isShow)
            android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     * 
     * @param context
     * @param message
     */
    public static void showShort(android.content.Context context, int message) {
        if (isShow)
            android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     * 
     * @param context
     * @param message
     */
    public static void showLong(android.content.Context context, CharSequence message) {
        if (isShow)
            android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     * 
     * @param context
     * @param message
     */
    public static void showLong(android.content.Context context, int message) {
        if (isShow)
            android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     * 
     * @param context
     * @param message
     * @param duration
     */
    public static void show(android.content.Context context, CharSequence message, int duration) {
        if (isShow)
            android.widget.Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     * 
     * @param context
     * @param message
     * @param duration
     */
    public static void show(android.content.Context context, int message, int duration) {
        if (isShow)
            android.widget.Toast.makeText(context, message, duration).show();
    }

}
