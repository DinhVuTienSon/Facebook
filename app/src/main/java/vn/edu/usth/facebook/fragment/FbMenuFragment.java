package vn.edu.usth.facebook.fragment;

import android.os.Bundle;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;
import android.widget.Button;

import android.view.LayoutInflater;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import com.google.firebase.auth.FirebaseAuth;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.LoginActivity;
import vn.edu.usth.facebook.model.Profile_menu;

//TODO: function to call current user ava, name


public class FbMenuFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Profile_menu> profile_menus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fb_menu, container, false);

        Button logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(view1 -> {
            // Log out the user from Firebase
            FirebaseAuth.getInstance().signOut();

            // Show a message indicating that the user is logging out
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

        CircleImageView profile_menu_ava = view.findViewById(R.id.profile_menu_ava);
        profile_menu_ava.setOnClickListener(view12 -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, new ProfileFragment());
            fragmentTransaction.addToBackStack("profile");
            fragmentTransaction.commit();
        });

        return view;
    }
}
