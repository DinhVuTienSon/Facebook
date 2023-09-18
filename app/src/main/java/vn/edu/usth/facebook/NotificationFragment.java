package vn.edu.usth.facebook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.edu.usth.facebook.model.Notifications;
import vn.edu.usth.facebook.model.Post;


public class NotificationFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Notifications> notifications;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        notifications = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String notification_ava = "https://picsum.photos/600/300?random&";
            String notification_content = "ST liked your post";
            String notification_time = "2 hours ago";

            Notifications notification = new Notifications(notification_ava, notification_content, notification_time);
            notifications.add(notification);

            NotificationAdapter adapter = new NotificationAdapter(notifications, NotificationFragment.this);
            RecyclerView recyclerView = view.findViewById(R.id.notification_recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }
}