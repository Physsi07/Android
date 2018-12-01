package com.example.twins.whowroteit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    // Variables for the search input field, and results TextViews
    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;

   // private String mQueryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        // initialize all the view variables\
        mBookInput = (EditText)findViewById(R.id.bookInput );

        mTitleText= (TextView) findViewById(R.id.titleText);

        mAuthorText = (TextView) findViewById(R.id.authorText);
    }

    public void searchBooks(View view) {

        /// get the text from the EditText widget and convert
        /// to a String, assigning it to a string variable.

        String queryString = mBookInput.getText().toString();

        // to hide the keyboard when the button is pressed:

        InputMethodManager inputManager = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE );

        inputManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS );

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE );

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {

            /// to launch the AsyncTask:
            new FetchBook( mTitleText, mAuthorText ).execute( queryString );
            mAuthorText.setText( "" );
            mTitleText.setText( R.string.loading );

        } else {

        }
            if (queryString.length() == 0) {
                mAuthorText.setText( "" );
                mTitleText.setText( "Please enter a search term" );

            } else {
                mAuthorText.setText( "" );
                mTitleText.setText( "Please check your network connection and try again." );
            }
        }

    }