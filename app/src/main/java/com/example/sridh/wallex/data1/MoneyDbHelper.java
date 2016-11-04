package com.example.sridh.wallex.data1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sridh.wallex.MainActivity;
import com.example.sridh.wallex.data1.MoneyContract;

/**
 * Created by sridh on 30-10-2016.
 */
public class MoneyDbHelper extends SQLiteOpenHelper
{
   public static final String DATABASE_NAME = "paisa.db";
    private static final int  DATABASE_VERSION = 1;
    public static final String LOG_TAG = MoneyDbHelper.class.getName() ;
    public MoneyDbHelper(Context context)
    {
           super(context , DATABASE_NAME,null ,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.i(LOG_TAG , "Inside onCreate of SQLITEdatabse");

        String CREATE_PAISA_TABLE = "CREATE TABLE " + MoneyContract.MoneyEntry.TABLE_NAME + " ( "
                + MoneyContract.MoneyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoneyContract.MoneyEntry.COLUMN_EVENT_NAME + " TEXT NOT NULL, " +
                MoneyContract.MoneyEntry.COLUMN_CREDIT_AMOUNT + " INTEGER DEFAULT 0," +
                MoneyContract.MoneyEntry.COLUMN_DEBIT_AMOUNT  + " INTEGER DEFAULT 0" + ")";

        sqLiteDatabase.execSQL(CREATE_PAISA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
