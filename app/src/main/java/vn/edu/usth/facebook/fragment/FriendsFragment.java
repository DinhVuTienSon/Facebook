package vn.edu.usth.facebook.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.edu.usth.facebook.adapter.FriendsAdapter;
import vn.edu.usth.facebook.adapter.FriendsRecommendAdapter;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.model.Users;

//TODO: function to call other user ava, name, number of mutual friend

public class FriendsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Users> users;
    private ArrayList<Users> users1;

    private AppCompatButton see_all_friend_req, see_less_friend_req;
    private FriendsAdapter adapter;
    private boolean isExpanded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        see_all_friend_req = view.findViewById(R.id.see_all_friend_req);
        see_less_friend_req = view.findViewById(R.id.see_less_friend_req);

        users = new ArrayList<>();
        users1 = new ArrayList<>();
//        friends_rec = new ArrayList<>();




        for (int i = 0; i < 7; i++) {
            String friendReqAva = "https://picsum.photos/600/300?random&" + i;
            String friendReqName = "ST";
            String reqDate = "2d";
            String mutualFriends = "16 mutual friends";

            Users user = new Users(friendReqAva, friendReqName, reqDate, mutualFriends);
            users.add(user);
        }
            recyclerView = view.findViewById(R.id.friend_request_recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new FriendsAdapter(getLimitedFriendRequests(), FriendsFragment.this);
            recyclerView.setAdapter(adapter);

        if (users.size() <= 5) {
            see_all_friend_req.setVisibility(View.GONE);
        } else {
            see_all_friend_req.setVisibility(View.VISIBLE);
        }

        see_all_friend_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpanded = true;
                adapter.setData(users);
                see_all_friend_req.setVisibility(View.GONE);
                see_less_friend_req.setVisibility(View.VISIBLE);
            }
        });

        see_less_friend_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpanded = false;
                adapter.setData(getLimitedFriendRequests());
                see_all_friend_req.setVisibility(View.VISIBLE);
                see_less_friend_req.setVisibility(View.GONE);
            }
        });

        for (int i = 0; i < 16; i++) {
            String friend_rec_ava = "https://picsum.photos/600/300?random&" + i;
            String friend_rec_name = "Testing";
            String mutual_friends_rec = "18 mutual friends";

            Users user1 = new Users(friend_rec_ava, friend_rec_name, mutual_friends_rec, "");
            users1.add(user1);
        }
        FriendsRecommendAdapter adapter1 = new FriendsRecommendAdapter(users1, FriendsFragment.this);
        RecyclerView recyclerView1 = view.findViewById(R.id.friend_recommend_recyclerView);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView1.setAdapter(adapter1);

        return view;
    }

    private ArrayList<Users> getLimitedFriendRequests() {
        if (isExpanded || users.size() <= 5) {
            return users;
        } else {
            return new ArrayList<>(users.subList(0, 5));
        }
    }

}