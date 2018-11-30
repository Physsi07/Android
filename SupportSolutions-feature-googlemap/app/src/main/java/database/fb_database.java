package database;

/**
 * Created by luism on 4/28/2018.
 */

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.HashMap;

import tech.niocoders.com.supportsolutions.ss_camera_helper;


/**
 * Created by luism on 7/29/2017.
 */

public class fb_database {
    private com.google.firebase.database.DatabaseReference DatabaseReference;
    private FirebaseStorage mStorage;
    private StorageReference mStorageReference;
    private Context myContext;
    private boolean updated = false;
    private boolean authenticated = false;
    private boolean emailExist = false;
    private member authenticatedUser = null;
    private boolean checkIfProfileisCreated;
    private boolean ICanWaitSitted = true;

    private String SUPPORT_SOLUTION_PARENTS_URL = "gs://supportsolutions-1c937.appspot.com/parents/";
    private String SUPPORT_SOLUTION_CHILDS_URL = "gs://supportsolutions-1c937.appspot.com/childs/";
    private String SUPPORT_SOLUTION_EVENTS_URL = "gs://supportsolutions-1c937.appspot.com/events/";

    private HashMap<String, String> levelBackgrounds;
    //lets work with firebase task management

    private File imageFile = null;
    private File levelFile = null;

    private ss_camera_helper camera_helper;

    private member profile;


    public fb_database(ss_camera_helper camera, Context context) {
        this.myContext = context;
        this.camera_helper = camera;
        DatabaseReference = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance();
        this.updated = false;
        this.emailExist = false;
        this.authenticated = false;
        this.authenticatedUser = null;
        this.checkIfProfileisCreated = false;
        this.levelBackgrounds = new HashMap<String, String>();


    }

    public fb_database(Context context) {
        this.myContext = context;
        DatabaseReference = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance();
        this.updated = false;
        this.emailExist = false;
        this.authenticated = false;
        this.authenticatedUser = null;
        this.checkIfProfileisCreated = false;
        this.levelBackgrounds = new HashMap<String, String>();

    }


    public void setUpdated(boolean upd) {
        this.updated = upd;
    }

    public void setProfile(member p)
    {this.profile=p;}

    public void setAuthenticated(boolean auth) {
        this.authenticated = auth;
    }

    public void setEmailExist(boolean Ex) {
        this.emailExist = Ex;
    }

    public void setCheckIfProfileisCreated(boolean t) {
        this.checkIfProfileisCreated = t;
    }


    //getthers

    public HashMap<String, String> getLevelBackgrounds() {
        return this.levelBackgrounds;
    }

    public boolean getUpdatedPassword() {
        return this.updated;
    }

    public boolean getAuthenticatedUser() {
        return this.authenticated;
    }

    public member getCurrentUserLoggedIn() {
        return this.authenticatedUser;
    }

    public boolean getEmailExistCheck() {
        return this.emailExist;
    }

    public boolean isCheckIfProfileisCreated() {
        return this.checkIfProfileisCreated == true;
    }
    public member getProfile()
    {return this.profile;}

    public void FireBaseWriteNewUser(String fname,String lname,String phone, String email, String password, String security, String gender,String custody, String language) {
        member user = new member(fname,lname, phone,email, password, security, gender,custody, language);
        DatabaseReference.child("profile").child(user.getUserId()).setValue(user);
    }

    public void createProfileWithUserTokenId(final String userId) {
        FirebaseDatabase.getInstance().getReference().child("profile").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       /* worm_profile profile = dataSnapshot.getValue(worm_profile.class);

                        if (profile==null) {
                            worm_profile profiles = new worm_profile(userId, myContext);
                            DatabaseReference.child("profiles").child(userId).setValue(profiles);
                        }
                       */
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }

    public void getUserDataProfileFromDataBaseByTokenId(final String tokenId) {
        //worm_profile exist = null;
        FirebaseDatabase.getInstance().getReference().child("profiles")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        member exist = null;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            member wp = snapshot.getValue(member.class);
                            if (wp.getTokenizableId().equals(tokenId)) {

                                exist = wp;
                                break;
                            }
                        }

                        if (exist != null) {

                            setProfile(exist);

                        }

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }


    public void UpDateUserAccountPassword(final String email, final String security, final String value) {


        FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            member user = snapshot.getValue(member.class);
                            if (user.getEmail().equals(email)
                                    && user.getSecurity().equals(security)) {
                                DatabaseReference.child("users").child(user.getUserId()).child("password").setValue(value);
                                setUpdated(true);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }


    public void CheckIfEmailIsAlreadyRegiter(final String email) {

        FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            member user = snapshot.getValue(member.class);

                            if (user.getEmail().equals(email))
                                emailExist = true;
                            break;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }


    public member getFireBaseUserFromDataBase(final String email, final String password) {
        FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            member user = snapshot.getValue(member.class);

                            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                                authenticatedUser = user;
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        return authenticatedUser;
    }

    //get user from data base by email reference
    public member getFireBaseUserFromDataBaseByEmailReference(final String email) {
        FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            member user = snapshot.getValue(member.class);

                            if (user.getEmail().equals(email)) {
                                authenticatedUser = user;
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        return authenticatedUser;
    }

}

    //LETS LOAD A LEVEL FROM DB STORAGES
    /*public void LoalLevelBaseOnNameAndIdFromDataBase(final String id) { //lets load image background and game data
        //Toast.makeText(myContext,"trying to load image with name = "+id+".jfif",Toast.LENGTH_LONG).show();

        mStorageReference = mStorage.getReferenceFromUrl(this.LEVEL_IMG_URL).child(id+".png");

        try {
            final File localFile = File.createTempFile("images", "jfif");
            mStorageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(myContext,"The image exist and its path  = "+localFile.getAbsolutePath(),Toast.LENGTH_LONG).show();
                    imageFile = localFile;
                    if(game!=null)
                    {
                        Bitmap img = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        img  =  Bitmap.createScaledBitmap(img, game.getViewPort().getScreenWidth(),game.getViewPort().getScreenHeight(), true);

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}


        //now lets load the file hww type
        mStorageReference =  mStorage.getReferenceFromUrl(this.LEVEL_REFERENCE_URL).child(id+".hww");
        try {
            final File localFile = File.createTempFile("levels", "hww");
            mStorageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(myContext,"The level exist and its path  = "+localFile.getAbsolutePath(),Toast.LENGTH_LONG).show();
                    levelFile = localFile;
                    if(game!=null)
                    {
                        try {
                            game.getFilemanager().loadSavedLevel(levelFile);
                        }catch(IOException e){
                            Log.d("fileRead", e.getMessage());
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}


    }

    public void getLevelBackgroundImgs(final String id)
    {
        mStorageReference = mStorage.getReferenceFromUrl(this.LEVEL_IMG_URL).child(id+".png");
        mStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {


                levelBackgrounds.put(id,uri.toString());



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.d("Exception", exception.getMessage());
            }
        });

    }

    //lets load the level selection Stage only Once
    public void LoadLevelSelectionStageSingleTon(final String id) {

        //now lets load the file hww type
        mStorageReference =  mStorage.getReferenceFromUrl(this.LEVEL_SELECTION_URL).child(id+".hww");
        try {
            final File localFile = File.createTempFile("levels", "hww");
            mStorageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(myContext,"The level exist and its path  = "+localFile.getAbsolutePath(),Toast.LENGTH_LONG).show();
                    levelFile = localFile;
                    if(game!=null)
                    {
                        try {
                            game.getFilemanager().LoadLevelSelectionFile(levelFile);
                        }catch(IOException e){
                            Log.d("fileRead", e.getMessage());
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}

    }

}
/*
one time call


DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
 DatabaseReference mostafa = ref.child("Users").child("mostafa_farahat22@yahoo.com").child("_email");

 mostafa.addListenerForSingleValueEvent(new ValueEventListener() {
 @Override
 public void onDataChange(DataSnapshot dataSnapshot) {
    String email = dataSnapshot.getValue(String.class);
    //do what you want with the email
 }

 @Override
 public void onCancelled(DatabaseError databaseError) {

 }
 });*/

/*
real time calls
mostafa.addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(DataSnapshot dataSnapshot) {
    String email = dataSnapshot.getValue(String.class);

    display.setText(email);
}

@Override
public void onCancelled(DatabaseError databaseError) {

}
});
 */