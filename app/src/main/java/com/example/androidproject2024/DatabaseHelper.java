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
    private static final int DATABASE_VERSION = 2;
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
        recipes.add(new Recipe("Spaghetti Carbonara", R.drawable.sCarbonara, "Cook pasta, fry pancetta with garlic, and mix with eggs and parmesan."));
        recipes.add(new Recipe("Spaghetti Bolognese", R.drawable.sBolognese, "Cook spaghetti and serve with a hearty meat sauce."));
        recipes.add(new Recipe("Spaghetti Aglio e Olio", R.drawable.sAglioOlio, "Sauté garlic in olive oil, add red pepper flakes, and toss with spaghetti."));
        recipes.add(new Recipe("Spaghetti al Pomodoro", R.drawable.sPomodoro, "Simple tomato sauce with fresh basil served over spaghetti."));
        recipes.add(new Recipe("Spaghetti alla Puttanesca", R.drawable.sPuttanesca, "Tomato sauce with olives, anchovies, and capers."));
        recipes.add(new Recipe("Spaghetti Cacio e Pepe", R.drawable.sCacioPepe, "Cook spaghetti, and toss with Pecorino Romano cheese and black pepper."));
        recipes.add(new Recipe("Spaghetti alla Norma", R.drawable.sAllaNorma, "Serve spaghetti with a sauce of tomatoes, fried eggplant, basil, and ricotta salata."));
        recipes.add(new Recipe("Spaghetti with Clam Sauce", R.drawable.sClam, "Cook spaghetti and toss with a sauce of clams, garlic, parsley, and white wine."));
        recipes.add(new Recipe("Spaghetti al Limone", R.drawable.sLimone, "Lemon zest and juice blended with cream, Parmesan, and pasta water for a creamy citrus sauce over spaghetti."));
        recipes.add(new Recipe("Spaghetti Primavera", R.drawable.sPrimavera, "Toss spaghetti with sautéed seasonal vegetables, garlic, and Parmesan."));
        recipes.add(new Recipe("Spaghetti alla Gricia", R.drawable.sGricia, "Cook spaghetti and combine with guanciale, Pecorino Romano cheese, and plenty of black pepper."));
        recipes.add(new Recipe("Garlic Shrimp Spaghetti", R.drawable.sShrimp, "Sauté shrimp with garlic, parsley, and chili, then toss with spaghetti."));
        recipes.add(new Recipe("Spaghetti with Meatballs", R.drawable.sMeatball, "Serve spaghetti with homemade meatballs simmered in marinara sauce."));
        recipes.add(new Recipe("Spaghetti al Nero di Seppia", R.drawable.sSeppia, "Spaghetti cooked with cuttlefish ink, seafood, garlic, and white wine."));
        recipes.add(new Recipe("Creamy Mushroom Spaghetti", R.drawable.sMushroom, "Sauté mushrooms and mix with a creamy sauce served over spaghetti."));


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
