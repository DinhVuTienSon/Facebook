package vn.edu.usth.facebook;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.model.Friends;
import vn.edu.usth.facebook.model.Profile_menu;

// not used file

public class ProfileMenuAdapter extends RecyclerView.Adapter<ProfileMenuAdapter.ViewHolder>{

    private ArrayList<Profile_menu> profile_menus;
    private FbMenuFragment context;

    public ProfileMenuAdapter(ArrayList<Profile_menu> profile_menus, FbMenuFragment context){
        this.profile_menus = profile_menus;
        this.context = context;
    }
    @NonNull
    @Override
    public ProfileMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_fb_menu, parent, false);
        return new ProfileMenuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileMenuAdapter.ViewHolder holder, int position) {
        Profile_menu profile_menu = profile_menus.get(position);
        Picasso.get().load(profile_menu.getProfile_menu_ava()).into(holder.profile_menu_ava);
        holder.profile_menu_name_holder.setText(profile_menu.getProfile_menu_name());

        holder.profile_menu_ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new ProfileFragment());
                fragmentTransaction.addToBackStack(null); // Optional: Add to back stack
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return profile_menus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView profile_menu_ava;
        private TextView profile_menu_name_holder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_menu_ava = itemView.findViewById(R.id.profile_menu_ava);
            profile_menu_name_holder = itemView.findViewById(R.id.profile_menu_name);

        }
    }

}
