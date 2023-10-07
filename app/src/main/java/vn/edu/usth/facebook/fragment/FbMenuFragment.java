package vn.edu.usth.facebook.fragment;

import android.net.Uri;
import android.os.Bundle;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import android.view.LayoutInflater;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.LoginActivity;
import vn.edu.usth.facebook.model.Users;

//TODO: function to call current user ava, name


public class FbMenuFragment extends Fragment {
    private CircleImageView profile_menu_ava;
    private TextView profile_menu_name;
    private String TAG = "MENU GRAG";
    private FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fb_menu, container, false);

        Button logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(view1 -> {
            // Log out the user from Firebase
            FirebaseAuth.getInstance().signOut();

            // Show a message indicating that the user is logging out.
            Toast.makeText(getActivity(), "Logging out...", Toast.LENGTH_SHORT).show();

            // Delay for 2 seconds before navigating to the login screen
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Navigate to the login screen
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }, 2000);
        });
        getUserImg(mStorage.child("users").child(current_user.getUid()));

        profile_menu_name = view.findViewById(R.id.profile_menu_name);

        mDatabase.child("users").child(current_user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                profile_menu_name.setText(users.getFirst_name() + " " + users.getSur_name());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profile_menu_ava = view.findViewById(R.id.profile_menu_ava);
        profile_menu_ava.setOnClickListener(view12 -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, new ProfileFragment());
            fragmentTransaction.addToBackStack("profile");
            fragmentTransaction.commit();
        });

        return view;
    }

    public void getUserImg(StorageReference user_storage){
//        todo: threading
        user_storage.child("avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profile_menu_ava);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Picasso.get().load(R.drawable.default_ava).into(profile_menu_ava);
                Log.e(TAG,"GET AVA/BANNER IMG ERROR: SKIPPP");
            }
        });
    }
}