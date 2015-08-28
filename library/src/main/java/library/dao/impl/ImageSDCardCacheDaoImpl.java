package library.dao.impl;


/**
 * 
 * ClassName: ImageSDCardCacheDaoImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:16:34 <br/>
 *ImageSDCardCacheDao  接口实现
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class ImageSDCardCacheDaoImpl implements library.dao.ImageSDCardCacheDao {

    private library.util.SqliteUtils sqliteUtils;

    public ImageSDCardCacheDaoImpl(library.util.SqliteUtils sqliteUtils) {
        this.sqliteUtils = sqliteUtils;
    }

    @Override
    public boolean putIntoImageSDCardCache(library.service.impl.ImageSDCardCache imageSDCardCache, String tag) {
        if (imageSDCardCache == null || library.util.StringUtils.isEmpty(tag)) {
            return false;
        }

        StringBuilder selection = new StringBuilder();
        selection.append(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_TAG).append("=?");
        String[] selectionArgs = { tag };
        android.database.Cursor cursor = sqliteUtils.getDb().query(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_TABLE_NAME, null, selection.toString(), selectionArgs, null, null, null);
        if (cursor == null) {
            return true;
        }

        if (cursor != null && cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                library.entity.CacheObject<String> value = new library.entity.CacheObject<String>();
                String imageUrl = cursor.getString(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_URL_INDEX);
                value.setData(cursor.getString(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_PATH_INDEX));
                value.setUsedCount(cursor.getInt(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_USED_COUNT_INDEX));
                value.setPriority(cursor.getInt(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_PRIORITY_INDEX));
                value.setExpired(cursor.getInt(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_IS_EXPIRED_INDEX) == 1);
                value.setForever(cursor.getInt(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_IS_FOREVER_INDEX) == 1);
                imageSDCardCache.put(imageUrl, value);
            }
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return true;
    }

    @Override
    public boolean deleteAndInsertImageSDCardCache(library.service.impl.ImageSDCardCache imageSDCardCache, String tag) {
        if (imageSDCardCache == null || library.util.StringUtils.isEmpty(tag)) {
            return false;
        }

        android.database.sqlite.SQLiteDatabase db = sqliteUtils.getDb();
        db.beginTransaction();
        try {
            StringBuilder whereClause = new StringBuilder();
            whereClause.append(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_TAG).append("=?");
            String[] whereArgs = { tag };
            db.delete(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_TABLE_NAME, whereClause.toString(), whereArgs);

            String key;
            library.entity.CacheObject<String> value;
            for (java.util.Map.Entry<String, library.entity.CacheObject<String>> entry : imageSDCardCache.entrySet()) {
                if (entry != null && (key = entry.getKey()) != null && (value = entry.getValue()) != null) {
                    db.insert(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_TABLE_NAME, null, cacheObjectToCV(tag, key, value));
                }
            }

            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            db.endTransaction();
        }
    }

    /**
     * @param tag
     * @param url
     * @param value
     * @return
     */
    private static android.content.ContentValues cacheObjectToCV(String tag, String url, library.entity.CacheObject<String> value) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_TAG, tag);
        values.put(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_URL, url);
        values.put(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_PATH, value.getData());
        values.put(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_ENTER_TIME, value.getEnterTime());
        values.put(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_LAST_USED_TIME, value.getLastUsedTime());
        values.put(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_USED_COUNT, value.getUsedCount());
        values.put(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_PRIORITY, value.getPriority());
        values.put(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_IS_EXPIRED, value.isExpired() ? 1 : 0);
        values.put(library.controller.DbConstants.IMAGE_SDCARD_CACHE_TABLE_IS_FOREVER, value.isForever() ? 1 : 0);
        return values;
    }
}
