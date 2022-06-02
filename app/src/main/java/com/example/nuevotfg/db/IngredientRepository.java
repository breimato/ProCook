package com.example.nuevotfg.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class IngredientRepository {
    private IngredientDao mIngredientDao;
    private LiveData<List<Ingredient>> mAllIngredients;

    // Note that in order to unit test the IngredientRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public IngredientRepository(Application application) {
        IngredientRoomDatabase db = IngredientRoomDatabase.getDatabase(application);
        mIngredientDao = db.ingredientDao();
        mAllIngredients = mIngredientDao.getAlphabetizedIngredients();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Ingredient>> getAllIngredients() {
        return mAllIngredients;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Ingredient ingredient) {
        IngredientRoomDatabase.databaseWriteExecutor.execute(() -> {
            mIngredientDao.insert(ingredient);
        });
    }
}
