package vn.edu.usth.facebook.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.edu.usth.facebook.EditProfileActivity;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.UploadPostActivity;
import vn.edu.usth.facebook.model.Users;

//TODO: function to call all user information
//TODO: function to display friend's ava, name
//TODO: function to display user's post

public class ProfileFragment extends Fragment {
//    for bug fixes and error messages
    private String TAG = "PROFILE FRAGMENT";
    public Button editBtn;

    private Toolbar toolbar;

//    firebase stuff
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    private String uid;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//  get database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

//  get current user ID
        FirebaseUser c_user = mAuth.getCurrentUser();
        uid = c_user.getUid();

//  check if user is logged in
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG,"onAuthStateChanged: sign_in: " + uid);
                }
                else{
                    Log.d(TAG, "signed out" + uid);
                }
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }

//    show data
//    TODO: check for initial delay when updating name
    public void showData(DataSnapshot ds){
//      get relative layout id(contain name, banner, ava, quote, no_friends
        RelativeLayout user_profile = getView().findViewById(R.id.user_profile);

        Users user = new Users();

//        get basic infos
        user.setFirst_name(ds.getValue(Users.class).getFirst_name());
        user.setSur_name(ds.getValue(Users.class).getSur_name());
        user.setEmail(ds.getValue(Users.class).getEmail());
        Log.i(TAG,"AAAAAAAAAAAAAAAAAAA" + user.getFirst_name());

//        display name(only name for now until delay is fixed)
        TextView user_name = user_profile.findViewById(R.id.name);
        user_name.setText(user.getFirst_name() + " " + user.getSur_name());

    }

//    when app start, check if user is still logged in
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        toolbar = view.findViewById(R.id.profile_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        activity.getSupportActionBar().setTitle("");


//  add user infos from database to fragment
//        TODO: check for initial delay when updating name
        mDatabase.addValueEventListener(new ValueEventListener() {
//            this method is called once with initial value
//                and again whenever data at this location is updated

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//            take a snapshot of the database and display that data
//                showData(snapshot);

//                check if snapshot of user ID exist
                if (snapshot.child("users").child(uid).exists()){
                    Log.i(TAG,"user exist: " + snapshot.child("users").child(uid).getKey());
                    showData(snapshot.child("users").child(uid));
                }
                else{
                    Log.i(TAG, "scam NOOOOOOOOOOOOOOOOOOOOOO");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });

        editBtn = view.findViewById(R.id.btnEdit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.fb_menu) {
            requireActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}