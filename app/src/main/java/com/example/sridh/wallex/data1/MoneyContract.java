package com.example.sridh.wallex.data1;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sridh on 30-10-2016.
 */
public class MoneyContract {

    public static final String CONTENT_AUTHORITY = "com.example.sridh.wallex";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH = "paisa";

    private MoneyContract() {
    }

    ; //Sp that the object isnt invoked anywhere within the program

    public static class MoneyEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);

        public static final String TABLE_NAME = "paisa";
        public static final String _ID = BaseColumns._ID;
         public static final String COLUMN_EVENT_NAME = "event";
        public static final String COLUMN_CREDIT_AMOUNT = "credit";
        public static final String COLUMN_DEBIT_AMOUNT = "debit";
    }
}