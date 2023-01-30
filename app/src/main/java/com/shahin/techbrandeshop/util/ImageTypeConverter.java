package com.shahin.techbrandeshop.util;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ImageTypeConverter {
    @TypeConverter
    public String IntToJson(List<String> imageResources) {
        return new Gson().toJson(imageResources);
    }

    @TypeConverter
    public List<String> fromJson(String json) {
        Type type = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(json, type);
    }
}