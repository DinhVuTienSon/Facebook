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
import vn.edu.usth.facebook.fragment.ProfileFragment;
import vn.edu.usth.facebook.model.Friends;
import vn.edu.usth.facebook.model.Users_friends;

public class UserFriendsAdapter extends RecyclerView.Adapter<UserFriendsAdapter.ViewHolder>{
    private ProfileFragment context;
    private ArrayList<Users_friends> users_friends;
    public UserFriendsAdapter(ArrayList<Users_friends> users_friends, ProfileFragment context){
        this.users_friends = users_friends;
        this.context = context;
    }

    public void setDataUserFriends(ArrayList<Users_friends> newUsersFriendsList) {
        users_friends.clear();
        users_friends.addAll(newUsersFriendsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserFriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_friends_container, parent, false);
        return new UserFriendsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFriendsAdapter.ViewHolder holder, int position) {
        Users_friends user_friends = users_friends.get(position);
        Picasso.get().load(user_friends.getUser_friend_ava()).into(holder.ava_user_friend);
        holder.user_friend_name.setText(user_friends.getUser_friend_name());

    }

    @Override
    public int getItemCount() {
        return users_friends.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ava_user_friend;
        private TextView user_friend_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ava_user_friend = itemView.findViewById(R.id.ava_user_friend);
            user_friend_name = itemView.findViewById(R.id.user_friend_name);
        }
    }
}
