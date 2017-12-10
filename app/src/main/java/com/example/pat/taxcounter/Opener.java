package com.example.pat.taxcounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import static java.security.AccessController.getContext;

public class Opener extends AppCompatActivity {
    public final class FeedReaderContract {
        // To prevent someone from accidentally instantiating the contract class,
        // make the constructor private.
        private FeedReaderContract() {
        }

        /* Inner class that defines the table contents */
        public class FeedEntry implements BaseColumns {
            public static final String TABLE_NAME = "entry";
            public static final String COLUMN_NAME_TITLE = "title";
            public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        }

        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                     FeedEntry._ID + " INTEGER PRIMARY KEY," +
                      FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                       FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
        public class FeedReaderDbHelper extends SQLiteOpenHelper {
            // If you change the database schema, you must increment the database version.
            public static final int DATABASE_VERSION = 1;
            public static final String DATABASE_NAME = "FeedReader.db";

            public FeedReaderDbHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(SQL_CREATE_ENTRIES);
            }
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // This database is only a cache for online data, so its upgrade policy is
                // to simply to discard the data and start over
                db.execSQL(SQL_DELETE_ENTRIES);
                onCreate(db);
            }
            public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                onUpgrade(db, oldVersion, newVersion);

            }
            FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);
        }
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opener);
        Handler handler = new Handler();


      /*  handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.opener);
            }
        }, 10000);

        EditText login = (EditText) findViewById(R.id.login);
        EditText haslo = (EditText) findViewById(R.id.haslo);
        TextView tit = (TextView) findViewById(R.id.title);
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);
        DBHandler dbHandler = new DBHandler(this);
        dbHandler.dodajLogo(login.getText().toString(), haslo.getText().toString());
        Cursor cursor = dbHandler.dajWszystkie();
        while(cursor.moveToNext()) {
            int nr = cursor.getInt(0);
            String loginn = cursor.getString(1);
            String hasloo = cursor.getString(2);
            tit.setText(tit.getText() + "\n" + loginn + " " + hasloo);
        }*/
    }
}
