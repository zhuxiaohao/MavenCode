package library.util;

/**
 * Project Name:com.zhuxiaohao.common.util
 * File Name: android-common-master
 * Date:15/8/12下午4:1908
 * blog:http://blog.csdn.net/qq718799510?viewmode=contents
 * Copyright (c) 2015, zhuxiaohao All Rights Reserved.
 * 处理未知异常,避免出现xx 应用报错.
 */

public class CrashHandler implements java.lang.Thread.UncaughtExceptionHandler {
    private static final String TAG = library.util.CrashHandler.class.getSimpleName();

    private static library.util.CrashHandler instance; // 单例模式


    private android.content.Context context; // 程序Context对象
    private java.lang.Thread.UncaughtExceptionHandler defalutHandler; // 系统默认的UncaughtException处理类
    private java.text.DateFormat formatter = new java.text.SimpleDateFormat(
            "yyyy-MM-dd_HH-mm-ss.SSS", java.util.Locale.CHINA);

    private CrashHandler() {

    }

    /**
     * 获取CrashHandler实例
     *
     * @return CrashHandler
     */
    public static library.util.CrashHandler getInstance() {
        if (instance == null) {
            synchronized (library.util.CrashHandler.class) {
                if (instance == null) {
                    instance = new library.util.CrashHandler();
                }
            }
        }

        return instance;
    }

    /**
     * 异常处理初始化
     *
     * @param context
     */
    public void init(android.content.Context context) {
        this.context = context;
        // 获取系统默认的UncaughtException处理器
        defalutHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //异常发生时，先给蓝牙服务端发送OK命令


        // 自定义错误处理
        boolean res = handleException(ex);
        if (!res && defalutHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            defalutHandler.uncaughtException(thread, ex);

        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, e.toString());
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     *
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }

        new Thread() {

            @Override
            public void run() {
                android.os.Looper.prepare();

                ex.printStackTrace();
                String err = "[" + ex.getMessage() + "]";
                android.widget.Toast.makeText(context, "程序出现异常." + err, android.widget.Toast.LENGTH_LONG)
                        .show();

                android.os.Looper.loop();
            }

        }.start();

        // 收集设备参数信息 \日志信息
//        String errInfo = collectDeviceInfo(context, ex);
//        // 保存日志文件
//        saveCrashInfo2File(errInfo);
        return true;
    }
}

