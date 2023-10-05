package vn.edu.usth.facebook;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.model.Post;

// TODO: function to upload post after click on post button
// TODO: function to call user ava, name

public class UploadPostActivity extends AppCompatActivity {
    private String TAG = "UPLOAD POST ACTIVITY"; // for debugging and messages
    private CircleImageView author_post_ava;
    private TextView author_post_name;
    private AppCompatButton post_btn;
    private EditText post_text;
    private ImageView post_image, open_album, open_camera;
    private ActivityResultLauncher<String> galleryLauncher_img;

    //    firebase stuff
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    private String uid;
    private Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);

        //  get database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //  get current user ID
        FirebaseUser c_user = mAuth.getCurrentUser();
        uid = c_user.getUid();

        //  get storage ref (get al the way to users/user_id)
        mStorage = FirebaseStorage.getInstance().getReference().child("posts");


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

        Toolbar toolbar = findViewById(R.id.upload_post_toolbar);
        setSupportActionBar(toolbar);

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

        author_post_ava = findViewById(R.id.upload_post_ava);
        author_post_name = findViewById(R.id.upload_post_name);
        post_btn = findViewById(R.id.post_btn);
        post_text = findViewById(R.id.post_text);
        post_image = findViewById(R.id.post_image);
        open_album = findViewById(R.id.open_album);
        open_camera = findViewById(R.id.open_camera);

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add image
//                TODO: jump back to home screen after post
                String post_id = mDatabase.child("posts").push().getKey();
                Post post = new Post(post_id, uid);
//                set post description
                post.setPostDescription(post_text.getText().toString());
//                get time of posting (use Map type because ServerValue.TIMESTAMP is Map and can be converted back to date)
                Map<String,String> post_time = ServerValue.TIMESTAMP;

                if (image_uri != null){//upload post with img
                    uploadPost(post, post_time, mDatabase);
                    uploadImage(post_id,image_uri);
                }
                else{//upload post without img
                    uploadPost(post, post_time, mDatabase);
                }

            }
        });

        galleryLauncher_img = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            post_image.setImageURI(result);
                            image_uri = result;
                        }
                    }
                });
        open_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        open_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultLauncher.launch(intent);
            }
        });
    }
    private void openGallery() {
        galleryLauncher_img.launch("image/*");
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null){
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    post_image.setImageBitmap(bitmap);

                }
            });

    public void uploadPost(Post post, Map<String,String> date, DatabaseReference db){
        try{
            //create an emty map to add to db
            HashMap <String,Object> add_infos_to_db = new HashMap<>();

            add_infos_to_db.put(
                    //create a uid node under users in firebase
                    "/posts/" + post.getAuthorId() + "_" + post.getPostId(),
                    //add post infos map to hashmap(with key set as authorID_postID) to add to db
                    post.toNewTextMap(date));
            //using  firebase's update children to add to db
            db.updateChildren(add_infos_to_db);
        }
        catch (Exception e){
            Log.e("WRITE TO DB ERROR: ", "PROBLEM WHEN WRITING TO FIREBASE" + e);
        }
    }

    public void uploadImage(String upload_file_location, Uri img){
        StorageReference store_img = mStorage.child(upload_file_location);
        store_img.putFile(img).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                store_img.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i(TAG, "UPLOAD IMG SUCCESS" + uri.toString());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "IMG UPLOAD ERROR: " + e);
            }
        });
    }

}