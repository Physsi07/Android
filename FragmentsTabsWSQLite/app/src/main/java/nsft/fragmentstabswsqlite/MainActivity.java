package nsft.fragmentstabswsqlite;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.support.v7.app.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;


public class MainActivity extends AppCompatActivity {

    // OBJECTS FROM OTHER CLASSES //
    DbHelper dbHelper;

    // ARRAYADAPTER //
    ArrayAdapter<String> mAdapter;

    // LISTVIEW VARIABLE //
    ListView task;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // CALLING THE DBHELPER WHICH IS MY SQLITE DATABASE //
        dbHelper = new DbHelper(this);

        // INITIALIZING THE LISTVIEW VARIBLE BY CONNECTING IT WITH ITS XML ID //
        task = findViewById(R.id.firstTask);

        // CALLING LOADTASKLIST WHICH ... //
        loadTaskList();

    }

    private void loadTaskList() {
        // CREATING AN ARRAY LIST OF TASK OR LIST ITEMS AND STORING IT INTO THE LIST OF THE DATABASE //
        ArrayList<String> taskList = dbHelper.getTaskList();

        // CHECKING IF THE ADAPTER IS NULL //
        if (mAdapter == null) {
            // IF IT IS NULL, SET A NEW ADAPTER WITH NEW VALUES //
            mAdapter = new ArrayAdapter<String>(this, R.layout.row, R.id.taskTitle, taskList);

            // SETTING THE ADAPTER //
            task.setAdapter(mAdapter);
        }
        // OR WE JUST WILL CLEAR THE ADAPTER AND ...  //
        else{
            // CLEAR IT //
            mAdapter.clear();
            mAdapter.addAll(taskList);

            // CHECK FOR CHANGES //
            // Notifies the attached observers that the underlying data has been changed and any View reflecting the data set should refresh itself. //
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // INFLATING THE MENU TO BE ABLE TO UPDATE THAT LAYOUT //
        getMenuInflater().inflate(R.menu.menu, menu);

        // CHANGING THE COLOR OF THE ICON
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

        // RETURNING ... //
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addTask:

                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add New Title")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // STRING VARIABLE //
                                String task = String.valueOf(taskEditText.getText());

                                // INSERTING INTO THE NEXT ROW THATS EMPTY //
                                dbHelper.insertNextTask(task);
                                loadTaskList();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(View view) {
        View parent = (View)view.getParent();
        TextView taskTextView = findViewById(R.id.taskTitle);
        String task = String.valueOf(taskTextView.getText());

        // DELETING FROM THE DATABASE //
        dbHelper.deleteTask(task);

        // LOADING THE TASK AGAIN //
        loadTaskList();
    }

}
