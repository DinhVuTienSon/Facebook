package vn.edu.usth.facebook.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.model.Profile_menu;

public class FbMenuFragment extends Fragment{
    private CircleImageView profile_menu_ava;
    private RecyclerView recyclerView;
    private ArrayList<Profile_menu> profile_menus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fb_menu, container, false);

        profile_menu_ava = view.findViewById(R.id.profile_menu_ava);
        profile_menu_ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new ProfileFragment());
                fragmentTransaction.addToBackStack("profile");
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}

