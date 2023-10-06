package vn.edu.usth.facebook.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.facebook.adapter.FriendsAdapter;
import vn.edu.usth.facebook.adapter.FriendsRecommendAdapter;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.model.Users;

//TODO: function to call other user ava, name, number of mutual friend

public class FriendsFragment extends Fragment {
    private String TAG = "FRIENDS FRAGMENT";
    private RecyclerView recycler_view_req;
    private RecyclerView recycler_view_recc;
    private List<Users> friends_req;
    private List<Users> friends_recc;

    private AppCompatButton see_all_friend_req, see_less_friend_req;
    private FriendsAdapter adapter;
    private FriendsRecommendAdapter adapter_recc;
    private boolean isExpanded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        see_all_friend_req = view.findViewById(R.id.see_all_friend_req);
        see_less_friend_req = view.findViewById(R.id.see_less_friend_req);


//        FRIENDS REQUEST
//        recycler_view_req = view.findViewById(R.id.friend_request_recyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recycler_view_req.setLayoutManager(layoutManager);
//        adapter = new FriendsAdapter(getLimitedFriendRequests(), FriendsFragment.this);
//        recycler_view_req.setAdapter(adapter);
//
//        if (friends_req.size() <= 5) {
//            see_all_friend_req.setVisibility(View.GONE);
//        } else {
//            see_all_friend_req.setVisibility(View.VISIBLE);
//        }
//
//        see_all_friend_req.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isExpanded = true;
//                adapter.setData(friends_req);
//                see_all_friend_req.setVisibility(View.GONE);
//                see_less_friend_req.setVisibility(View.VISIBLE);
//            }
//        });
//
//        see_less_friend_req.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isExpanded = false;
//                adapter.setData(getLimitedFriendRequests());
//                see_all_friend_req.setVisibility(View.VISIBLE);
//                see_less_friend_req.setVisibility(View.GONE);
//            }
//        });

        //    FRIEND RECOMMEND
        adapter_recc = new FriendsRecommendAdapter(friends_recc, getContext());
        recycler_view_recc = view.findViewById(R.id.friend_recommend_recyclerView);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setStackFromEnd(true);// -> latest friend first
        layoutManager1.setReverseLayout(true);
        recycler_view_recc.setLayoutManager(layoutManager1);

        friends_recc = new ArrayList<>();
        adapter_recc = new FriendsRecommendAdapter(friends_recc, getContext());
        recycler_view_recc.setAdapter(adapter_recc);

//        get friend recommend
        getFriendRecommend();
        return view;
    }

    public void getFriendRecommend(){
        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friends_recc.clear();
                Log.i(TAG, "DATABASE FRIENDS CHECK: " + FirebaseDatabase.getInstance().getReference().child("users"));

                for(DataSnapshot sp : snapshot.getChildren()){
                    Users friend_recc = sp.getValue(Users.class);
                    friend_recc.setUser_id(sp.getKey());
                    Log.i(TAG,"FRIEND RECOMMEND ID: " + friend_recc.getUser_id());

                    friends_recc.add(friend_recc);
                }
                adapter_recc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

//    private List<Users> getLimitedFriendRequests() {
//        if (isExpanded || friends_req.size() <= 5) {
//            return friends_req;
//        } else {
//            return new ArrayList<>(friends_req.subList(0, 5));
//        }
//    }
}