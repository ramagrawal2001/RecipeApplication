package com.example.recipeapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.Listners.RecipeClickListener;
import com.example.recipeapp.Models.Recipe;
import com.example.recipeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder> {

    Context context;
    List<Recipe> list;
    RecipeClickListener listener;

    public RandomRecipeAdapter(Context context, List<Recipe> list,RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener=listener;
    }

    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        holder.tv_title.setText(list.get(position).title);
        holder.tv_title.setSelected(true);
        holder.tv_likes.setText(list.get(position).aggregateLikes+" Likes");
        holder.tv_servings.setText(list.get(position).servings+" Servings");
        holder.tv_time.setText(list.get(position).readyInMinutes+" Minutes");
        Picasso.get().load(list.get(position).image).into(holder.iv_food);

        holder.random_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecipeClicked(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class RandomRecipeViewHolder extends RecyclerView.ViewHolder {

    CardView random_list_container;
    TextView tv_title, tv_servings, tv_likes, tv_time;
    ImageView iv_food;

    public RandomRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        random_list_container = itemView.findViewById(R.id.random_list_container);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_servings = itemView.findViewById(R.id.tv_servings);
        tv_likes = itemView.findViewById(R.id.tv_likes);
        tv_time = itemView.findViewById(R.id.tv_time);
        iv_food = itemView.findViewById(R.id.iv_food);
    }
}
