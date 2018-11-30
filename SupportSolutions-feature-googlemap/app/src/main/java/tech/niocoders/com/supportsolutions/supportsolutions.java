package tech.niocoders.com.supportsolutions;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class supportsolutions extends AppCompatActivity {

    // MY CONTEXT //
    private Context context;

    // VARIABLES //
    private ImageView logo;
    private Button    btn_signup;
    private Button    btn_login;

    // VARIABLES FOR ANIMATION //
    ConstraintLayout mLayout;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supportsolutions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Context appContext = supportsolutions.this;
                Class camera_helper =  ss_camera_helper.class;

                Intent childActivityCameraHelper = new Intent(appContext,camera_helper);
                startActivity(childActivityCameraHelper);

            }
        });

        // INITIALIZING THE CONTENTS //
        logo       = findViewById(R.id.imglogo);
        btn_login  = findViewById(R.id.btnlogin);
        btn_signup = findViewById(R.id.btnsingup);

        // INITIALIZING THE CONTEXT //
        context = getApplicationContext();

        // LOGIN BUTTON FOR WHEN IS CLICKED //
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // OPENING THE ACTIVITY FOR THE LOGIN //
                openActivityLogIn();

                // FOR NOW JUST SHOWING A TOAST //
                Toast.makeText(context, "LOGIN CLICKED", Toast.LENGTH_LONG).show();
            }
        });


        // SIGNUP BUTTON FOR WHEN IS CLICKED //
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // OPENING THE ACTIVITY FOR THE LOGIN //
                openActivitySignUp();

                // FOR NOW JUST SHOWING A TOAST //
                Toast.makeText(context, "SINGUP CLICKED", Toast.LENGTH_LONG).show();
            }
        });
    }

    // METHOD FOR THE LOGIN ACTIVITY //
    public void openActivityLogIn() {
        // CONNECTING WITH AN INTENT TO THE LOGIN CLASS FROM THE MAIN ACTIVITY //
        Intent intent = new Intent(this, login.class);

        // STARTING THE ACTIVITY //
        startActivity(intent);
    }

    // METHOD FOR THE SIGNUP ACTIVITY
    public void openActivitySignUp() {
        // CONNECTING WITH AN INTENT TO THE SIGNUP CLASS FROM THE MAIN ACTIVITY //
        Intent intent = new Intent(this, signup.class);

        // StTARTING THE ACTIVITY //
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_supportsolutions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}



/*package tech.niocoders.com.supportsolutions;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.content.Context;
import android.content.Intent;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.stripe.android.model.Card;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stripe.android.model.Card;


public class supportsolutions extends AppCompatActivity implements OnMapReadyCallback{

      public GeoDataApi mGeoDataClient;
      public PlaceDetectionApi placeDetection;
      public FusedLocationProviderApi provider;
      public boolean mLocationPermissionGranted=false;

    // MY CONTEXT //
    private Context context;

    // VARIABLES //
    private ImageView logo;
    private Button    btn_signup;
    private Button    btn_login;

    // VARIABLES FOR ANIMATION //
    ConstraintLayout mLayout;
    AnimationDrawable animationDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        //setContentView(R.layout.activity_supportsolutions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // mGeoDataClient = Places.GeoDataApi;
       // placeDetection = Places.PlaceDetectionApi;
       // provider = LocationServices.FusedLocationApi;


        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();



                Context context = supportsolutions.this;
                Class child = PaymentActivity.class;
                Intent obj = new Intent(context,child);
                startActivity(obj);


                Context appContext = supportsolutions.this;
                Class camera_helper =  ss_camera_helper.class;

                Intent childActivityCameraHelper = new Intent(appContext,camera_helper);
                startActivity(childActivityCameraHelper);

            }
        });

        // INITIALIZING THE CONTENTS //
        logo       = findViewById(R.id.imglogo);
        btn_login  = findViewById(R.id.btnlogin);
        btn_signup = findViewById(R.id.btnsingup);

        // INITIALIZING THE CONTEXT //
        context = getApplicationContext();

        // LOGIN BUTTON FOR WHEN IS CLICKED //
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // OPENING THE ACTIVITY FOR THE LOGIN //
                openActivityLogIn();

                // FOR NOW JUST SHOWING A TOAST //
                Toast.makeText(context, "LOGIN CLICKED", Toast.LENGTH_LONG).show();
            }
        });


        // SIGNUP BUTTON FOR WHEN IS CLICKED //
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // OPENING THE ACTIVITY FOR THE LOGIN //
                openActivitySignUp();

                // FOR NOW JUST SHOWING A TOAST //
                Toast.makeText(context, "SINGUP CLICKED", Toast.LENGTH_LONG).show();
>>>>>>> master
            }
        });
    }


    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    // METHOD FOR THE LOGIN ACTIVITY //
    public void openActivityLogIn() {
        // CONNECTING WITH AN INTENT TO THE LOGIN CLASS FROM THE MAIN ACTIVITY //
        Intent intent = new Intent(this, login.class);

        // STARTING THE ACTIVITY //
        startActivity(intent);
    }

    // METHOD FOR THE SIGNUP ACTIVITY
    public void openActivitySignUp() {
        // CONNECTING WITH AN INTENT TO THE SIGNUP CLASS FROM THE MAIN ACTIVITY //
        Intent intent = new Intent(this, signup.class);

        // StTARTING THE ACTIVITY //
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_supportsolutions, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(36.7783, 119.4179);
        googleMap.addMarker(new MarkerOptions().position(location).title("popote"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}*/
