package com.example.finalproject;

import java.util.Objects;
import java.util.Set;

public class Recipe {
    private Set<String> keywords;
    private Set<String> category;

    private String name;
    private String description;
    private String instructions;
    private int image;

    public Recipe(Set<String> keywords, Set<String> category, String name, String description, String instructions, int image)
    {
        this.keywords = keywords;
        this.category = category;
        this.name = name;
        this.description = description;
        this.instructions = instructions;
        this.image = image;
    }

    public Recipe () {}

    public boolean checkKeywords(String input)
    {
        for (String x : keywords) {
            if (Objects.equals(x, input)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCategories(String input){
        for (String x: category)
        {
            if (Objects.equals(x, input))
            {
                return true;
            }
        }
        return false;
    }

    public Set<String> getKeywords()
    {
        return keywords;
    }

    public Set<String> getCategories()
    {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getImage() {
        return image;
    }
}
