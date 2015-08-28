package library.util;


/**
 * ClassName: DbHelper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午5:52:55 <br/>
 * 数据库帮助类
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class DbHelper extends android.database.sqlite.SQLiteOpenHelper {

    public DbHelper(android.content.Context context) {
        super(context, library.controller.DbConstants.DB_NAME, null, library.controller.DbConstants.DB_VERSION);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(library.controller.DbConstants.CREATE_IMAGE_SDCARD_CACHE_TABLE_SQL.toString());
            db.execSQL(library.controller.DbConstants.CREATE_IMAGE_SDCARD_CACHE_TABLE_INDEX_SQL.toString());

            db.execSQL(library.controller.DbConstants.CREATE_HTTP_CACHE_TABLE_SQL.toString());
            db.execSQL(library.controller.DbConstants.CREATE_HTTP_CACHE_TABLE_INDEX_SQL.toString());
            db.execSQL(library.controller.DbConstants.CREATE_HTTP_CACHE_TABLE_UNIQUE_INDEX.toString());
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
