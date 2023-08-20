package co.edu.uniempresarial.bdsqllite.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ManageDB extends SQLiteOpenHelper {

    private static final String DATA_BASE_USERS = "dbUsers";
    private static final int VERSION = 2;
    private static final String TABLE_USERS = "users";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_USERS + "(" +
                    "use_document INTEGER PRIMARY KEY," +
                    "use_user TEXT NOT NULL," +
                    "use_names TEXT NOT NULL," +
                    "use_lastnames TEXT NOT NULL," +
                    "use_pass TEXT NOT NULL," +
                    "use_status INTEGER NOT NULL);";

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_USERS;

    public ManageDB(Context context) {
        super(context, DATA_BASE_USERS, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.i("DATABASE", "The table was created successfully " + CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL(DELETE_TABLE);
            onCreate(db);
        }
    }
}