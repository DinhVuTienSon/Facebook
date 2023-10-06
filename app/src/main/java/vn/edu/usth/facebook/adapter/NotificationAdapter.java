package vn.edu.usth.facebook.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.model.Notifications;
import vn.edu.usth.facebook.model.Users;

//TODO: function to handle notification
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<Notifications> notifications;
    private Context context;
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    public NotificationAdapter(List<Notifications> notifications, Context context){
        this.notifications = notifications;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_container, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        Notifications notification = notifications.get(position);

        getUser(holder.notification_user_name, notification.getUserId());
        getUserImg(mStorage.child("users").child(notification.getUserId()), holder);


        Picasso.get().load(notification.getNotification_ava()).into(holder.notification_ava);
        holder.notification_content.setText(notification.getNotification_content());
        holder.notification_user_name.setText(notification.getNotification_user_name());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView notification_ava;
        private TextView notification_content, notification_user_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notification_ava = itemView.findViewById(R.id.notification_ava);
            notification_content = itemView.findViewById(R.id.notification_content);
            notification_user_name = itemView.findViewById(R.id.notification_user_name);
        }
    }
    private void getUser( TextView textView, String userId){
        FirebaseDatabase.getInstance().getReference().child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
//                if(users.getUser_ava().equals(null)){
//                    imageView.setImageResource(R.drawable.default_ava);
//                }
//                else {
//                    Picasso.get().load(users.getUser_ava()).into(imageView);
//                }

                textView.setText(users.getFirst_name()+" "+users.getSur_name());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getUserImg(StorageReference user_storage, @NonNull NotificationAdapter.ViewHolder holder){
//        todo: threading
        user_storage.child("avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.notification_ava);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Log.e(TAG,"GET AVA/BANNER IMG ERROR:" + e);
            }
        });
    }
}