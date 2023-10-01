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
import vn.edu.usth.facebook.model.Friends;

//TODO: function to add friend after click on accept

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    private ArrayList<Friends> friends;
    private FriendsFragment context;

    public FriendsAdapter(ArrayList<Friends> friends, FriendsFragment context){
        this.friends = friends;
        this.context = context;
    }

    public void setData(ArrayList<Friends> newFriendsList) {
        friends.clear();
        friends.addAll(newFriendsList);
        notifyDataSetChanged();
    }

    public ArrayList<Friends> getFriends() {
        return friends;
    }

    public void removeFriend(int position) {
        friends.remove(position);
        notifyItemRemoved(position);
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

        holder.accept_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.friend_accepted.setVisibility(View.VISIBLE);
                holder.accept_friend.setVisibility(View.GONE);
                holder.not_accept_friend.setVisibility(View.GONE);
            }
        });
        holder.not_accept_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                friends.remove(holder.getAdapterPosition());
//                notifyItemRemoved(holder.getAdapterPosition());
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    removeFriend(adapterPosition);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView friend_req_ava;
        private TextView friend_req_name, req_date, mutual_friends, friend_accepted;
        private AppCompatButton accept_friend, not_accept_friend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            friend_req_ava = itemView.findViewById(R.id.ava_friend_req);
            friend_req_name = itemView.findViewById(R.id.friend_req_name);
            req_date = itemView.findViewById(R.id.time_request);
            mutual_friends = itemView.findViewById(R.id.mutual_friends);
            friend_accepted = itemView.findViewById(R.id.friend_accepted);
            accept_friend = itemView.findViewById(R.id.accept_friend);
            not_accept_friend = itemView.findViewById(R.id.not_accept_friend);
        }
    }
}
