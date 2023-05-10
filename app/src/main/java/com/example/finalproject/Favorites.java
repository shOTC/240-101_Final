package com.example.finalproject;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Favorites {
    public static final String saveFile = "favorites.txt";
    private Context context;
    private static List<Recipe> saveData = new ArrayList<>();

    public Favorites(Context context)
    {
        this.context = context;
    }

    public static void addFavorite(Recipe in)
    {
        saveData.add(in);
    }

    public static void removeFavorite(String name)
    {
        Recipe remove = null;
        for (Recipe x : saveData){
            if (Objects.equals(x.getName(), name)){
                remove = x;
            }
        }
        saveData.remove(remove);
    }

    public static List<Recipe> getFavorites()
    {
        return saveData;
    }

    public void save() throws IOException
    {
        Gson gson = new Gson();
        String json = gson.toJson(saveData);

        FileOutputStream outputStream = context.openFileOutput(saveFile, Context.MODE_PRIVATE);

        PrintWriter writer = new PrintWriter(outputStream);
        writer.println(json);
        writer.close();
    }

    public void load() throws IOException
    {
        FileInputStream inputStream = context.openFileInput(saveFile);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
        {
            saveData.clear();

            int fileSize = inputStream.available();
            char[] fileOutput = new char[fileSize];

            reader.read(fileOutput);

            Gson gson = new Gson();

            saveData = gson.fromJson(new String(fileOutput), new TypeToken<List<Recipe>>(){});
        }
        catch (FileNotFoundException exception)
        {

        }
    }
}
