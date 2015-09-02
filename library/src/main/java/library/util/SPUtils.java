/**
 * Project Name:android-common-tools
 * File Name:SPUtils.java
 * Package Name:com.android.common.util
 * Date:2015年3月2日上午10:29:54
 * Copyright (c) 2015, zhuxiaohao All Rights Reserved.
 *
 */

package library.util;

/**
 * ClassName:SPUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年3月2日 上午10:29:54 <br/>
 * @author   chenhao
 * @version  
 * @since    JDK 1.6
 * @see 	 SharedPreferences封装类SPUtils
 */
public class SPUtils {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * 
     * @param context
     * @param key
     * @param object
     */
    public static void put(android.content.Context context, String key, Object object) {

        android.content.SharedPreferences sp = context.getSharedPreferences(FILE_NAME, android.content.Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        library.util.SPUtils.SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * 
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(android.content.Context context, String key, Object defaultObject) {
        android.content.SharedPreferences sp = context.getSharedPreferences(FILE_NAME, android.content.Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     * 
     * @param context
     * @param key
     */
    public static void remove(android.content.Context context, String key) {
        android.content.SharedPreferences sp = context.getSharedPreferences(FILE_NAME, android.content.Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        library.util.SPUtils.SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     * 
     * @param context
     */
    public static void clear(android.content.Context context) {
        android.content.SharedPreferences sp = context.getSharedPreferences(FILE_NAME, android.content.Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        library.util.SPUtils.SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     * 
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(android.content.Context context, String key) {
        android.content.SharedPreferences sp = context.getSharedPreferences(FILE_NAME, android.content.Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     * 
     * @param context
     * @return
     */
    public static java.util.Map<String, ?> getAll(android.content.Context context) {
        android.content.SharedPreferences sp = context.getSharedPreferences(FILE_NAME, android.content.Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     * 
     * @author zhy
     * 
     */
    private static class SharedPreferencesCompat {
        private static final java.lang.reflect.Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         * 
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static java.lang.reflect.Method findApplyMethod() {
            try {
                Class clz = android.content.SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         * 
         * @param editor
         */
        public static void apply(android.content.SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (java.lang.reflect.InvocationTargetException e) {
            }
            editor.commit();
        }
    }

}
