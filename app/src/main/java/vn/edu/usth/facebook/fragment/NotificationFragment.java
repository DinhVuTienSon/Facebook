package vn.edu.usth.facebook.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.edu.usth.facebook.adapter.NotificationAdapter;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.model.Notifications;

//TODO: function to call ava of user causing notification and notification content

public class NotificationFragment extends Fragment {
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private List<Notifications> notifications;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = view.findViewById(R.id.notification_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notifications = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(notifications, getContext());
        recyclerView.setAdapter(notificationAdapter);


        readNotifications();

//        for (int i = 0; i < 10; i++) {
//            String notification_ava = "https://picsum.photos/600/300?random&"+i;
//            String notification_content = "ST liked your post";
//            String notification_time = "2 hours ago";
//
//            Notifications notification = new Notifications(notification_ava, notification_content, notification_time);
//            notifications.add(notification);
//
//            NotificationAdapter adapter = new NotificationAdapter(notifications, getContext());
//            RecyclerView recyclerView = view.findViewById(R.id.notification_recyclerView);
//            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setAdapter(adapter);
//        }

        return view;
    }

    private void readNotifications() {
        FirebaseDatabase.getInstance().getReference().child("notifications").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sp : snapshot.getChildren()){
                    notifications.add(snapshot.getValue(Notifications.class));
                }
                Collections.reverse(notifications);
                notificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}