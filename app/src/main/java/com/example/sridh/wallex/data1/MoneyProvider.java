package com.example.sridh.wallex.data1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by sridh on 30-10-2016.
 */
public class MoneyProvider extends ContentProvider {



    private static final int MONEY = 100;
    private static final int MONEY_ID = 101;

    public static final String LOG_TAG = MoneyProvider.class.getSimpleName();

    private MoneyDbHelper moneyDbHelper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static
    {

        Log.i(LOG_TAG ,"In static block of MoneyProvider");
        sUriMatcher.addURI(MoneyContract.CONTENT_AUTHORITY ,MoneyContract.PATH ,MONEY );
        sUriMatcher.addURI(MoneyContract.CONTENT_AUTHORITY,MoneyContract.PATH + "/#" , MONEY_ID);

    }

    @Override
    public boolean onCreate()
    {
        Log.i(LOG_TAG ,"Inside Oncreate block of MoneyProvider");
        moneyDbHelper = new MoneyDbHelper(getContext());
        return true;

    }

    //@Nullable
    @Override
    public Cursor query(Uri uri, String[] projection , String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = moneyDbHelper.getReadableDatabase();

        Log.i(LOG_TAG,"Inside MoneyProvider query() called");
        // This cursor will hold the result of the query
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch(match){

            case MONEY:
                cursor = database.query(MoneyContract.MoneyEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case MONEY_ID:

                Log.i(LOG_TAG , "Inside MONEY_ID");

                selection = MoneyContract.MoneyEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(MoneyContract.MoneyEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);  //Sends the message that databse has changed
        return cursor;
    }

    //@Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        Log.i(LOG_TAG ,"In Insert block of MoneyProvider");
        final int match=sUriMatcher.match(uri);
        switch(match){
            case MONEY:
                return insertMoney(uri ,contentValues);

            default:
                throw new IllegalArgumentException("Insertion isnt supported for the URI" + uri);
        }

    }

    private Uri insertMoney(Uri uri ,ContentValues contentValues){

        //Log.i(LOG_TAG,"insert() --> insertMoney()");


        String event_name = contentValues.getAsString(MoneyContract.MoneyEntry.COLUMN_EVENT_NAME);
        if(event_name==null){
            throw new IllegalArgumentException("Event requires a title");
        }

        int credit = contentValues.getAsInteger(MoneyContract.MoneyEntry.COLUMN_CREDIT_AMOUNT);
        int debit = contentValues.getAsInteger(MoneyContract.MoneyEntry.COLUMN_DEBIT_AMOUNT);
        if(credit==0 && debit==0){
            throw new IllegalArgumentException("Event requires a some cash to be entered");

        }

        SQLiteDatabase database = moneyDbHelper.getWritableDatabase();
        long id = database.insert(MoneyContract.MoneyEntry.TABLE_NAME , null ,contentValues);

        getContext().getContentResolver().notifyChange(uri , null);
        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {

        Log.i(LOG_TAG,"update() --> insertMoney()");
        final int match = sUriMatcher.match(uri);
        switch (match) {

            case MONEY:

                return updateMoney(uri, contentValues, selection, selectionArgs);

            case MONEY_ID:
                selection = MoneyContract.MoneyEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                return updateMoney(uri, contentValues, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }


    private int updateMoney(Uri uri, ContentValues contentValues , String selection , String[] selectionargs){


        SQLiteDatabase database = moneyDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(MoneyContract.MoneyEntry.TABLE_NAME, contentValues, selection, selectionargs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Returns the number of database rows affected by the update statement

        return rowsUpdated;
    }


}
