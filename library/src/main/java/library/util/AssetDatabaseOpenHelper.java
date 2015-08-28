package library.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * 
 * ClassName: AssetDatabaseOpenHelper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:35:26 <br/>
 *  数据库打开助手类
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class AssetDatabaseOpenHelper {

    private android.content.Context context;
    private String databaseName;

    public AssetDatabaseOpenHelper(android.content.Context context, String databaseName) {
        this.context = context;
        this.databaseName = databaseName;
    }

    /**
     * Create and/or open a database that will be used for reading and writing.
     * 
     * @return
     * @throws RuntimeException
     *             if cannot copy database from assets
     * @throws SQLiteException
     *             if the database cannot be opened
     */
    public synchronized android.database.sqlite.SQLiteDatabase getWritableDatabase() {
        java.io.File dbFile = context.getDatabasePath(databaseName);
        if (dbFile != null && !dbFile.exists()) {
            try {
                copyDatabase(dbFile);
            } catch (java.io.IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return android.database.sqlite.SQLiteDatabase.openDatabase(dbFile.getPath(), null, android.database.sqlite.SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * Create and/or open a database that will be used for reading only.
     * 
     * @return
     * @throws RuntimeException
     *             if cannot copy database from assets
     * @throws SQLiteException
     *             if the database cannot be opened
     */
    public synchronized android.database.sqlite.SQLiteDatabase getReadableDatabase() {
        java.io.File dbFile = context.getDatabasePath(databaseName);
        if (dbFile != null && !dbFile.exists()) {
            try {
                copyDatabase(dbFile);
            } catch (java.io.IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return android.database.sqlite.SQLiteDatabase.openDatabase(dbFile.getPath(), null, android.database.sqlite.SQLiteDatabase.OPEN_READONLY);
    }

    /**
     * @return the database name
     */
    public String getDatabaseName() {
        return databaseName;
    }

    private void copyDatabase(java.io.File dbFile) throws java.io.IOException {
        java.io.InputStream stream = context.getAssets().open(databaseName);
        FileUtils.writeFile(dbFile, stream);
        stream.close();
    }
}
