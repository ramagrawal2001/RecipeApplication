package com.example.recipeapp.Listners;

import com.example.recipeapp.Models.RecipeDetailResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailResponse response,String message);
    void didError(String message);
}
