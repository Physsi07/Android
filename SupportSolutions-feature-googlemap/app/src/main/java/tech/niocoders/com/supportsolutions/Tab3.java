package tech.niocoders.com.supportsolutions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.stripe.android.view.CardMultilineWidget;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab3 extends Fragment implements View.OnClickListener {

    public Tab3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.payment_window, container, false);
    }

    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
         Button save;
         CardMultilineWidget mCardInputWidget;
         EditText myEditText;
         Calendar calendar;
        save = (Button)view.findViewById(R.id.save);
        save.setOnClickListener(this);
        myEditText = (EditText)view.findViewById(R.id.calendarDate);
        myEditText.setOnClickListener(this);
        mCardInputWidget = (CardMultilineWidget) view.findViewById(R.id.card_multiline_widget);
    }

    @Override
    public void onClick(View view) {

    }

    public interface OnFragmentInteractionListener {

    }
}
