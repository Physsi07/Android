package com.example.twins.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by TWINS on 3/24/2018.
 */

///Use an AsyncTask to make network connections:

public class FetchBook extends AsyncTask <String,Void,String> {

/////TextViews that show the results, and initialize them
/// in a constructor. You will use this constructor in your
/// MainActivity to pass along the TextViews to your AsyncTask.

    private TextView mTitleText;
    private TextView mAuthorText;




    public FetchBook(TextView mTitleText, TextView mAuthorText) {
        this.mTitleText = mTitleText;
        this.mAuthorText = mAuthorText;
    }

    /////Method for handling the results on the UI thread
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute( s );

        try {
            JSONObject jsonObject = new JSONObject( s );
            JSONArray itemsArray = jsonObject.getJSONArray( "items" );
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject book = itemsArray.getJSONObject( i );
                String title = null;
                String authors = null;
                JSONObject volumeInfo = book.getJSONObject( "volumeInfo" );


                try {
                    title = volumeInfo.getString( "title" );
                    authors = volumeInfo.getString( "authors" );
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (title != null && authors != null) {
                    mTitleText.setText( title );
                    mAuthorText.setText( authors );
                    return;
                }
            }


            mTitleText.setText( "No Results Found" );
            mAuthorText.setText( "" );


        } catch (Exception e) {
            mTitleText.setText( "No Results Found" );
            mAuthorText.setText( "" );
            e.printStackTrace();


        }


    }



    @Override
    protected String doInBackground(String... params) {


        ////calling the information from getBookInfo method
        //from the NetworkUnits class
        return NetworkUtils.getBookInfo(params[0]);
    }
}
