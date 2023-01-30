package com.shahin.techbrandeshop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.shahin.techbrandeshop.dao.ProductDAO;
import com.shahin.techbrandeshop.entity.Product;
import com.shahin.techbrandeshop.util.ImageTypeConverter;


@Database(entities = {Product.class}, version = 1,exportSchema = false)
@TypeConverters(ImageTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "HMal_DB";
    private static AppDatabase INSTANCE;
    public abstract ProductDAO productDAO();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DB_NAME).allowMainThreadQueries().build();
        return INSTANCE;
    }

    public static void closeConnection() {
        if (INSTANCE != null) INSTANCE.close();
    }
}