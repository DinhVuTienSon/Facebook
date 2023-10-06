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
import vn.edu.usth.facebook.fragment.NotificationFragment;
import vn.edu.usth.facebook.model.Notifications;

//TODO: function to handle notification

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private ArrayList<Notifications> notifications;
    private NotificationFragment context;
    public NotificationAdapter(ArrayList<Notifications> notifications, NotificationFragment context){
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

        Picasso.get().load(notification.getNotification_ava()).into(holder.notification_ava);
        holder.notification_content.setText(notification.getNotification_content());
        holder.notification_time.setText(notification.getNotification_time());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView notification_ava;
        private TextView notification_content, notification_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notification_ava = itemView.findViewById(R.id.notification_ava);
            notification_content = itemView.findViewById(R.id.notification_content);
            notification_time = itemView.findViewById(R.id.notification_time);
        }
    }
}
