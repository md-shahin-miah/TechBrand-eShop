package com.shahin.techbrandeshop.util;

import android.content.Context;

public class ImageConverterHelper {
    public static int getResourceIdFromString(Context context, String drawableString) {
        String temp = drawableString.replaceAll("R.drawable.", "");
        return context.getResources().getIdentifier(temp, "drawable", context.getPackageName());
    }
}