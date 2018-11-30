package tech.niocoders.com.supportsolutions;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;

import database.fb_database;

/**
 * Created by luism on 4/28/2018.
 */

public class ss_camera_helper extends AppCompatActivity implements View.OnClickListener{
    public CameraHelper cameraHelper;
    private TextView child_text_path;
    private TextView parent_text_path;

    public static boolean isParent = false;
    public  static boolean isChild = false;
    public static boolean isPermissionGranted = false;
    public VisualRecognition visualRecognition = null;
    private FirebaseAuth auth;
    private Intent prevInstance;
    private fb_database myDatabase;
    private int success;
    private String mess;
    private TextView prevIntent;
    private ImageView parentI;
    private ImageView childI;
    private Bitmap parentBitmap,childBitmap;
    private Button child;
    private Button parent;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_helper);

        visualRecognition =  new VisualRecognition("Apr 28, 2018 - 03:51:28",
                getString(R.string.api_key));

        cameraHelper = new CameraHelper(this);
        //visualRecognition.setApiKey(getString(R.string.watson_api_key).toString());

        //lets instantiate the myDatabase reference for the api
        myDatabase = new fb_database(this,getApplicationContext());
        auth = FirebaseAuth.getInstance();
        //lets find item by View
        child_text_path =  (TextView)findViewById(R.id.child_path);
        parent_text_path = (TextView)findViewById(R.id.parent_path);
        parent = (Button)findViewById(R.id.parent);
        parent.setOnClickListener(this);
        child = (Button)findViewById(R.id.child);
        child.setOnClickListener(this);
        childI = (ImageView)findViewById(R.id.imageView);

        parentI=(ImageView)findViewById(R.id.parentImg);

        prevIntent = (TextView)findViewById(R.id.extraIntent);

        if(getIntent()!=null && getIntent().hasExtra("fname"))
        {
           prevInstance = (Intent)getIntent();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser()!=null)
        {
            Toast.makeText(getApplicationContext(),"The user is logged with account = "+auth.getCurrentUser().getEmail(),Toast.LENGTH_LONG).show();

        }else{
            if(prevIntent!=null) {
                String fname = prevInstance.getStringExtra("fname");
                String lname = prevInstance.getStringExtra("lname");
                String phone = prevInstance.getStringExtra("phone");
                String email = prevInstance.getStringExtra("email");
                String gender = prevInstance.getStringExtra("gender");
                String custody = prevInstance.getStringExtra("custody");
                String language = prevInstance.getStringExtra("language");
                String password = prevInstance.getStringExtra("password");
                prevIntent.setText("First name : " + fname +
                        "/nLast name : " + lname +
                        "/nEmail : " + email +
                        "/nPhone : "+ phone+
                        "/nGender : " + gender +
                        "/nCustody :" + custody +
                        "/nlanguage : " + language);
            }
            //Toast.makeText(getApplicationContext(),"Please input all credentials to log in on our end!!",Toast.LENGTH_LONG).show();
        }


    }

    //this method creates an 15 digit string Id for our database
    public String getTokenizableId()
    {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder string = new StringBuilder();
        Random rnd = new Random();
        while (string.length() < 16) {
            int index = (int) (rnd.nextFloat() * characters.length());
            string.append(characters.charAt(index));
        }

        return  string.toString();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id)
        {
            case R.id.parent:
                isParent =  true;
                Toast.makeText(getApplicationContext(),"justClicked parent button!",Toast.LENGTH_LONG).show();
                cameraHelper.dispatchTakePictureIntent();
                break;
            case R.id.child:
                Toast.makeText(getApplicationContext(),"justClicked parent button!",Toast.LENGTH_LONG).show();
                isChild = true;
                cameraHelper.dispatchTakePictureIntent();
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CameraHelper.REQUEST_PERMISSION: {
                // permission granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                  isPermissionGranted = true;
                }
            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
            String fname = prevInstance.getStringExtra("fname");
            String lname = prevInstance.getStringExtra("lname");
            String phone =  prevInstance.getStringExtra("phone");
            String email =  prevInstance.getStringExtra("email");
            String gender = prevInstance.getStringExtra("gender");
            String custody = prevInstance.getStringExtra("custody");
            String language = prevInstance.getStringExtra("language");
            String password = prevInstance.getStringExtra("password");

            if(isParent) {
                parent_text_path.setText(cameraHelper.getFile(resultCode).toString());

                parentBitmap =  BitmapFactory.decodeFile(cameraHelper.getFile(resultCode).getAbsolutePath());
                if(parentBitmap!=null)
                {
                    parentBitmap = Bitmap.createScaledBitmap(parentBitmap,150,150,true);
                    parentI.setImageBitmap(parentBitmap);
                }
                //postImagesToBucket(cameraHelper.getFile(resultCode).toURI().getPath(),"parent");
                System.out.println(cameraHelper.getFile(resultCode));
            }else if(isChild)
            {

                 childBitmap =  BitmapFactory.decodeFile(cameraHelper.getFile(resultCode).getAbsolutePath());
                 if(childBitmap!=null)
                 {
                     childBitmap = Bitmap.createScaledBitmap(childBitmap,150,150,true);
                     childI.setImageBitmap(childBitmap);
                 }
                //postImagesToBucket(cameraHelper.getFile(resultCode).toURI().getPath(),"child");
                child_text_path.setText(cameraHelper.getFile(resultCode).toString());
                System.out.println(cameraHelper.getFile(resultCode));
            }

           // if(parentBitmap!=null && childBitmap !=null)
            {
                  Toast.makeText(getApplicationContext(),"we are saving data!",Toast.LENGTH_LONG).show();
               /* prevIntent.setText("First name : "+fname+
                   "/nLast name : "+lname+
                "/nEmail : "+email+
                "/nGender : "+gender+
                "/nCustody :"+custody+
                "/nlanguage : "+language);*/
                //we writting a profile for user sign up
                createAccountAndValidate(email,password);
                myDatabase.FireBaseWriteNewUser(fname,lname,phone,email,password,getTokenizableId(),gender,custody,language);
            }

        }


        isParent =false;
        isChild = false;
    }

    public void signInOrSavedNewUser( String Email, String Password)
    {
        auth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mess = "Hey Account Created Host! check ur email and validate!";
                            sendEmailVerification();
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d("CreateAccount", "createUserWithEmail:success");
                            Toast.makeText(getApplicationContext(), "The user just sign up on our end! ",Toast.LENGTH_LONG).show();
                            FirebaseUser user = auth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("errorSignIn", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed. Email exist on Account creation!",Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    public void sendEmailVerification() {

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = auth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("verificationFail", "sendEmailVerification", task.getException());
                            Toast.makeText(getApplicationContext(),
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }
    public void SignOut()
    {
        if(auth.getCurrentUser()!=null)
        {
            Toast.makeText(getApplicationContext()," the user "+auth.getCurrentUser().getEmail()+" has log out on our end!",Toast.LENGTH_LONG).show();
            auth.signOut();

        }

    }


    public void createAccountAndValidate(String em,String pass)
    {

        // Building Parameters
        if(pass.length()>0 && em.length()>0)
        {

            signInOrSavedNewUser(em,pass);

        }else{
            success=-1;
            mess="make sure all the credentials are inputed !!";
        }
    }


    public void postImagesToBucket(String path, String person)
    {
        try {
            if(person.equals("parent")) {
                InputStream imagesStream = new FileInputStream(path);
                ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                        .imagesFile(imagesStream)
                        .imagesFilename("parent.jpg")
                        .threshold((float) 0.6)
                        .owners(Arrays.asList("me"))
                        .build();
                ClassifiedImages result = visualRecognition.classify(classifyOptions).execute();
                System.out.println(result);
            }else{
                InputStream imagesStream = new FileInputStream(path);
                ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                        .imagesFile(imagesStream)
                        .imagesFilename("child.jpg")
                        .threshold((float) 0.6)
                        .owners(Arrays.asList("me"))
                        .build();
                ClassifiedImages result = visualRecognition.classify(classifyOptions).execute();
                System.out.println(result);
            }
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
