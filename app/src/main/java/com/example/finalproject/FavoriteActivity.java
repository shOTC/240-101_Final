package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private Favorites fav;
    private List<Recipe> display = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        fav = new Favorites(this);

        loadRecipes();
    }

    public void favoriteToMain(View v){
        finish();
    }

    private void displayRecipes() {
        LinearLayout recipeLayout = findViewById(R.id.recipeLayout);
        int listItemCount = recipeLayout.getChildCount();

        for (int i = 0; i < listItemCount; i++) {
            recipeLayout.removeView(recipeLayout.getChildAt(i));
        }

        for (Recipe x : display) {
            TextView listItem = new TextView(this);
            listItem.setText(x.getName());
            listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            listItem.setOnClickListener(v -> {
                SharedPreferences prefs = getSharedPreferences("recipeData", MODE_PRIVATE);
                SharedPreferences.Editor edit = prefs.edit();

                edit.putString("recipeName", x.getName());
                edit.putString("recipeDescription", x.getDescription());
                edit.putString("recipeInstructions", x.getInstructions());
                edit.putInt("image", x.getImage());
                edit.putStringSet("recipeKeywords", x.getKeywords());
                edit.putStringSet("recipeCategories", x.getCategories());
                edit.putBoolean("isFavorite", true);
                edit.apply();

                startActivity(new Intent(FavoriteActivity.this, RecipeActivity.class));
                finish();
            });
            recipeLayout.addView(listItem);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            fav.load();
        }
        catch (IOException exception) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            fav.save();
        }
        catch (IOException exception) {

        }
    }

    private void loadRecipes() {
        display.clear();
        display.addAll(Favorites.getFavorites());
        displayRecipes();
    }
}
