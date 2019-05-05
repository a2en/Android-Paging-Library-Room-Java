package io.github.a2en.androidpagingjava.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import io.github.a2en.androidpagingjava.model.Repo;

@Database(entities = {Repo.class}, version = 1, exportSchema = false)
public abstract class RepoDatabase extends RoomDatabase {

    private static  RepoDatabase INSTANCE;

    public abstract RepoDao reposDao();


    public static synchronized RepoDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RepoDatabase.class, "Github.db")
                    .build();
        }
        return INSTANCE;
    }
}
