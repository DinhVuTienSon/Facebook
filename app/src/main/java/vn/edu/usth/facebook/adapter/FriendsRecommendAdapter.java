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
import vn.edu.usth.facebook.model.Friends_recommend;

public class FriendsRecommendAdapter extends RecyclerView.Adapter<FriendsRecommendAdapter.ViewHolder>{

    private ArrayList<Friends_recommend> friends_rec;
    private FriendsFragment context;
    public FriendsRecommendAdapter(ArrayList<Friends_recommend> friends_rec, FriendsFragment context){
        this.friends_rec = friends_rec;
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
        Friends_recommend friend_rec = friends_rec.get(position);
        Picasso.get().load(friend_rec.getFriend_rec_ava()).into(holder.friend_rec_ava);
        holder.friend_rec_name.setText(friend_rec.getFriend_rec_name());
        holder.mutual_friends_rec.setText(friend_rec.getMutual_friends_rec());
    }

    @Override
    public int getItemCount() {
        return friends_rec.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView friend_rec_ava;
        private TextView friend_rec_name, mutual_friends_rec;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            friend_rec_ava = itemView.findViewById(R.id.ava_friend_rec);
            friend_rec_name = itemView.findViewById(R.id.friend_rec_name);
            mutual_friends_rec = itemView.findViewById(R.id.mutual_friends_rec);
        }
    }
}
