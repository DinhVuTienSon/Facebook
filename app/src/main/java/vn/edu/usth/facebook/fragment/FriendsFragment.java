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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

    private List<String> requests;
    private List<String> not_friends;

    private AppCompatButton see_all_friend_req, see_less_friend_req;
    private FriendsAdapter adapter;
    private FriendsRecommendAdapter adapter_recc;

    private FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private boolean isExpanded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        requests = new ArrayList<>();
        not_friends = new ArrayList<>();

//        FRIENDS REQUEST

        see_all_friend_req = view.findViewById(R.id.see_all_friend_req);
        see_less_friend_req = view.findViewById(R.id.see_less_friend_req);

        friends_req = new ArrayList<>();
        if (friends_req.size() <= 5) {
            see_all_friend_req.setVisibility(View.GONE);
        } else {
            see_all_friend_req.setVisibility(View.VISIBLE);
        }

        adapter = new FriendsAdapter(friends_req, getContext());
        recycler_view_req = view.findViewById(R.id.friend_request_recyclerView);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setStackFromEnd(true);// -> latest friend first
        layoutManager2.setReverseLayout(true);
        recycler_view_req.setLayoutManager(layoutManager2);
        recycler_view_req.setAdapter(adapter);

        filter_friend_req();

        see_all_friend_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpanded = true;
                adapter.setData(friends_req);
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
        filter_friend_rec();

        return view;
    }



    public void getFriendRecommend(){

        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friends_recc.clear();
                Log.i(TAG,"NOT FRIENDS:" + not_friends);
                for (DataSnapshot sp : snapshot.getChildren()) {
                    Users friend_recc = snapshot.getValue(Users.class);
                    for (String id : not_friends){
                        Log.i(TAG, "SP KEY: " + sp.getKey());
                        if(sp.getKey().equals(id)){
                            friend_recc.setUser_id(sp.getKey());
                            friends_recc.add(friend_recc);
                        }
                    }
                }
                adapter_recc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
        public void getFriendRequest() {
            if(mDatabase.child("users").child(current_user.getUid()).child("friend_reqs") != null){
                mDatabase.child("users").child(current_user.getUid()).child("friend_reqs").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        friends_req.clear();
                        for (DataSnapshot sp : snapshot.getChildren()) {
                            Users friend_req = snapshot.getValue(Users.class);
                            Log.i(TAG, "DATABASE FRIENDS CHECK: " + sp.getKey());
                            for (String id : requests) {
                                if (sp.getKey().equals(id)) {
                                    friend_req.setUser_id(sp.getKey());

                                    friends_req.add(friend_req);
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
            else {
            }
    }

    private List<Users> getLimitedFriendRequests() {
        if (isExpanded || friends_req.size() <= 5) {
            return friends_req;
        } else {
            return new ArrayList<>(friends_req.subList(0, 5));
        }
    }

    private void filter_friend_req(){
        mDatabase.child("users").child(current_user.getUid()).child("friend_reqs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requests.clear();
                for(DataSnapshot sp : snapshot.getChildren()){
                    requests.add(sp.getKey());
                }
                getFriendRequest();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filter_friend_rec(){
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                not_friends.clear();
                for(DataSnapshot sp : snapshot.getChildren()){
                    not_friends.add(sp.getKey());
                }
                Log.i(TAG,"USERS ALL:" + not_friends);
                for(DataSnapshot ss : snapshot.child(current_user.getUid()).child("friends").getChildren()){
                    not_friends.remove(ss.getKey());
                    Log.i(TAG,"SS KEY(ALL FRIENDS): " + ss.getKey());
                }
                not_friends.remove(current_user.getUid());

                getFriendRecommend();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}