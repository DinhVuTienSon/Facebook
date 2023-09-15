package vn.edu.usth.facebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> posts;
    private Context context;

    public PostAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        Picasso.get().load(post.getAuthorImage()).into(holder.author_img);
        holder.author_name.setText(post.getAuthorName());
        holder.time_post.setText(post.getPostDate());
        holder.post_description.setText(post.getPostDescription());
        Picasso.get().load(post.getPostImage()).into(holder.post_img);
        holder.post_likes.setText(post.getPostLikes());
        holder.post_comments.setText(post.getPostComments());
//        holder.username.setText(String.format("%s", posts.get(position).getUsername()));
//        holder.text.setText(String.format("%s", posts.get(position).getText()));
//        Picasso.get().load(posts.get(position).getPhoto()).into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView author_img;
        private TextView author_name, time_post, post_description;
        private ImageView post_img;
        private TextView post_likes, post_comments;
        private LinearLayout post_share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            author_img = itemView.findViewById(R.id.idCVAuthor);
            author_name = itemView.findViewById(R.id.idTVAuthorName);
            time_post = itemView.findViewById(R.id.idTVTime);
            post_description = itemView.findViewById(R.id.idTVDescription);
            post_img = itemView.findViewById(R.id.idIVPost);
            post_likes = itemView.findViewById(R.id.idTVLikes);
            post_comments = itemView.findViewById(R.id.idTVComments);
            post_share = itemView.findViewById(R.id.idLLShare);
        }
    }
}
//        private TextView username;
//        private TextView text;
//        private ImageView photo;
//        public ViewHolder(@NonNull View itemView) {
//
//            super(itemView);
//            username = itemView.findViewById(R.id.username);
//            text = itemView.findViewById(R.id.text);
//            photo = itemView.findViewById(R.id.photo);
//        }
//    }
//}