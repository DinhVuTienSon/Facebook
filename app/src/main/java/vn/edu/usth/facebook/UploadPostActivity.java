package vn.edu.usth.facebook;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.R;

public class UploadPostActivity extends AppCompatActivity {
    private CircleImageView upload_post_ava;
    private TextView upload_post_name;
    private AppCompatButton post_btn;
    private EditText post_text;
    private ImageView post_image, open_album, open_camera;
    private ActivityResultLauncher<String> galleryLauncher_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);

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

        upload_post_ava = findViewById(R.id.upload_post_ava);
        upload_post_name = findViewById(R.id.upload_post_name);
        post_btn = findViewById(R.id.post_btn);
        post_text = findViewById(R.id.post_text);
        post_image = findViewById(R.id.post_image);
        open_album = findViewById(R.id.open_album);
        open_camera = findViewById(R.id.open_camera);

        galleryLauncher_img = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            post_image.setImageURI(result);
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
}