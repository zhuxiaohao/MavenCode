package library.dao.impl;


import java.util.Map;

import library.entity.HttpResponse;

/**
 * 
 * ClassName: HttpCacheDaoImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:15:23 <br/>
 * HttpCacheDaoImpl  接口实现
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class HttpCacheDaoImpl implements library.dao.HttpCacheDao {

    private library.util.SqliteUtils sqliteUtils;

    public HttpCacheDaoImpl(library.util.SqliteUtils sqliteUtils) {
        this.sqliteUtils = sqliteUtils;
    }

    @Override
    public long insertHttpResponse(HttpResponse httpResponse) {
        android.content.ContentValues contentValues = httpResponseToCV(httpResponse);
        if (contentValues == null) {
            return -1;
        }
        synchronized (library.dao.impl.HttpCacheDaoImpl.class) {
            return sqliteUtils.getDb().replace(library.controller.DbConstants.HTTP_CACHE_TABLE_TABLE_NAME, null, contentValues);
        }
    }

    @Override
    public HttpResponse getHttpResponse(String url) {
        if (library.util.StringUtils.isEmpty(url)) {
            return null;
        }

        StringBuilder appWhere = new StringBuilder();
        appWhere.append(library.controller.DbConstants.HTTP_CACHE_TABLE_URL).append("=?");
        String[] appWhereArgs = { url };
        synchronized (library.dao.impl.HttpCacheDaoImpl.class) {
            android.database.Cursor cursor = sqliteUtils.getDb().query(library.controller.DbConstants.HTTP_CACHE_TABLE_TABLE_NAME, null, appWhere.toString(), appWhereArgs, null, null, null);
            if (cursor == null) {
                return null;
            }

            HttpResponse httpResponse = null;
            if (cursor.moveToFirst()) {
                httpResponse = cursorToHttpResponse(cursor, url);
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
            return httpResponse;
        }
    }

    @Override
    public Map<String, HttpResponse> getHttpResponsesByType(int type) {
        StringBuilder whereClause = new StringBuilder();
        whereClause.append(library.controller.DbConstants.HTTP_CACHE_TABLE_TYPE).append("=?");
        String[] whereClauseArgs = { Integer.toString(type) };

        synchronized (library.dao.impl.HttpCacheDaoImpl.class) {
            android.database.Cursor cursor = sqliteUtils.getDb().query(library.controller.DbConstants.HTTP_CACHE_TABLE_TABLE_NAME, null, whereClause.toString(), whereClauseArgs, null, null, null);

            if (cursor == null) {
                return null;
            }

            Map<String, HttpResponse> httpResponseMap = new java.util.HashMap<String, HttpResponse>();
            if (cursor.getCount() > 0) {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    String url = cursor.getString(library.controller.DbConstants.HTTP_CACHE_TABLE_URL_INDEX);
                    if (library.util.StringUtils.isEmpty(url)) {
                        continue;
                    }

                    HttpResponse httpResponse = cursorToHttpResponse(cursor, url);
                    if (httpResponse != null) {
                        httpResponseMap.put(url, httpResponse);
                    }
                }
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
            return httpResponseMap;
        }
    }

    @Override
    public int deleteAllHttpResponse() {
        return sqliteUtils.getDb().delete(library.controller.DbConstants.HTTP_CACHE_TABLE_TYPE, null, null);
    }

    /**
     * convert cursor to HttpResponse
     * 
     * @param cursor
     * @param url
     * @return
     */
    private HttpResponse cursorToHttpResponse(android.database.Cursor cursor, String url) {
        if (cursor == null) {
            return null;
        }
        if (url == null) {
            url = cursor.getString(library.controller.DbConstants.HTTP_CACHE_TABLE_URL_INDEX);
        }
        if (library.util.StringUtils.isEmpty(url)) {
            return null;
        }

        HttpResponse httpResponse = new HttpResponse(url);
        httpResponse.setResponseBody(cursor.getString(library.controller.DbConstants.HTTP_CACHE_TABLE_RESPONSE_INDEX));
        httpResponse.setExpiredTime(cursor.getLong(library.controller.DbConstants.HTTP_CACHE_TABLE_EXPIRES_INDEX));
        httpResponse.setType(cursor.getInt(library.controller.DbConstants.HTTP_CACHE_TABLE_TYPE_INDEX));
        return httpResponse;
    }

    /**
     * convert HttpResponse to ContentValues
     * 
     * @param httpResponse
     * @return
     */
    private static android.content.ContentValues httpResponseToCV(HttpResponse httpResponse) {
        if (httpResponse == null || library.util.StringUtils.isEmpty(httpResponse.getUrl())) {
            return null;
        }

        android.content.ContentValues values = new android.content.ContentValues();
        values.put(library.controller.DbConstants.HTTP_CACHE_TABLE_URL, httpResponse.getUrl());
        values.put(library.controller.DbConstants.HTTP_CACHE_TABLE_RESPONSE, httpResponse.getResponseBody());
        values.put(library.controller.DbConstants.HTTP_CACHE_TABLE_EXPIRES, httpResponse.getExpiredTime());
        values.put(library.controller.DbConstants.HTTP_CACHE_TABLE_CREATE_TIME, library.util.TimeUtils.getCurrentTimeInString());
        values.put(library.controller.DbConstants.HTTP_CACHE_TABLE_TYPE, httpResponse.getType());
        return values;
    }
}
