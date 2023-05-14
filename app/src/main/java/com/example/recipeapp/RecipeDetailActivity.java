package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipeapp.Adapters.IngredientsAdapter;
import com.example.recipeapp.Adapters.InstructionsAdapter;
import com.example.recipeapp.Adapters.SimilarRecipeAdapter;
import com.example.recipeapp.Listners.InstructionsListener;
import com.example.recipeapp.Listners.RecipeClickListener;
import com.example.recipeapp.Listners.RecipeDetailsListener;
import com.example.recipeapp.Listners.SimilarRecipesListener;
import com.example.recipeapp.Models.InstructionsResponse;
import com.example.recipeapp.Models.RecipeDetailResponse;
import com.example.recipeapp.Models.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {
    int id;
    TextView tv_meal_name,tv_meal_source, tv_meal_summary;
    ImageView iv_meal_image;
    RecyclerView recycler_meal_ingredients,recycler_meal_similar,recycler_meal_instructions;
    ProgressDialog dialog;
    RequestManager manager;
    IngredientsAdapter ingredientsAdapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionsAdapter instructionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        findViews();

        id=Integer.parseInt(getIntent().getStringExtra("id"));

        manager=new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener,id);
        manager.getSimilarRecipes(similarRecipesListener,id);
        manager.getInstructions(instructionsListener,id);

        dialog=new ProgressDialog(this);
        dialog.setTitle("Loading Details...");
        dialog.show();
    }

    private void findViews() {
        tv_meal_name=findViewById(R.id.tv_meal_name);
        tv_meal_source=findViewById(R.id.tv_meal_source);
        tv_meal_summary=findViewById(R.id.tv_meal_summary);
        iv_meal_image=findViewById(R.id.iv_meal_image);
        recycler_meal_ingredients=findViewById(R.id.recycler_meal_ingredients);
        recycler_meal_similar=findViewById(R.id.recycler_meal_similar);
        recycler_meal_instructions=findViewById(R.id.recycler_meal_instructions);
    }

    private final RecipeDetailsListener recipeDetailsListener=new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailResponse response, String message) {
            dialog.dismiss();
            tv_meal_name.setText(response.title);
            tv_meal_source.setText(response.sourceName);
            tv_meal_summary.setText(response.summary);
            Picasso.get().load(response.image).into(iv_meal_image);

            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
            ingredientsAdapter=new IngredientsAdapter(RecipeDetailActivity.this,response.extendedIngredients);
            recycler_meal_ingredients.setAdapter(ingredientsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final SimilarRecipesListener similarRecipesListener=new SimilarRecipesListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> responses, String message) {
            recycler_meal_similar.setHasFixedSize(true);
            recycler_meal_similar.setLayoutManager(new LinearLayoutManager(RecipeDetailActivity.this,LinearLayoutManager.HORIZONTAL,false));
            similarRecipeAdapter=new SimilarRecipeAdapter(RecipeDetailActivity.this,responses,recipeClickListener);
            recycler_meal_similar.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
    private final RecipeClickListener recipeClickListener=new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(RecipeDetailActivity.this,RecipeDetailActivity.class).putExtra("id",id));
        }
    };
    private final InstructionsListener instructionsListener=new InstructionsListener() {
        @Override
        public void didFetch(List<InstructionsResponse> response, String message) {
            recycler_meal_instructions.setHasFixedSize(true);
            recycler_meal_instructions.setLayoutManager(new LinearLayoutManager(RecipeDetailActivity.this,LinearLayoutManager.VERTICAL,false));
            instructionsAdapter=new InstructionsAdapter(RecipeDetailActivity.this,response);
            recycler_meal_instructions.setAdapter(instructionsAdapter);
        }

        @Override
        public void didError(String message) {

        }
    };
}