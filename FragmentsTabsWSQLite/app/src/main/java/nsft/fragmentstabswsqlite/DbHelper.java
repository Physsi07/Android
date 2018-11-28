package nsft.fragmentstabswsqlite;


import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    // STATIC VARIABLES //
    private static String DB_Name  = "EDMTDev";
    private static int    DB_Ver   = 1;
    private static String DB_Table = "Task";
    private static String DB_Col   = "TaskName";

    public DbHelper(Context context) {
        super(context, DB_Name, null, DB_Ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // SQL QUERY //
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL)", DB_Table, DB_Col);
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //
        String query = String.format("DELETE TABLE IF EXIST %s", DB_Table);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertNextTask(String task) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // PUTTING THE DATA INTO ITS PERSPECTIVE COLUMN //
        values.put(DB_Col, task);

        //
        db.insertWithOnConflict(DB_Table, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        // CLOSING THE DATABASE //
        db.close();
    }

    public void deleteTask(String task) {

        //  //
        SQLiteDatabase db = this.getWritableDatabase();

        // DELETING THE TASK FROM THE DATABASE AND FROM THE TABLE AND COLUMN //
        db.delete(DB_Table, DB_Col + "- ?", new String[]{task});

        // CLOSING THE DATABASE //
        db.close();
    }

    public ArrayList<String> getTaskList() {

        // NEW OBJECTS
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //
        Cursor cursor = db.query(DB_Table, new String[]{DB_Col}, null, null, null, null, null);

        //
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_Col);
            taskList.add(cursor.getString(index));
        }

        // CLOSING THE CURSOR AND THE DATABASE //
        cursor.close();
        db.close();

        // RETURNING THE TASKLIST //
        return taskList;
    }
}
