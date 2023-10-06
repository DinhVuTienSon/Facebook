package vn.edu.usth.facebook.adapter;

import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.model.Comments;
import vn.edu.usth.facebook.model.Users;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private String TAG = "COMMENT ADAPTER";
    private Context context;
    private List<Comments> comments;
    private FirebaseUser firebaseUser;
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference();
//    public CircleImageView comment_ava;
//    public TextView comment_content, comment_name;
    public CommentAdapter(List<Comments> comments, Context context){
        this.comments = comments;
        this.context = context;
    }
    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_container, parent, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Comments comment = comments.get(position);



//        Picasso.get().load(comment.getComment_ava()).into(holder.comment_ava);
//        holder.comment_name.setText(comment.getComment_name());
        holder.comment_content.setText(comment.getContent());

        FirebaseDatabase.getInstance().getReference().child("users").child(comment.getAuthor()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                holder.comment_name.setText(user.getFirst_name()+" "+user.getSur_name());
                Log.i(TAG, "AUTHOR" + comment.getAuthor());
                getUserImg(mStorage.child("users").child(comment.getAuthor()), holder);
                holder.comment_content.setText(comment.getContent());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView comment_ava;
        private TextView comment_name, comment_content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            comment_ava = itemView.findViewById(R.id.comment_ava);
            comment_name = itemView.findViewById(R.id.comment_name);
            comment_content = itemView.findViewById(R.id.comment_content);

        }
    }
    public void getUserImg(StorageReference user_storage, @NonNull CommentAdapter.ViewHolder holder){
//        todo: threading
        user_storage.child("avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.comment_ava);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Picasso.get().load(R.drawable.default_ava).into(holder.comment_ava);
                Log.e(TAG,"GET AVA/BANNER IMG ERROR: SKIPPP");
            }
        });
    }

}