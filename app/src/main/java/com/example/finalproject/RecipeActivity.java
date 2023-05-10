package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class RecipeActivity extends AppCompatActivity {
    private Favorites fav;
    private boolean favorite;
    private boolean favoriteStart;
    private String name;
    private String description;
    private String instructions;
    private int image;
    private Set<String> keywords;
    private Set<String> categories;
    private ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        fav = new Favorites(this);

        TextView title = findViewById(R.id.recipeTitle);
        TextView desc = findViewById(R.id.recipeDescription);
        TextView inst = findViewById(R.id.recipeInstructions);
        TextView cat = findViewById(R.id.recipeCategories);
        TextView key = findViewById(R.id.recipeKeywords);
        ImageView img = findViewById(R.id.imageView);
        button = findViewById(R.id.imageButton);

        SharedPreferences prefs = getSharedPreferences("recipeData", MODE_PRIVATE);
        name = prefs.getString("recipeName", "");
        description = prefs.getString("recipeDescription", "");
        instructions = prefs.getString("recipeInstructions", "");
        image = prefs.getInt("image", R.drawable.baseline_search_24);
        keywords = prefs.getStringSet("recipeKeywords", Collections.singleton(("")));
        categories = prefs.getStringSet("recipeCategories", Collections.singleton(("")));
        favorite = prefs.getBoolean("isFavorite", false);
        favoriteStart = favorite;

        if (favorite) {
            button.setImageResource(R.drawable.baseline_bookmark_remove_24);
        }
        else {
            button.setImageResource(R.drawable.baseline_bookmark_add_24);
        }

        StringBuilder recipeCategories = new StringBuilder("Categories:");
        StringBuilder recipeKeywords = new StringBuilder();

        for (String x : categories) {
            recipeCategories.append(" " + x);
        }

        for (String x : keywords) {
            recipeKeywords.append(" #" + x);
        }

        title.setText(name);
        desc.setText(description);
        inst.setText(instructions);
        img.setImageResource(image);
        cat.setText(recipeCategories);
        key.setText(recipeKeywords);
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

    public void recipeToMain(View v) {
        if (favoriteStart){
            startActivity(new Intent(RecipeActivity.this, FavoriteActivity.class));
            finish();
        }
        else{
            finish();
        }
    }

    public void setFavorite(View v) {
        if (favorite) {
            Favorites.removeFavorite(name);
            favorite = false;
            button.setImageResource(R.drawable.baseline_bookmark_add_24);
        }
        else {
            Favorites.addFavorite(new Recipe(
                    keywords,
                    categories,
                    name,
                    description,
                    instructions,
                    image
            ));
            favorite = true;
            button.setImageResource(R.drawable.baseline_bookmark_remove_24);
        }
    }
}
