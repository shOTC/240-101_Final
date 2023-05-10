package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Favorites fav;
    private EditText search;
    private List<Recipe> display = new ArrayList<>();
    private Spinner category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fav = new Favorites(this);
        search = findViewById(R.id.keywordSearch);

        category = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });*/

        Recipes.loadRecipeList();

        loadRecipes();
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

    private void displayRecipes() {
        LinearLayout recipeLayout = findViewById(R.id.recipeLayout);

        recipeLayout.removeAllViews();

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
                edit.putBoolean("isFavorite", false);
                edit.apply();

                startActivity(new Intent(MainActivity.this, RecipeActivity.class));
            });
            recipeLayout.addView(listItem);
        }
    }

    private void loadRecipes() {
        display.clear();
        display.addAll(Recipes.recipeList);
        displayRecipes();
    }

    public void searchRecipes(View v) {
        display.clear();

        String spinnerInput = category.getSelectedItem().toString();

        if (!search.getText().toString().equals(getResources().getString(R.string.keywordDefault))) {
            for (Recipe x : Recipes.recipeList) {
                if (x.checkKeywords(search.getText().toString())) {
                    display.add(x);
                }
            }
            if (!spinnerInput.equals(getResources().getStringArray(R.array.categories)[0])) {
                display.removeIf(x -> !x.checkCategories(spinnerInput));
            }
        }
        else if (!spinnerInput.equals(getResources().getStringArray(R.array.categories)[0])) {
            for (Recipe x : Recipes.recipeList) {
                if (x.checkCategories(spinnerInput)) {
                    display.add(x);
                }
            }
        }
        else {
            loadRecipes();
        }

        displayRecipes();
    }

    public void clearSearch(View v) {
        search.setText(getResources().getText(R.string.keywordDefault));
        category.setSelection(0);
        loadRecipes();
    }

    public void goToFavorites(View v) {
        startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
    }
}