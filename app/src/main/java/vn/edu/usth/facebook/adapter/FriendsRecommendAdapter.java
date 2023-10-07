package vn.edu.usth.facebook.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.fragment.OtherUserProfileFragment;
import vn.edu.usth.facebook.model.Users;

//TODO: function to send friend request after click add friend
public class FriendsRecommendAdapter extends RecyclerView.Adapter<FriendsRecommendAdapter.ViewHolder>{
    private String TAG = "FRIENDS RECOMMEND ADAPTER";
    private List<Users> friends_recc;
    private Context context;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
    public FriendsRecommendAdapter(List<Users> friends_recc, Context context){
        this.friends_recc = friends_recc;
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
//        get firebase stuff
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference().child("users");

        Users friend_recc = friends_recc.get(position);

        Log.i(TAG, "FRIEND_RECC:" + friend_recc.getUser_id());

//      get friends recc ava
        getUserImg(mStorage.child(friend_recc.getUser_id()),holder);

        getUser_name(friend_recc.getUser_id(), holder);

        holder.friend_rec_ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new OtherUserProfileFragment(friend_recc.getUser_id());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.friend_fragment, myFragment).addToBackStack(null).commit();
            }
        });

        holder.add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.friend_sent.setVisibility(View.VISIBLE);
                holder.add_friend.setVisibility(View.GONE);
                holder.remove_friend_rec.setVisibility(View.GONE);

                add_friend_req(friend_recc);
            }
        });
        holder.remove_friend_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friends_recc.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return friends_recc.size();
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

    public void getUserImg(StorageReference user_storage,@NonNull FriendsRecommendAdapter.ViewHolder holder){
//        todo: threading
        user_storage.child("avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.friend_rec_ava);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Picasso.get().load(R.drawable.default_ava).into(holder.friend_rec_ava);
//                Log.e(TAG,"GET AVA/BANNER IMG ERROR:" + e);
            }
        });
    }

    public void getUser_name(String friend_id, @NonNull FriendsRecommendAdapter.ViewHolder holder){
        mDatabase.child("users").child(friend_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.friend_rec_name.setText(snapshot.child("first_name").getValue()+ " " + snapshot.child("sur_name").getValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void add_friend_req(Users friend_recc){
//       adding current_user_id -> friend_id/friends_req/ list in db
//        Log.i(TAG, "FRIEND DATABSE: " + mDatabase.child("users").child(friend_recc.getUser_id()).child("friend_reqs").child(current_user.getUid()));
        mDatabase.child("users").child(friend_recc.getUser_id()).child("friend_reqs").child(current_user.getUid()).setValue(true);
    }
}