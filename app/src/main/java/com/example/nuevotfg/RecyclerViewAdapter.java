package com.example.nuevotfg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<Result> dataset;

    private Context context;
    public RecyclerViewAdapter(Context context){
        this.context = context;
        dataset = new ArrayList<>();
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Result r = dataset.get(position);
        holder.tvTitle.setText(r.getTitle());
        Glide.with(context)
                .load("https://spoonacular.com/recipeImages/"+r.getNumber())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addFoodList(Recipe r) {
        dataset.addAll(r.results);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView fotoImageView;
        private TextView tvTitle;

        public ViewHolder (View view){
            super(view);
            fotoImageView = (ImageView) itemView.findViewById(R.id.imageViewApi);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
}
