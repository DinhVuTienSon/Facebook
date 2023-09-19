package vn.edu.usth.facebook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.edu.usth.facebook.model.Friends;
import vn.edu.usth.facebook.model.Profile_menu;

public class FbMenuFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Profile_menu> profile_menus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fb_menu, container, false);

        profile_menus = new ArrayList<>();
        String profile_menu_ava = "https://picsum.photos/600/300?random&";
        String profile_menu_name = "ST";

        Profile_menu profile_menu = new Profile_menu(profile_menu_ava, profile_menu_name);
        profile_menus.add(profile_menu);

        return view;
    }
}

// not finished file