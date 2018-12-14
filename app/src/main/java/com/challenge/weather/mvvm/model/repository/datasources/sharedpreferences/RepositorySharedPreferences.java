package com.challenge.weather.mvvm.model.repository.datasources.sharedpreferences;

import android.content.Context;
import com.challenge.weather.mvvm.model.repository.RepositoryCache;

import java.io.File;

import javax.inject.Inject;

public class RepositorySharedPreferences {

    private Context context;
    private RepositoryCache repositoryCache;
    private static final Object LOCK = new Object();

    @Inject
    public RepositorySharedPreferences(Context context, RepositoryCache repositoryCache) {
        this.context = context;
        this.repositoryCache = repositoryCache;
    }

    public SharedPreferencesOperations getSharedPreferencesOperations(String sharedPreferencesName) {
        SharedPreferencesOperations sharedPreferencesOperations;
        synchronized (LOCK) {
            sharedPreferencesOperations = repositoryCache.getSharedPreferencesFromCache(sharedPreferencesName);
            if (sharedPreferencesOperations == null) {
                sharedPreferencesOperations = createSharedPreferencesOperations(sharedPreferencesName);
                repositoryCache.storeSharedPreferencesInCache(sharedPreferencesName, sharedPreferencesOperations);
            }
        }
        return sharedPreferencesOperations;
    }

    public void deleteAllSharedPreferences() {
        File sharedPreferenceFile = new File(context.getFilesDir().getParent() + "/shared_prefs/");
        File[] listFiles = sharedPreferenceFile.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file : listFiles) {
                file.delete();
            }
        }
    }

    private SharedPreferencesOperations createSharedPreferencesOperations(String sharedPreferencesName) {
        return new SharedPreferencesOperations(context, sharedPreferencesName);
    }
}
