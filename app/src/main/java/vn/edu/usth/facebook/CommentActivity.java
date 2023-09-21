package vn.edu.usth.facebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CommentActivity extends AppCompatActivity {
    private EditText write_comment;
    private ImageView send_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
    }
}
//        Toolbar toolbar = findViewById(R.id.comment_toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Comments");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        write_comment = findViewById(R.id.write_comment);
//        send_comment = findViewById(R.id.send_comment);
//
//        send_comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//    }
