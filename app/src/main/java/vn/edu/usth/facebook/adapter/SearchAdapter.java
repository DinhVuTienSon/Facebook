package vn.edu.usth.facebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.model.Users;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Users> users;
    public SearchAdapter(ArrayList<Users> users, Context context){
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_container, parent, false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        Users user = users.get(position);
        Picasso.get().load(user.getUser_ava()).into(holder.search_ava);
        holder.search_name.setText(user.getFirst_name()+" "+user.getSur_name());
//        holder.search_info.setText(user.getSearch_info());
        holder.search_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.search_add_friend.setVisibility(View.GONE);
                holder.search_add_friend_text.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView search_ava;
        private TextView search_name, search_info, search_add_friend_text;
        private AppCompatButton search_add_friend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            search_ava = itemView.findViewById(R.id.search_ava);
            search_name = itemView.findViewById(R.id.search_name);
            search_info = itemView.findViewById(R.id.search_info);
            search_add_friend = itemView.findViewById(R.id.search_add_friend);
            search_add_friend_text = itemView.findViewById(R.id.search_add_friend_text);
        }
    }
}
