package nsft.sqlitepractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLInput;

public class SQLHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "worldList";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_WORDLIST = "tblWordList";

    // KEYS IDS
    protected static final String KEY_ID = "_id";
    protected static final String KEY_WORD = "word";
    private static final String [] COLUMNS = {KEY_ID, KEY_WORD};

    // create table
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_WORDLIST + " (" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_WORD + " TEXT );";

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("SQLHelper", "Inside of the constructor of SQLHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("SQLHelper", "Inside of the constructor of ONCREATE");
        sqLiteDatabase.execSQL(CREATE_TABLE);
        fillMyDB(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("SQLHelper", "Inside of the constructor of UPGRADE");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDLIST );
        onCreate(sqLiteDatabase);
    }

    protected void fillMyDB(SQLiteDatabase db){
        String [] words = {"arroz", "carne", "habichuelas", "helado", "jugp", "pan", "gineo"};

        ContentValues values = new ContentValues();
        for(int i = 0; i < words.length; i++){
            values.put(KEY_WORD, words[i]);

            // inserting the value into the dabase
            db.insert(CREATE_TABLE, null, values);
        }
    }


}
