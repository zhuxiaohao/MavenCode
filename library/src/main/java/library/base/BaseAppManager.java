/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//                              _oo0oo_
//                             o8888888o
//                             88" . "88
//                             (| -_- |)
//                             0\  =  /0
//                           ___/`---‘\___
//                        .' \\\|     |// '.
//                       / \\\|||  :  |||// \\
//                      / _ ||||| -:- |||||- \\
//                      | |  \\\\  -  /// |   |
//                      | \_|  ''\---/''  |_/ |
//                      \  .-\__  '-'  __/-.  /
//                    ___'. .'  /--.--\  '. .'___
//                 ."" '<  '.___\_<|>_/___.' >'  "".
//                | | : '-  \'.;'\ _ /';.'/ - ' : | |
//                \  \ '_.   \_ __\ /__ _/   .-' /  /
//            ====='-.____'.___ \_____/___.-'____.-'=====
//                              '=---='
//
//
//          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//
//                  佛祖保佑                 永无BUG

package library.base;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
/**
 * Project Name:com.cn.reading
 * File Name: Reading
 * Date:15/8/28下午4:2008
 * blog:http://blog.csdn.net/qq718799510?viewmode=contents
 * Copyright (c) 2015, zhuxiaohao All Rights Reserved.
 * 所有activity 管理
 */
public class BaseAppManager {

    private static final String TAG = BaseAppManager.class.getSimpleName();

    private static BaseAppManager instance = null;
    private static List<Activity> mActivities = new LinkedList<Activity>();

    private BaseAppManager() {

    }

    public synchronized static BaseAppManager getInstance() {
        if (instance == null) {
            instance = new BaseAppManager();
        }
        return instance;
    }

    /**
     *
     * @return 返回activity size
     */
    public int size() {
        return mActivities.size();
    }

    /**
     * 获取当前 activity
     * @return
     */
    public synchronized Activity getForwardActivity() {
        return size() > 0 ? mActivities.get(size() - 1) : null;
    }

    /**
     *  添加 activity
     * @param activity 要添加的 activity
     */
    public synchronized void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    /**
     * 移除 activity
     * @param activity 要移除 activity
     */
    public synchronized void removeActivity(Activity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    /**
     * 清除所有 activity
     */
    public synchronized void clear() {
        for (int i = mActivities.size() - 1; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size();
        }
    }

    /**
     * 清除最上面的 activity
     */
    public synchronized void clearToTop() {
        for (int i = mActivities.size() - 2; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size() - 1;
        }
    }
}
