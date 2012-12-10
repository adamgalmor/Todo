
package il.ac.shenkar.mobile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TasksOpenHelper extends SQLiteOpenHelper {
    static final String TABLE_NAME = "Tasks";
    static final String DB_NAME = "Tasks";
    static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "text TEXT," +
//            "timestamp DATETIME" +
            "timestamp INTEGER" +
            ")";
    static final int VERSION = 1;

    public TasksOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
