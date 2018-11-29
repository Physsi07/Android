package nsft.inclassasynctaskloader72;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String>{

    public static final String TAG = "MainActivity";
    public final int ID_1 = 1;
    public final int ID_2 = 2;
    private TextView textView1, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportLoaderManagers and initLoaders for each ID
        if(getSupportLoaderManager().getLoader(ID_1) != null){
            getSupportLoaderManager().initLoader(ID_1,null,this);
        }
        if(getSupportLoaderManager().getLoader(ID_2) != null){
            getSupportLoaderManager().initLoader(ID_2,null, this);
        }
        //get references to widgets
        textView1 = (TextView)findViewById(R.id.textView_Task1_status);
        textView2 = (TextView)findViewById(R.id.textView_Task2_status);

    }

    public void startTask1(View view) {
        Log.d(TAG, "task1 start button clicked");
        textView1.setText("Running");
        Bundle bundle = new Bundle();
        bundle.putString("TASK_ID", "TASK ID" +ID_1);
        getSupportLoaderManager().restartLoader(ID_1,bundle,this);
    }

    public void startTask2(View view) {
        Log.d(TAG, "task2 start button clicked");
        textView2.setText("Running");
        Bundle bundle = new Bundle();
        bundle.putString("TASK_ID", "TASK ID" +ID_2);
        getSupportLoaderManager().restartLoader(ID_2,bundle,this);
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new MyAsyncTaskLoader(this, bundle.getString("TASK_ID"));//return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        Log.d(TAG, "GOT:"+s);
        if(s.startsWith("TASK ID"+ID_1)){
            textView1.setText(s);
        }
        else if(s.startsWith("TASK ID"+ID_2)){
            textView2.setText(s);
        }
        else{
            Log.d(TAG,"Dunno what happened...");
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}