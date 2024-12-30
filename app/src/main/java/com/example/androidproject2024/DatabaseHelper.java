package com.example.androidproject2024;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RecipeDatabase";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_RECIPES = "recipes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_INSTRUCTIONS = "instructions";
    private static final String COLUMN_IMAGE_RESOURCE_ID = "imageResourceId";


    private static final String CREATE_TABLE_RECIPES = "CREATE TABLE " + TABLE_RECIPES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_INSTRUCTIONS + " TEXT,"
            + COLUMN_IMAGE_RESOURCE_ID + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RECIPES);
        insertInitialRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    private void insertInitialRecipes(SQLiteDatabase db) {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Spaghetti Carbonara", R.drawable.s_carbonara, "To create a delicious pasta dish, start by bringing a large pot of salted water to a boil. Add your choice of pasta and cook according to the package instructions until al dente. While the pasta is cooking, cut pancetta into small cubes and fry in a deep skillet over medium heat until crispy. Add minced garlic to the pancetta during the last few minutes of cooking, taking care not to burn the garlic.\n" +
                "\n" +
                "In a separate bowl, beat together eggs and freshly grated Parmesan cheese, seasoning with black pepper to taste. Once the pasta is cooked, reserve a cup of pasta water and then drain the pasta. Quickly add the hot pasta to the pancetta and garlic in the skillet, removing the skillet from heat to prevent the eggs from scrambling.\n" +
                "\n" +
                "Pour the egg and cheese mixture over the pasta, tossing vigorously to coat the pasta evenly and create a creamy sauce. Add reserved pasta water a little at a time if the sauce is too thick. Serve immediately, garnishing with extra Parmesan and a sprinkle of freshly cracked black pepper."));

        recipes.add(new Recipe("Spaghetti Bolognese", R.drawable.s_bolognese, "Cook spaghetti and serve with a hearty meat sauce.bblla blla bllaa blaa bllaa "));
        recipes.add(new Recipe("Spaghetti Aglio e Olio", R.drawable.s_aglio_olio, "Sauté garlic in olive oil, add red pepper flakes, and toss with spaghetti."));
        recipes.add(new Recipe("Spaghetti al Pomodoro", R.drawable.s_pomodoro, "Simple tomato sauce with fresh basil served over spaghetti."));
        recipes.add(new Recipe("Spaghetti alla Puttanesca", R.drawable.s_puttanesca, "Tomato sauce with olives, anchovies, and capers."));
        recipes.add(new Recipe("Spaghetti Cacio e Pepe", R.drawable.s_caciopepe, "Cook spaghetti, and toss with Pecorino Romano cheese and black pepper."));
        recipes.add(new Recipe("Spaghetti alla Norma", R.drawable.s_allanorma, "Serve spaghetti with a sauce of tomatoes, fried eggplant, basil, and ricotta salata."));
        recipes.add(new Recipe("Spaghetti with Clam Sauce", R.drawable.s_clam, "Cook spaghetti and toss with a sauce of clams, garlic, parsley, and white wine."));
        recipes.add(new Recipe("Spaghetti al Limone", R.drawable.s_limone, "Lemon zest and juice blended with cream, Parmesan, and pasta water for a creamy citrus sauce over spaghetti."));
        recipes.add(new Recipe("Spaghetti Primavera", R.drawable.s_primavera, "Toss spaghetti with sautéed seasonal vegetables, garlic, and Parmesan."));
        recipes.add(new Recipe("Spaghetti alla Gricia", R.drawable.s_gricia, "Cook spaghetti and combine with guanciale, Pecorino Romano cheese, and plenty of black pepper."));
        recipes.add(new Recipe("Garlic Shrimp Spaghetti", R.drawable.s_shrimp, "Sauté shrimp with garlic, parsley, and chili, then toss with spaghetti."));
        recipes.add(new Recipe("Spaghetti with Meatballs", R.drawable.s_meatball, "Serve spaghetti with homemade meatballs simmered in marinara sauce."));
        recipes.add(new Recipe("Spaghetti al Nero di Seppia", R.drawable.s_seppia, "Spaghetti cooked with cuttlefish ink, seafood, garlic, and white wine."));
        recipes.add(new Recipe("Creamy Mushroom Spaghetti", R.drawable.s_mushroom, "Sauté mushrooms and mix with a creamy sauce served over spaghetti."));


        for (Recipe recipe : recipes) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, recipe.getName());
            values.put(COLUMN_INSTRUCTIONS, recipe.getInstructions());
            values.put(COLUMN_IMAGE_RESOURCE_ID, recipe.getImageResourceId());
            db.insert(TABLE_RECIPES, null, values);
        }
    }


    @SuppressLint("Range")
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPES, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_INSTRUCTIONS, COLUMN_IMAGE_RESOURCE_ID}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                recipes.add(new Recipe(
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_IMAGE_RESOURCE_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_INSTRUCTIONS))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recipes;
    }
}
