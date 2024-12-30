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
    private static final int DATABASE_VERSION = 4;
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

        recipes.add(new Recipe("Spaghetti Bolognese", R.drawable.s_bolognese, "Start by bringing a large pot of salted water to a boil. Add spaghetti and cook according to the package instructions until al dente. Meanwhile, heat a few tablespoons of olive oil in a large skillet over medium heat. Add finely chopped onions and minced garlic, sautéing until the onions are translucent. Introduce ground beef to the skillet, breaking it apart with a spoon, and cook until it's fully browned and no pink remains.\n" +
                "\n" +
                "Deglaze the skillet with a splash of red wine, letting it simmer until the wine has reduced slightly. Stir in crushed tomatoes and a generous amount of tomato paste. Season the sauce with salt, freshly ground black pepper, dried basil, and oregano, allowing it to simmer for about 20-30 minutes until thickened. Once the spaghetti is cooked and drained, toss it with the meat sauce to combine.\n" +
                "\n" +
                "Serve the Spaghetti Bolognese hot, topped with a sprinkle of freshly grated Parmesan cheese and a few basil leaves for garnish. This dish brings the hearty and comforting flavors of a traditional Italian meal to your table."));        recipes.add(new Recipe("Spaghetti Aglio e Olio", R.drawable.s_aglio_olio, "Sauté garlic in olive oil, add red pepper flakes, and toss with spaghetti."));

        recipes.add(new Recipe("Spaghetti Aglio e Olio", R.drawable.s_aglio_olio, "Heat a generous amount of olive oil in a large pan over medium heat. Add thinly sliced garlic and cook until golden and fragrant. Sprinkle in red pepper flakes for a bit of heat. Cook the spaghetti in salted boiling water until al dente. Drain the spaghetti, reserving some of the pasta water. Toss the cooked spaghetti in the garlic oil, adding pasta water as needed to emulsify the sauce. Serve immediately, garnished with chopped parsley and grated Parmesan if desired."));

        recipes.add(new Recipe("Spaghetti al Pomodoro", R.drawable.s_pomodoro, "Begin by heating olive oil in a saucepan. Add minced garlic and sauté until fragrant. Pour in crushed tomatoes, season with salt, and simmer for 15-20 minutes to let the flavors meld. Near the end of cooking, stir in freshly torn basil leaves. Meanwhile, cook spaghetti to al dente, drain, and toss with the tomato sauce. Serve garnished with more fresh basil and a sprinkling of Parmesan cheese."));

        recipes.add(new Recipe("Spaghetti alla Puttanesca", R.drawable.s_puttanesca, "In a skillet, heat olive oil over medium heat. Add chopped anchovies, minced garlic, and capers, cooking until the anchovies melt into the oil. Stir in diced tomatoes, olives, and a pinch of red pepper flakes, simmering the sauce until it thickens. Cook spaghetti al dente, drain, and toss with the sauce. Finish with chopped parsley and a drizzle of extra virgin olive oil."));

        recipes.add(new Recipe("Spaghetti Cacio e Pepe", R.drawable.s_caciopepe, "Cook spaghetti in salted boiling water until al dente. In a large bowl, combine finely grated Pecorino Romano cheese with freshly ground black pepper and a little pasta water to create a creamy sauce. Drain the pasta and immediately toss it in the cheese mixture, adding more pasta water if necessary to achieve a silky texture. Serve immediately with additional cheese and pepper."));

        recipes.add(new Recipe("Spaghetti alla Norma", R.drawable.s_allanorma, "Fry cubes of eggplant in olive oil until golden and set aside. In the same pan, add chopped garlic and chili flakes, cooking until aromatic. Pour in crushed tomatoes and simmer to develop the flavors. Add the fried eggplant back into the sauce. Cook spaghetti to al dente, drain, and toss with the sauce. Garnish with torn basil leaves and crumbled ricotta salata before serving."));

        recipes.add(new Recipe("Spaghetti with Clam Sauce", R.drawable.s_clam, "In a skillet, cook minced garlic in olive oil until fragrant. Add clams and pour in white wine, covering the skillet to steam the clams open. Once opened, remove clams, reduce the sauce slightly, and then return clams to the pan. Cook spaghetti al dente, toss it with the clam sauce, and finish with chopped parsley and a squeeze of lemon juice."));

        recipes.add(new Recipe("Spaghetti al Limone", R.drawable.s_limone, "Zest and juice lemons, setting aside. Cook spaghetti in salted water to al dente. In a pan, warm cream and add lemon zest and a little pasta water. Once the spaghetti is cooked, drain and toss it in the cream sauce, adding lemon juice and grated Parmesan cheese to create a smooth sauce. Adjust seasoning and serve garnished with more zest and black pepper."));

        recipes.add(new Recipe("Spaghetti Primavera", R.drawable.s_primavera, "Sauté a mix of seasonal vegetables such as bell peppers, zucchini, and asparagus in olive oil with minced garlic until just tender. Season with salt and pepper. Cook spaghetti al dente, drain, and toss with the vegetables. Add grated Parmesan cheese and a splash of pasta water to create a light sauce that coats the vegetables and pasta evenly. Serve immediately."));

        recipes.add(new Recipe("Spaghetti alla Gricia", R.drawable.s_gricia, "Render diced guanciale in a skillet until crispy. Remove guanciale and to the fat, add cooked spaghetti along with a splash of pasta water. Toss with grated Pecorino Romano cheese and a generous amount of cracked black pepper. Serve hot with extra cheese on top."));

        recipes.add(new Recipe("Garlic Shrimp Spaghetti", R.drawable.s_shrimp, "Sauté peeled shrimp with minced garlic and chili flakes in olive oil until the shrimp are pink and cooked through. Deglaze the pan with a splash of white wine. Cook spaghetti al dente, drain, and toss with the shrimp and a handful of chopped parsley. Serve immediately."));

        recipes.add(new Recipe("Spaghetti with Meatballs", R.drawable.s_meatball, "Prepare meatballs by mixing ground beef, breadcrumbs, Parmesan, and herbs, and bake until golden. Simmer marinara sauce in a saucepan. Add the cooked meatballs to the sauce and let simmer for a few minutes. Cook spaghetti al dente, drain, and serve topped with meatballs and sauce. Garnish with grated Parmesan and fresh basil."));

        recipes.add(new Recipe("Spaghetti al Nero di Seppia", R.drawable.s_seppia, "Cook spaghetti in a mixture of cuttlefish ink and salted water until al dente. In a skillet, sauté minced garlic, chopped seafood, and a little white wine until the seafood is cooked. Toss the cooked spaghetti with the seafood and finish with chopped parsley and a drizzle of olive oil."));

        recipes.add(new Recipe("Creamy Mushroom Spaghetti", R.drawable.s_mushroom, "Sauté sliced mushrooms in butter until golden. Add minced garlic and cook until fragrant. Deglaze the pan with a splash of white wine and add cream, letting the sauce thicken slightly. Cook spaghetti al dente, drain, and toss with the mushroom sauce. Finish with grated Parmesan cheese and freshly ground black pepper."));

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
