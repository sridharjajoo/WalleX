package com.example.sridh.wallex;


import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.app.LoaderManager;
import android.content.Loader;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sridh.wallex.data1.MoneyContract;
import com.example.sridh.wallex.data1.MoneyContract.MoneyEntry;
import com.example.sridh.wallex.data1.MoneyDbHelper;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int loaderID=0;
    public static final String LOG_TAG = MainActivity.class.getName() ;
    private MoneyDbHelper moneyDbHelper;
    private MoneyCursorAdapter mCursorAdapter;
    private int CURRENT_BALANCE=0;
    private TextView amount1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this , EditorActivity.class);
                startActivity(intent);


            }
        });

        moneyDbHelper= new MoneyDbHelper(this);

        ListView list = (ListView) findViewById(R.id.list_view);
        mCursorAdapter = new MoneyCursorAdapter(this ,null);
        list.setAdapter(mCursorAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this , EditorActivity.class);
                Uri moneyUri = ContentUris.withAppendedId(MoneyEntry.CONTENT_URI , id);
                intent.setData(moneyUri);
                startActivity(intent);
                 }
        });


        getLoaderManager().initLoader(loaderID , null ,this);
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {

        String[] projection = {MoneyEntry._ID ,MoneyEntry.COLUMN_EVENT_NAME  ,MoneyEntry.COLUMN_CREDIT_AMOUNT , MoneyEntry.COLUMN_DEBIT_AMOUNT };

        Log.i(LOG_TAG ,"Inside onCreateLoader in MainActivity");
        return new CursorLoader(this ,MoneyEntry.CONTENT_URI ,projection ,null ,null ,null);  //Observer of the content uri
                                                                                           //content://com.example.sridh.money/paisa

    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {

        Log.i(LOG_TAG, "Inside onLoadFinished()--> Main Activity");

        mCursorAdapter.swapCursor(cursor);

    }
    @Override
    public void onLoaderReset(Loader loader)
    {

        Log.i(LOG_TAG , "Inside onLoadFinished()--> Main Activity");
        mCursorAdapter.swapCursor(null);
    }
}
