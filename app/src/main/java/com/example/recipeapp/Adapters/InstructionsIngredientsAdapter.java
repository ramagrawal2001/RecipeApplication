package com.example.recipeapp.Adapters;

import android.app.appsearch.PackageIdentifier;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.Models.Ingredient;
import com.example.recipeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstructionsIngredientsAdapter extends RecyclerView.Adapter<InstructionsIngredientsViewHolder>{

    Context context;
    List<Ingredient> list;

    public InstructionsIngredientsAdapter(Context context, List<Ingredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionsIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsIngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_istruction_step_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsIngredientsViewHolder holder, int position) {
        holder.tv_instructions_step_item.setText(list.get(position).name);
        holder.tv_instructions_step_item.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+list.get(position).image).into(holder.iv_instructions_step_items);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class InstructionsIngredientsViewHolder extends RecyclerView.ViewHolder{

    TextView tv_instructions_step_item;
    ImageView iv_instructions_step_items;

    public InstructionsIngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_instructions_step_item=itemView.findViewById(R.id.tv_instructions_step_item);
        iv_instructions_step_items=itemView.findViewById(R.id.iv_instructions_step_items);
    }
}