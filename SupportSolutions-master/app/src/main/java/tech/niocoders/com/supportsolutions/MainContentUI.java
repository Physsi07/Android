package tech.niocoders.com.supportsolutions;

/**
 * Created by luism on 4/28/2018.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import database.fb_database;


public class MainContentUI extends AppCompatActivity implements Tab1.OnFragmentInteractionListener,
        Tab2.OnFragmentInteractionListener, Tab3.OnFragmentInteractionListener {

    public FirebaseAuth auth;
    public fb_database mydatabase;
    public String user_id;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_ui);


        auth =  FirebaseAuth.getInstance();
        mydatabase =  new fb_database(this);
        // MY TABS //
        TabLayout tablayout = (TabLayout) findViewById(R.id.tapLayout);

        tablayout.addTab(tablayout.newTab().setText("Events")); /* WHAT YOU PUT IN THE TEXT WILL BE DISPLAY IN THE TAB */
        tablayout.addTab(tablayout.newTab().setText("Finder"));
        tablayout.addTab(tablayout.newTab().setText("Payments"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);


        // MY VIEW PAGER //
        final ViewPager viewPager  = (ViewPager) findViewById(R.id.pager);

        /* OBJECT OF THE PAGE ADAPTER CLASS */
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tablayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public void logOut()
    {
        if(auth.getCurrentUser()!=null)
        {
            Log.d("signout","we just sign out now lets try to refresh layout and allow user to create new account!");
            Toast.makeText(getApplicationContext()," the user "+auth.getCurrentUser().getEmail()+" has log out on our end!",Toast.LENGTH_LONG).show();
            auth.signOut();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
       //if (itemThatWasClickedId ==  ) {
           // makeGithubSearchQuery();
           // return true;
        //}
        return super.onOptionsItemSelected(item);
    }
}