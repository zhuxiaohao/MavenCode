package library.util;

/**
 * 
 * ClassName: SqliteUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:48:04 <br/>
 * SQLite的工具
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class SqliteUtils {

    private static volatile library.util.SqliteUtils instance;

    private DbHelper dbHelper;
    private android.database.sqlite.SQLiteDatabase db;

    private SqliteUtils(android.content.Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static library.util.SqliteUtils getInstance(android.content.Context context) {
        if (instance == null) {
            synchronized (library.util.SqliteUtils.class) {
                if (instance == null) {
                    instance = new library.util.SqliteUtils(context);
                }
            }
        }
        return instance;
    }

    public android.database.sqlite.SQLiteDatabase getDb() {
        return db;
    }
}
