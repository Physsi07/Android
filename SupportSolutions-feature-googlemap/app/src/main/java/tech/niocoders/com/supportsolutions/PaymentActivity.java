package tech.niocoders.com.supportsolutions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stripe.android.model.Card;
import com.stripe.android.view.CardMultilineWidget;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener{

    private Button save;
    private CardMultilineWidget mCardInputWidget;
    private EditText myEditText;
    private Calendar calendar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_window);
        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(this);
        myEditText = (EditText)findViewById(R.id.calendarDate);
        myEditText.setOnClickListener(this);
        mCardInputWidget = (CardMultilineWidget) findViewById(R.id.card_multiline_widget);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.save:
                Card cardToSave = mCardInputWidget.getCard();
                if(cardToSave==null)
                {
                    Toast.makeText(getApplicationContext(),"Empty data",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getApplicationContext(),cardToSave.getNumber(),Toast.LENGTH_LONG).show();
                    if(cardToSave.validateCard()&&cardToSave.validateCVC()){
                        /*
                        * We will encrypt the data and save it to an especific server
                        * */
                    }

                }
                break;
            case R.id.calendarDate:
                Context context = PaymentActivity.this;
                Class child = Calendar.class;
                Intent calendar = new Intent(context,child);
                startActivity(calendar);

                break;
        }
    }
}
