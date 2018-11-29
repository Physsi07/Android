package nsft.inclassasynctaskloader72;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.Random;

/**
 * Created by esofianos1 on 3/26/18.
 */

public class MyAsyncTaskLoader extends AsyncTaskLoader {

    public static final String TAG = "MyAsyncTaskLoader";
    private String taskText;
    private String taskID;

    public MyAsyncTaskLoader(Context context, String text) {
        super(context);
        taskID = text;
        taskText = taskID;

        Log.d(TAG, "ID"+ taskText);
        Log.d(TAG, "TEXT"+ taskText);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();//make sure we load the data regardless
    }

    @Override
    public Object loadInBackground() {
        //we'll generate some numbers and count up to them
        Random rand = new Random();
        int x = rand.nextInt(100);
        for(int i=0; i<=x; i++){
            try{
                taskText = taskID +" num "+x;//assign val
                Thread.sleep(250);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Log.d(TAG, taskText + " i="+i);
        }
        Log.d(TAG, "WILL RETURN NOW:"+taskText);
        return taskText + " DONE";
    }
}