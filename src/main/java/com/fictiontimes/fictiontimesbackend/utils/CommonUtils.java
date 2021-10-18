package com.fictiontimes.fictiontimesbackend.utils;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Properties;

public class CommonUtils {
    public static Gson getGson() {
        return new Gson();
    }

    public static String getDomain() {
        try {
            Properties properties = new Properties();
            properties.load(FileUtils.class.getResourceAsStream("/application.properties"));
            return properties.getProperty("domain");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFrontendAddress() {
        try {
            Properties properties = new Properties();
            properties.load(FileUtils.class.getResourceAsStream("/application.properties"));
            return properties.getProperty("frontend");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
