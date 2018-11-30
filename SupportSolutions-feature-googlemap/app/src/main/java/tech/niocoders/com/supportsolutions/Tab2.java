package tech.niocoders.com.supportsolutions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2 extends Fragment implements View.OnClickListener{

    public Tab2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.finder, container, false);
    }
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

       // mCardInputWidget = (CardMultilineWidget) view.findViewById(R.id.card_multiline_widget);
    }

    @Override
    public void onClick(View view) {

    }


    public interface OnFragmentInteractionListener {

    }
}
