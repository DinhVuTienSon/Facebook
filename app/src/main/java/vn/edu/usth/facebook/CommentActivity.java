package vn.edu.usth.facebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.adapter.CommentAdapter;
import vn.edu.usth.facebook.model.Comments;

//TODO: function to call commentator's ava, name, comment's content


public class CommentActivity extends AppCompatActivity {
    private RecyclerView comment_recyclerView;
    private CommentAdapter commentAdapter;
    private List<Comments> comments;
    private ImageView send_comment;
    private String postId;
    private String authorId;
    private EditText write_comment;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Toolbar toolbar = findViewById(R.id.comment_toolbar);
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

        comment_recyclerView = findViewById(R.id.comment_recyclerView);
        comment_recyclerView.setHasFixedSize(true);
        comment_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(comments, this);


        for (int i = 0; i < 15; i++) {
            String comment_ava = "https://picsum.photos/600/300?random&" + i;
            String comment_name = "ST";
            String comment_content = "this is a test comment";

            Comments comment = new Comments(comment_ava, comment_name, comment_content);
            comments.add(comment);
            CommentAdapter adapter = new CommentAdapter(comments, CommentActivity.this);
            RecyclerView recyclerView = findViewById(R.id.comment_recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        send_comment = findViewById(R.id.send_comment);
        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: function to upload comment
                if(TextUtils.isEmpty(write_comment.getText().toString())){
                    Toast.makeText(CommentActivity.this,"Write a comment", Toast.LENGTH_SHORT).show();
                }
                else {
                    putComment();
                }
            }
        });

        write_comment = findViewById(R.id.write_comment);
        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");
        authorId = intent.getStringExtra("authorId");

        fUser = FirebaseAuth.getInstance().getCurrentUser();

    }

    private void putComment() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("comment", write_comment.getText().toString());
        map.put("author", fUser.getUid());
        FirebaseDatabase.getInstance().getReference().child("comments").child(postId).push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CommentActivity.this, "Comment uploaded", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(CommentActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}