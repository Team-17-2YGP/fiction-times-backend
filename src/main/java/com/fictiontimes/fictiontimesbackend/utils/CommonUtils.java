package com.fictiontimes.fictiontimesbackend.utils;

import com.google.gson.Gson;

public class CommonUtils {
    private static Gson gson = null;

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }
}
