package vn.edu.usth.facebook.adapter;

import android.content.Context;
import android.net.Uri;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.fragment.OtherUserProfileFragment;
import vn.edu.usth.facebook.model.Users;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private String TAG = "SEARCH ADAPTER";
    private Context context;
    private List<Users> users;
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    public SearchAdapter(List<Users> users, Context context){
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_container, parent, false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        Users user = users.get(position);

        getUserImg(mStorage.child("users").child(user.getUser_id()), holder);

        holder.search_ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new OtherUserProfileFragment(user.getUser_id());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.search_layout, myFragment).addToBackStack(null).commit();
            }
        });

        holder.search_name.setText(user.getFirst_name()+" "+user.getSur_name());
//        holder.search_info.setText(user.getSearch_info());
        holder.search_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.search_add_friend.setVisibility(View.GONE);
                holder.search_add_friend_text.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView search_ava;
        private TextView search_name, search_info, search_add_friend_text;
        private AppCompatButton search_add_friend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            search_ava = itemView.findViewById(R.id.search_ava);
            search_name = itemView.findViewById(R.id.search_name);
            search_info = itemView.findViewById(R.id.search_info);
            search_add_friend = itemView.findViewById(R.id.search_add_friend);
            search_add_friend_text = itemView.findViewById(R.id.search_add_friend_text);
        }
    }

    //TODO: func get avatar but cant get avatar
        //    /\
        //    ||
//            ||
//    said the stupid
    public void getUserImg(StorageReference user_storage, @NonNull SearchAdapter.ViewHolder holder){
//        todo: threading
        user_storage.child("avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.search_ava);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Log.e(TAG,"GET AVA/BANNER IMG ERROR:" + e);
            }
        });
    }
}