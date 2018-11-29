package nsft.listview_addingdeleting;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // VARIABLES FROM OUR XML//
    private EditText itemText;
    private Button button;
    private ListView itemList;

    // ARRAY VARIABLES //
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;

    // STRING VARIABLE //
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // LINKING VARIABLES TO THEIR PRESPECTIVE ID //
        itemList = (ListView) findViewById(R.id.listView);
        itemText = (EditText) findViewById(R.id.addText);
        button = (Button) findViewById(R.id.addButton);

        // INITIALIZING THE ARRAY LIST //
        list = new ArrayList<>();

        //  LOADING THE LIST //
//        loadList(getApplicationContext());

        // SETTING THE ADAPTER //
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);

        // SETTING THE BUTTON TO BE ABLE TO ADD TO THE LIST //
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // CONVERTTING THE EDIT TEXT TO A STRING //
                text = itemText.getText().toString();

                // ADD AN ITEM TO THE LIST //
                list.add(text);

                // SAVING THE LIST //
                saveList();

                // SETTING THE TEXT OF THE ITEM //
                itemText.setText("");

                // ADAPTER NOTIFY //
                adapter.notifyDataSetChanged();

                // SHOWING A TOAST TO THE USER //
                Toast.makeText(MainActivity.this, "item added", Toast.LENGTH_SHORT).show();
            }

        });

        // SETTING THE LIST WHEN IS HOLD TO BE DELETED //
        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // DELETE THE ITEM //
                list.remove(position);

                // SAVE THE LIST //
                saveList();

                adapter.notifyDataSetChanged();

                // SHOWING A TOAST TO THE USER //
                Toast.makeText(MainActivity.this, "item deleted", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        itemList.setAdapter(adapter);

    }


    // SAVING THE LIST //
    private void saveList() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("size", String.valueOf(list.size()));

        for (int i = 0; i < list.size(); i++) {
            editor.remove("text" + i);
            editor.putString("text" + i, list.get(i));
        }

        editor.commit();
    }
//
//    private void loadList(Context context) {
//        SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(context);
//        list.clear();
//        int size = sharedPreferences2.getInt("size", 0);
//        for(int i = 0; i < size; i++) {
//            list.add(sharedPreferences2.getString("text" + 1, null));
//        }
//
//    }
}
