package in.junaidbabu.logit;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by neo on 24/3/14.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "logit";

    //Main table
    private static final String TABLE_NAME = "logs";
    //Columns
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_TIME = "timestamp";

    private static final String KEY_TEXT = "text";

    private static final String KEY_START = "start";
    private static final String KEY_END = "end";
    private static final String KEY_LATLNG = "latlng";
    private static final String KEY_ISSYNC = "issync";
    private static final String KEY_ENTRY_ID = "entryid";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_TYPE + " TEXT, "+
                KEY_TIME + " TEXT, "+
                KEY_START + " TEXT, "+
                KEY_END + " TEXT, "+
                KEY_LATLNG + " TEXT, "+
                KEY_ISSYNC + " TEXT, "+
                KEY_ENTRY_ID + " TEXT, "+
                KEY_TEXT + " TEXT )";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS logs");

        // create fresh locations table
        this.onCreate(db);
    }

    public void addEntry(dataClass log){
        //Log.d("addBook", location.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, log.getType());
        values.put(KEY_TIME, log.getTimestamp());
        values.put(KEY_START, log.getStart());
        values.put(KEY_END, log.getEnd());
        values.put(KEY_LATLNG, log.getLatlong());
        values.put(KEY_ISSYNC, log.getIssync());
        values.put(KEY_ENTRY_ID, log.getEntryid());
        values.put(KEY_TEXT, log.getText());
        // 3. insert
        db.insert(TABLE_NAME, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

}
