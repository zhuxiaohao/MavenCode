/**
 * Project Name:android-common-tools
 * File Name:Log.java
 * Package Name:com.android.common.util
 * Date:2015年3月2日上午10:25:59
 * Copyright (c) 2015, zhuxiaohao All Rights Reserved.
 *
 */

package library.util;

/**
 * ClassName:Log <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年3月2日 上午10:25:59 <br/>
 * 
 * @author chenhao
 * @version
 * @since JDK 1.6
 * @see 日志工具类
 */
public class Log {

    private Log() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "way";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            library.util.Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            library.util.Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            library.util.Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            library.util.Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            library.util.Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            library.util.Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            library.util.Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            library.util.Log.i(tag, msg);
    }
}
