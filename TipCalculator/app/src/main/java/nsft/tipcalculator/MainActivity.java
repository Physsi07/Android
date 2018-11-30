package nsft.tipcalculator;

import android.widget.*;
import android.util.Log;
import android.os.Bundle;
import java.text.NumberFormat;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    // Declaring XML's Variables
    public TextView tip_Percentage;
    public TextView total_Display;
    public EditText bill_Amount;
    public TextView tip_Display;
    public SeekBar  seekBar;

    // Set the number formats to be used for the $ amounts , and % amounts
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    // Declare global variables for the calculations
    String total_BillAmount = " ";
    double total_Amount;
    double tip_Amount;
    double percentage;

    //Function That appears before apps starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Calling EditText and TextViews that we are going to use
        bill_Amount    = findViewById(R.id.bill_Amount);
        tip_Percentage = findViewById(R.id.tip_Percentage);
        tip_Display    = findViewById(R.id.tip_Dislpay);
        total_Display  = findViewById(R.id.total_Display);
        seekBar        = findViewById(R.id.seekBar);

        // Calling the listener for the seekbar
        seekBar.setOnSeekBarChangeListener(this);

        // Starting tip percentage  at 0%
        tip_Percentage.setText("0.0%");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {



        seekBar.setProgress(progress);
        tip_Percentage.setText(progress + "%");

        // Taking the progresss of the percentage
        percentage = progress;

        // Getting the bill amount into a String
        total_BillAmount = (bill_Amount.getText().toString());
        Log.d("MainActivity", "This is the bill amount: " + total_BillAmount);

        // Setting the percentage number as a text
        tip_Percentage.setText(Double.toString(percentage)+ "%");

        // format percent and display in percentTextView
        tip_Display.setText(percentFormat.format(percentage));

        // Calculates the tip amount
        tip_Amount = Double.parseDouble(total_BillAmount) * (percentage / 100);
        Log.d("MainActivity", "This is the tip amount: " + tip_Amount);

        // display tip and total formatted as currency
        // user currencyFormat instead of percentFormat to set the textViewTip
        tip_Display.setText(currencyFormat.format(tip_Amount));

        // Converting the total amount from from string to double and adding it to the tip
        total_Amount = Double.parseDouble(total_BillAmount) + tip_Amount;

        // Displaying the total amount as a text
        total_Display.setText(currencyFormat.format(total_Amount));
        Log.d("MainActivity", "This is the total amount: " + total_Display);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }

}
