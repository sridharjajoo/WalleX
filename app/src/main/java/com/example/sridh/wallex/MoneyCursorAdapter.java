package com.example.sridh.wallex;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.sridh.wallex.data1.MoneyContract;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

/**
 * Created by sridh on 30-10-2016.
 */
public class MoneyCursorAdapter extends CursorAdapter {

    private  int CURRENT_BALANCE =0; ;
    private TextView money,header,expenditure1;
    private int flag;
    public static final String LOG_TAG = MoneyCursorAdapter.class.getName() ;



    public MoneyCursorAdapter (Context context , Cursor c){
        super(context,c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {


        Log.i(LOG_TAG ,"Inside newView of CUrsorAdapter");
//
    //    int credit = cursor.getInt(cursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_CREDIT_AMOUNT));
  //      int debit = cursor.getInt(cursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_DEBIT_AMOUNT));
      //  String event = cursor.getString(cursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_EVENT_NAME));
//        CURRENT_BALANCE = CURRENT_BALANCE + credit - debit;
//        flag=1;

        return LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false);
    }


    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }



    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        //int d=1;
        Log.i(LOG_TAG , "Inside bindView");
        money = (TextView) view.findViewById(R.id.money);
        header = (TextView) view.findViewById(R.id.Header);
        expenditure1 = (TextView) view.findViewById(R.id.balance);


        int credit = cursor.getInt(cursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_CREDIT_AMOUNT));
        int debit = cursor.getInt(cursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_DEBIT_AMOUNT));
        String event = cursor.getString(cursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_EVENT_NAME));
        //CURRENT_BALANCE = CURRENT_BALANCE + credit - debit;

       // if(flag==1) {
        //    expenditure1.setText(formatMagnitude(CURRENT_BALANCE));
        //    flag=0;
       // }

        if(credit!=0){

            String credit1 =formatMagnitude(credit);
            money.setText(credit1);


            GradientDrawable moneycircle = (GradientDrawable) money.getBackground();
            moneycircle.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
            header.setText(event);

        }

        else{


            String credit2 = formatMagnitude(debit );
            money.setText(credit2);

            GradientDrawable moneycircle = (GradientDrawable) money.getBackground();
            moneycircle.setColor(ContextCompat.getColor(context, R.color.magnitude9));
            header.setText(event);


        }

   }

}

