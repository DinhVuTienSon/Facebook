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
import vn.edu.usth.facebook.fragment.FriendsFragment;
import vn.edu.usth.facebook.model.Friends;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    private ArrayList<Friends> friends;
    private FriendsFragment context;

    public FriendsAdapter(ArrayList<Friends> friends, FriendsFragment context){
        this.friends = friends;
        this.context = context;
    }
    @NonNull
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_req, parent, false);
        return new FriendsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.ViewHolder holder, int position) {
        Friends friend = friends.get(position);
        Picasso.get().load(friend.getFriendReqAva()).into(holder.friend_req_ava);
        holder.friend_req_name.setText(friend.getFriendReqName());
        holder.req_date.setText(friend.getReqDate());
        holder.mutual_friends.setText(friend.getMutualFriends());
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView friend_req_ava;
        private TextView friend_req_name, req_date, mutual_friends;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            friend_req_ava = itemView.findViewById(R.id.ava_friend_req);
            friend_req_name = itemView.findViewById(R.id.friend_req_name);
            req_date = itemView.findViewById(R.id.time_request);
            mutual_friends = itemView.findViewById(R.id.mutual_friends);
        }
    }
}
