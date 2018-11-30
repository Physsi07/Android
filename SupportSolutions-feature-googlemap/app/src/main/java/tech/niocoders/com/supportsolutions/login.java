package tech.niocoders.com.supportsolutions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import database.fb_database;

public class login extends AppCompatActivity{

    // VARIABLES //
    private ImageView logo;
    private EditText  email;
    private EditText  password;
    private TextView  email_textview;
    private TextView  password_textview;
    private Button    btn;
    private FirebaseAuth auth;
    private fb_database mydatabase;
    // CONTEXT VARIABLE //
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // INITIALIZING THE VARIABLES //
        logo = findViewById(R.id.img_logo);
        email = findViewById(R.id.email_editText);
        password = findViewById(R.id.password_editText);
        email_textview = findViewById(R.id.email_text);
        password_textview = findViewById(R.id.password_text);
        btn = findViewById(R.id.login_btn);

        // INITIALIZING THE CONTEXT //
        context = getApplicationContext();
        auth = FirebaseAuth.getInstance();

        // SETTING THE BUTTON LISTENER TO BE ABLE TO LOGIN //
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // SHOWING THE USER A TOAST FOR THEM TO KNOW THAT THEY LOGIN //
                executeLogIn();

            }
        });
    }


    @Override
    public void onStart()
    {
        super.onStart();
        if(auth.getCurrentUser()!=null)
        {
            Toast.makeText(getApplicationContext(),"The user is logged with account = "+auth.getCurrentUser().getEmail(),Toast.LENGTH_LONG).show();
            startContentUI(auth.getCurrentUser());


        }else{
            Toast.makeText(getApplicationContext(),"Please input all credentials to log in on our end!!",Toast.LENGTH_LONG).show();

        }

    }

    public void executeLogIn()
    {
        if(email.getText().length()>0 && password.getText().length()>0)
        {
            String em = email.getText().toString();
            String pass =  password.getText().toString();
            if(em.length()>0 && pass.length()>0){
                AuthCredential credential = EmailAuthProvider
                        .getCredential(em, pass);
                auth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("LoggedIn", "signInWithCredential:success");
                                    FirebaseUser user = auth.getCurrentUser();
                                    mydatabase.getUserDataProfileFromDataBaseByTokenId(user.getUid());

                                    Toast.makeText(getApplicationContext(), "Logged in user = "+user.getEmail(),
                                            Toast.LENGTH_SHORT).show();
                                    startContentUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("LoggedError", "signInWithCredential:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }else{
                Toast.makeText(getApplicationContext(),"Please check email or password if not then reset it!!",Toast.LENGTH_LONG).show();

            }

        }else{
            Toast.makeText(getApplicationContext(),"Please make sure email and password are not blank!!",Toast.LENGTH_LONG).show();
        }
    }


    public void startContentUI(FirebaseUser user)
    {
        Context context =  login.this;
        Class mainUI =  MainContentUI.class;
        Intent activity = new Intent(context, mainUI);
        activity.putExtra("authentication",user.getUid().toString());
        startActivity(activity);


    }
}
