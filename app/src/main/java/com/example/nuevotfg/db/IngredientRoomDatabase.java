package com.example.nuevotfg.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ingredient.class}, version = 1)
public abstract class IngredientRoomDatabase extends RoomDatabase {

    public abstract IngredientDao ingredientDao();



    private static volatile IngredientRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static IngredientRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (IngredientRoomDatabase.class) {
                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            IngredientRoomDatabase.class, "app_database").addCallback(sRoomDatabaseCallback).build();
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            IngredientRoomDatabase.class, "app_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//
//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//
//            // If you want to keep data through app restarts,
//            // comment out the following block
//            databaseWriteExecutor.execute(() -> {
//                // Populate the database in the background.
//                // If you want to start with more ingredients, just add them.
//                IngredientDao dao = INSTANCE.ingredientDao();
//
//                Ingredient ingredient = new Ingredient("Hello");
//                dao.insert(ingredient);
//                ingredient = new Ingredient("World");
//                dao.insert(ingredient);
//            });
//        }
//    };
}
