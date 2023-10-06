package vn.edu.usth.facebook.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.fragment.FriendsFragment;
import vn.edu.usth.facebook.model.Users;

//TODO: function to send friend request after click add friend

public class FriendsRecommendAdapter extends RecyclerView.Adapter<FriendsRecommendAdapter.ViewHolder>{

    private ArrayList<Users> users1;
    private FriendsFragment context;
    public FriendsRecommendAdapter(ArrayList<Users> users1, FriendsFragment context){
        this.users1 = users1;
        this.context = context;

    }
    @NonNull
    @Override
    public FriendsRecommendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_rec, parent, false);
        return new FriendsRecommendAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsRecommendAdapter.ViewHolder holder, int position) {
        Users user = users1.get(position);
        Picasso.get().load(user.getUser_ava()).into(holder.friend_rec_ava);
        holder.friend_rec_name.setText(user.getFirst_name()+" "+user.getSur_name());
//        holder.mutual_friends_rec.setText(friend_rec.getMutualFriends_rec());

        holder.add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.friend_sent.setVisibility(View.VISIBLE);
                holder.add_friend.setVisibility(View.GONE);
                holder.remove_friend_rec.setVisibility(View.GONE);
            }
        });
        holder.remove_friend_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                users1.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return users1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView friend_rec_ava;
        private TextView friend_rec_name, mutual_friends_rec, friend_sent;
        private AppCompatButton add_friend, remove_friend_rec;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            friend_rec_ava = itemView.findViewById(R.id.ava_friend_rec);
            friend_rec_name = itemView.findViewById(R.id.friend_rec_name);
            mutual_friends_rec = itemView.findViewById(R.id.mutual_friends_rec);
            friend_sent = itemView.findViewById(R.id.friend_sent);
            add_friend = itemView.findViewById(R.id.add_friend);
            remove_friend_rec = itemView.findViewById(R.id.remove_friend_rec);
        }
    }
}
