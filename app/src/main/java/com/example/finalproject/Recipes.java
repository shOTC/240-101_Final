package com.example.finalproject;

import android.util.ArraySet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Recipes {
    public static List<Recipe> recipeList = new ArrayList<>();

    //hard-coded recipes here
    public static void loadRecipeList(){
        recipeList.add(new Recipe(
                converter(new String[] {"Carbs", "Pasta", "Italian"}),
                Collections.singleton("Large"),
                "Spaghetti",
                "A classic meal!",
                "Boil the spaghetti until done, then serve!",
                R.drawable.baseline_fastfood_24
        ));
        recipeList.add(new Recipe(
                converter(new String[] {"Carbs", "Protein", "Veggies"}),
                Collections.singleton("Large"),
                "Burger",
                "People love burgers!",
                "Cook the patty, layer all the ingredients, then serve!",
                R.drawable.baseline_fastfood_24
        ));
        recipeList.add(new Recipe(
                converter(new String[] {"Side", "Salty"}),
                Collections.singleton("Small"),
                "Fries",
                "A fantastic side!",
                "Cut the potatoes, fry them until golden brown, then serve!",
                R.drawable.baseline_fastfood_24
        ));
        recipeList.add(new Recipe(
                converter(new String[] {"Diet", "Side"}),
                Collections.singleton("Medium"),
                "Salad",
                "A diet option!",
                "Throw together all your ingredients then serve!",
                R.drawable.baseline_fastfood_24
        ));
        recipeList.add(new Recipe(
                converter(new String[] {"Side", "Carbs"}),
                Collections.singleton("Small"),
                "Breadsticks",
                "A fantastic side!",
                "Form the dough, put it in the oven until done, then serve!",
                R.drawable.baseline_fastfood_24
        ));
    }

    public static Set<String> converter(String[] input) {
        Set<String> output = new ArraySet<>();
        output.addAll(Arrays.asList(input));
        return output;
    }
}
