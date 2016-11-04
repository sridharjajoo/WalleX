package com.example.sridh.wallex;

import android.content.ContentValues;
//import android.content.SharedPreferences;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.sridh.wallex.data1.MoneyContract;


public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private EditText event_name , cash;
    private RadioButton credit_radio;
    private RadioButton debit_radio;
    private int type,paisa;
    private Uri currenturi;
    public static final String LOG_TAG= EditorActivity.class.getName() ;
    private int loaderID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Log.i(LOG_TAG , "Inside onCreate of EditorActivity");

        credit_radio = (RadioButton) findViewById(R.id.credit);
        debit_radio = (RadioButton) findViewById(R.id.debit);
        event_name = (EditText) findViewById(R.id.event_name);
        cash = (EditText) findViewById(R.id.cash_amount);

        Intent intent = getIntent();
        currenturi = intent.getData();
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        if(currenturi!=null){

            Log.i(LOG_TAG , "Inside onCreate and currenturi!=null of EditorActivity");

            getLoaderManager().initLoader(loaderID , null ,this);
        }

    }



    public int radiobutton(){
        if(credit_radio.isChecked())return type=1;
        else return type=2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


switch(item.getItemId()){

    case R.id.action_save:
        insertPet();
        finish();
        return true;

    case android.R.id.home:
        NavUtils.navigateUpFromSameTask(EditorActivity.this);
        return true;


}


        return super.onOptionsItemSelected(item);}


    public void insertPet() {

        Log.i(LOG_TAG, "Inside insertPet() of EditorActivity");
        String eventString = event_name.getText().toString().trim();
        String cashString = cash.getText().toString().trim();


        ContentValues values = new ContentValues();
        if (radiobutton() == 1) {

            paisa = Integer.parseInt(cashString);
            values.put(MoneyContract.MoneyEntry.COLUMN_EVENT_NAME, eventString);
            values.put(MoneyContract.MoneyEntry.COLUMN_CREDIT_AMOUNT, paisa);
            values.put(MoneyContract.MoneyEntry.COLUMN_DEBIT_AMOUNT, 0);


        } else if (radiobutton() == 2) {

            paisa = Integer.parseInt(cashString);
            values.put(MoneyContract.MoneyEntry.COLUMN_EVENT_NAME, eventString);
            values.put(MoneyContract.MoneyEntry.COLUMN_DEBIT_AMOUNT, paisa);
            values.put(MoneyContract.MoneyEntry.COLUMN_CREDIT_AMOUNT, 0);

        }


        if (currenturi == null) {

            Uri newUri = getContentResolver().insert(MoneyContract.MoneyEntry.CONTENT_URI, values);
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_money_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_money_successful),
                        Toast.LENGTH_SHORT).show();
            }

        }

        else if (currenturi != null) {

            Log.i(LOG_TAG, "Inside insertPet() update of EditorActivity");

            int rowsaffected = getContentResolver().update(currenturi, values, null, null);
            if (rowsaffected == 0) {

                Toast.makeText(this, getString(R.string.editor_insert_money_failed),
                        Toast.LENGTH_SHORT).show();
            } else if (rowsaffected != 0) {

                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_money_successful),
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {MoneyContract.MoneyEntry._ID , MoneyContract.MoneyEntry.COLUMN_EVENT_NAME  , MoneyContract.MoneyEntry.COLUMN_CREDIT_AMOUNT , MoneyContract.MoneyEntry.COLUMN_DEBIT_AMOUNT };

        Log.i(LOG_TAG ,"Inside onCreateLoader in EditorActivity");
        return new CursorLoader(this , currenturi ,projection ,null ,null ,null);


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        Log.i(LOG_TAG,  "Inside onLoadFinished of Editor Activity");

        if (cursor.moveToFirst()) {
            String nameString = cursor.getString(cursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_EVENT_NAME));
            int creditAmount = cursor.getInt(cursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_CREDIT_AMOUNT));
            int debitAmount = cursor.getInt(cursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_DEBIT_AMOUNT));
            Log.i(LOG_TAG,  "Inside onLoadFinishedof Editor Activity --2");

            event_name.setText(nameString);
            if (creditAmount != 0) {
                cash.setText(Integer.toString(creditAmount));
                credit_radio.setChecked(true);

            } else if (debitAmount != 0) {
                cash.setText(Integer.toString(debitAmount));
                debit_radio.setChecked(true);
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        Log.i(LOG_TAG,"onLoaderfinished-2() called");
        cash.setText("");
        event_name.setText("");


    }
}


