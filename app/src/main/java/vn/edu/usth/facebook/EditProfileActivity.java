package vn.edu.usth.facebook;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.model.Users;

//TODO: connect to firebase to get and save data


public class EditProfileActivity extends AppCompatActivity {
//    for debugging and checking stuff :)
    private String TAG = "EDIT PROFILE ACTIVITY";
    private CircleImageView avatar;
    private ImageView background;
    private EditText bio, live_in, location, work, education, hobbies, links;
    private TextView save_ava, save_background, save_bio, save_details, save_hobbies, save_links;

    private ActivityResultLauncher<String> galleryLauncher_avatar;
    private ActivityResultLauncher<String> galleryLauncher_background;

//    firebase stuff
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    private String uid;
    private Uri ava_uri;
    private Uri background_uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //  get database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //  get current user ID
        FirebaseUser c_user = mAuth.getCurrentUser();
        uid = c_user.getUid();

        //  get storage ref (get al the way to users/user_id)
        mStorage = FirebaseStorage.getInstance().getReference().child("users").child(uid);


//  check if user is logged in
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG,"onAuthStateChanged: sign_in: " + uid);
                }
                else{
                    Log.d(TAG, "signed out" + uid);
                }
            }
        };

        Toolbar toolbar = findViewById(R.id.edit_profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profile");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // variables contain the data get from the input fields
        avatar = findViewById(R.id.avatar);
        background = findViewById(R.id.background);
        bio = findViewById(R.id.bio);
        live_in = findViewById(R.id.live_in);
        work = findViewById(R.id.work);
        education = findViewById(R.id.education);
        hobbies = findViewById(R.id.hobbies);
        links = findViewById(R.id.links);

        // variables to handle save button
        save_ava = findViewById(R.id.save_ava);
        save_background = findViewById(R.id.save_background);
        save_bio = findViewById(R.id.save_bio);
        save_details = findViewById(R.id.save_details);
        save_hobbies = findViewById(R.id.save_hobbies);
        save_links = findViewById(R.id.save_links);

//create user obj
        Users user = new Users();

        save_ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: create function to save avatar to firebase when save button is clicked
//                get img uri
//                TODO: threading yes
                if (ava_uri != null){
                    uploadImage("avatar", ava_uri);
                }

            }
        });
        save_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO: threading
                if (background_uri != null) {
                    uploadImage("background",background_uri);
                }
            }
        });
        save_bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                create user bio = input
//                add user bio to map
//                add map to db
                user.setUser_bio(bio.getText().toString());

                Map user_bio = user.toBioMap("bio");
                update_profile(user_bio, mDatabase, "Bio");
            }
        });
        save_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO:check if any of these bozos are null to add a function so that it does delete in db if null
                user.setUser_live_in(live_in.getText().toString());
                user.setUser_work(work.getText().toString());
                user.setUser_education(education.getText().toString());

                Map user_details = user.toDetailsMap();
                update_profile(user_details, mDatabase, "Details");
            }
        });
        save_hobbies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUser_hobbies(hobbies.getText().toString());

                Map user_hobbies = user.toHobbiesMap("hobbies");
                update_profile(user_hobbies,mDatabase,"Hobbies");
            }
        });
        save_links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUser_links(links.getText().toString());

                Map user_links = user.toLinksMap("links");
                update_profile(user_links,mDatabase,"Links");
            }
        });

        // add image from gallery
        galleryLauncher_avatar = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            avatar.setImageURI(result);
                            ava_uri = result;
                        }
                    }
                });
        galleryLauncher_background = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            background.setImageURI(result);
                            background_uri = result;
                        }
                    }
                });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery_avatar();
            }
        });
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery_background();
            }
        });
    }

//    when app start, check if user is still logged in
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void update_profile(Map<String,Object> user_info, DatabaseReference db, String type){
        try{
//            add user_info map to db
            db.child("users").child(uid).updateChildren(user_info);
//          create pop up message when saved
            Toast.makeText(EditProfileActivity.this, type + " updated", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e(TAG,"ERROR: " + e);
        }
    }

    //TODO: threading
//    upload images
    public void uploadImage(String upload_file_location, Uri img){
        StorageReference store_img = mStorage.child(upload_file_location);
        store_img.putFile(img).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                store_img.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i(TAG, "UPLOAD IMG SUCCESS" + uri.toString());
                        Toast.makeText(EditProfileActivity.this, upload_file_location + " updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "IMG UPPLOAD ERROR: " + e);
            }
        });
    }

    private void openGallery_avatar() {
        galleryLauncher_avatar.launch("image/*");
    }
    private void openGallery_background() {
        galleryLauncher_background.launch("image/*");
    }

}
