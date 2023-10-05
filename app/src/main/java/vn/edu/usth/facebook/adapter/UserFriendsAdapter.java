package vn.edu.usth.facebook.adapter;

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
import vn.edu.usth.facebook.model.Users;

public class UserFriendsAdapter extends RecyclerView.Adapter<UserFriendsAdapter.ViewHolder>{
    private ProfileFragment context;
    private ArrayList<Users> users;
    public UserFriendsAdapter(ArrayList<Users> users, ProfileFragment context){
        this.users = users;
        this.context = context;
    }

    public void setDataUserFriends(ArrayList<Users> newUsersFriendsList) {
        users.clear();
        users.addAll(newUsersFriendsList);
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
        Users user = users.get(position);
        Picasso.get().load(user.getUserAva()).into(holder.ava_user_friend);
        holder.user_friend_name.setText(user.getFirstName()+" "+user.getSurName());

    }

    @Override
    public int getItemCount() {
        return users.size();
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
