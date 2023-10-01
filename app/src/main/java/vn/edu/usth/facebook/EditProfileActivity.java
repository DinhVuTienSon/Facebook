package vn.edu.usth.facebook;

import androidx.activity.result.ActivityResultCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import de.hdodenhof.circleimageview.CircleImageView;

//TODO: connect to firebase to get and save data

public class EditProfileActivity extends AppCompatActivity {
    private CircleImageView avatar;
    private ImageView background;
    private EditText bio, live_in, location, work, education, hobbies, links;
    private TextView save_ava, save_background, save_bio, save_details, save_hobbies, save_links;

    private ActivityResultLauncher<String> galleryLauncher_avatar;
    private ActivityResultLauncher<String> galleryLauncher_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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
        location = findViewById(R.id.location);
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

        save_ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: create function to save avatar to firebase when save button is clicked
            }
        });
        save_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: create function to save background image to firebase when save button is clicked
            }
        });
        save_bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: create function to save bio to firebase when save button is clicked
            }
        });
        save_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: create function to save details to firebase when save button is clicked
            }
        });
        save_hobbies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: create function to save hobbies to firebase when save button is clicked
            }
        });
        save_links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: create function to save links to firebase when save button is clicked
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

    private void openGallery_avatar() {
        galleryLauncher_avatar.launch("image/*");
    }
    private void openGallery_background() {
        galleryLauncher_background.launch("image/*");
    }

}
