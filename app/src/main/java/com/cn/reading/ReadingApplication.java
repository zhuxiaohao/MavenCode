package com.cn.reading;

import android.app.Application;

/**
 * Project Name:com.cn.reading
 * File Name: Reading
 * Date:15/8/28下午4:2008
 * blog:http://blog.csdn.net/qq718799510?viewmode=contents
 * Copyright (c) 2015, zhuxiaohao All Rights Reserved.
 */
public class ReadingApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void exitApp(){

    }
    public void onLowMemory(){
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onLowMemory();
    }
}
