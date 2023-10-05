package vn.edu.usth.facebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.model.Comments;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Comments> comments;
    public CommentAdapter(ArrayList<Comments> comments, Context context){
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
        Comments comment = comments.get(position);
        Picasso.get().load(comment.getCommentAva()).into(holder.comment_ava);
        holder.comment_name.setText(comment.getCommentName());
        holder.comment_content.setText(comment.getCommentContent());

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
}
