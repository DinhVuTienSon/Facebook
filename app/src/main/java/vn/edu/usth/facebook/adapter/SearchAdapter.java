package vn.edu.usth.facebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.model.Search;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Search> search;
    public SearchAdapter(ArrayList<Search> search, Context context){
        this.search = search;
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
        Search searches = search.get(position);
        Picasso.get().load(searches.getSearch_ava()).into(holder.search_ava);
        holder.search_name.setText(searches.getSearch_name());
        holder.search_info.setText(searches.getSearch_info());
    }

    @Override
    public int getItemCount() {
        return search.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView search_ava;
        private TextView search_name, search_info;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            search_ava = itemView.findViewById(R.id.search_ava);
            search_name = itemView.findViewById(R.id.search_name);
            search_info = itemView.findViewById(R.id.search_info);

        }
    }
}
