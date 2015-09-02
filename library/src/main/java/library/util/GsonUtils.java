package library.util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Project Name:library.util
 * File Name: Reading
 * Date:15/9/2上午10:3209
 * blog:http://blog.csdn.net/qq718799510?viewmode=contents
 * Copyright (c) 2015, zhuxiaohao All Rights Reserved.
 */
public class GsonUtils {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtils() {

    }

    /**
     * 将对象转换成json格式
     *
     * @param ts
     *
     * @return
     */
    public static String objectToJson(Object ts) {
        String jsonStr = null;
        if (gson != null) {
            jsonStr = gson.toJson(ts);
        }
        return jsonStr;
    }

    /**
     * 将对象转换成json格式(并自定义日期格式)
     *
     * @param ts
     *
     * @return
     */
    public static String objectToJsonDateSerializer(Object ts, final String dateformat) {
        String jsonStr = null;
        gson = new com.google.gson.GsonBuilder()
                .registerTypeHierarchyAdapter(Date.class,
                        new JsonSerializer<Date>() {
                            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                                SimpleDateFormat format = new SimpleDateFormat(dateformat);
                                return new JsonPrimitive(format.format(src));
                            }
                        }).setDateFormat(dateformat).create();
        if (gson != null) {
            jsonStr = gson.toJson(ts);
        }
        return jsonStr;
    }

    /**
     * 将json格式转换成list对象
     *
     * @param jsonStr
     *
     * @return
     */
    public static List<?> jsonToList(String jsonStr) {
        List<?> objList = null;
        if (gson != null) {
            Type type = new TypeToken<List<?>>() {}.getType();
            objList = gson.fromJson(jsonStr, type);
        }
        return objList;
    }

    /**
     * 将json格式转换成list对象，并准确指定类型
     *
     * @param jsonStr
     * @param type
     *
     * @return
     */
    public static List<?> jsonToList(String jsonStr, Type type) {
        List<?> objList = null;
        if (gson != null) {
            objList = gson.fromJson(jsonStr, type);
        }
        return objList;
    }

    /**
     * 将json格式转换成map对象
     *
     * @param jsonStr
     *
     * @return
     */
    public static Map<?, ?> jsonToMap(String jsonStr) {
        Map<?, ?> objMap = null;
        if (gson != null) {
            Type type = new TypeToken<Map<?, ?>>() {
            }.getType();
            objMap = gson.fromJson(jsonStr, type);
        }
        return objMap;
    }

    /**
     * 将json转换成bean对象
     *
     * @param jsonStr
     *
     * @return
     */
    public static Object jsonToBean(String jsonStr, Class<?> cl) {
        Object obj = null;
        if (gson != null) {
            obj = gson.fromJson(jsonStr, cl);
        }
        return obj;
    }

    /**
     * 将json转换成bean对象
     *
     * @param jsonStr
     * @param cl
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToBeanDateSerializer(String jsonStr, Class<T> cl, final String pattern) {
        Object obj = null;
        gson = new com.google.gson.GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                String dateStr = json.getAsString();
                try {
                    return format.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).setDateFormat(pattern).create();
        if (gson != null) {
            obj = gson.fromJson(jsonStr, cl);
        }
        return (T) obj;
    }

    /**
     * 根据
     *
     * @param jsonStr
     * @param key
     *
     * @return
     */
    public static Object getJsonValue(String jsonStr, String key) {
        Object rulsObj = null;
        Map<?, ?> rulsMap = jsonToMap(jsonStr);
        if (rulsMap != null && rulsMap.size() > 0) {
            rulsObj = rulsMap.get(key);
        }
        return rulsObj;
    }


    /**
     * 解析 list类型
     */
//    public static List<JokeBeanInfo> parse(String str) throws org.json.JSONException {
//        List<JokeBeanInfo> jokeBeanInfo = new java.util.ArrayList<JokeBeanInfo>();
//        org.json.JSONObject jsonObject = new org.json.JSONObject(str);
//        CommonJson commonJson = new CommonJson();
//        commonJson.setError_code(jsonObject.getInt("error_code"));
//        commonJson.setReason(jsonObject.getString("reason"));
//        org.json.JSONObject jsonObject1 = jsonObject.getJSONObject("result");
//        JSONArray jsonArray = jsonObject1.getJSONArray("data");
//        Type listType = new TypeToken<java.util.ArrayList<JokeBeanInfo>>() {}.getType();//强制转换对应的类型
//        jokeBeanInfo = (List<JokeBeanInfo>) GsonTools.jsonToList(jsonArray.toString(), listType);
//        return jokeBeanInfo;
//    }



    /**
     * 解析数据
     *
     * @param str
     *         数据
     *
     * @return
     *
     * @throws JsonIOException
     */
//    public static TestBeanInfo parse(String str) throws org.json.JSONException {
//        TestBeanInfo testBean = new TestBeanInfo();
//        org.json.JSONObject jsonObject = new org.json.JSONObject(str);
//        CommonJson commonJson = new CommonJson();
//        commonJson.setError_code(jsonObject.getInt("error_code"));
//        commonJson.setReason(jsonObject.getString("reason"));
//        org.json.JSONObject jsonObject1 = jsonObject.getJSONObject("result");
//        testBean = (TestBeanInfo) GsonTools.jsonToBean(jsonObject1.toString(), TestBeanInfo.class);
//        return testBean;
//    }
}
