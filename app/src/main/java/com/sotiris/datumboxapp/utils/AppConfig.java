package com.sotiris.datumboxapp.utils;

import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sotiris on 24/2/2015.
 */
public class AppConfig {

    private static AppConfig config;
    private static String apiKey;
    private static String url;
    private static String textSimilarity;
    private static String numberOfKeywords;

    private AppConfig(AssetManager assetManager) {
        Properties prop = new Properties();
        try {
            InputStream fileStream = assetManager.open("config.properties");
            prop.load(fileStream);
            fileStream.close();
            apiKey = prop.getProperty("apiKey");
            url = prop.getProperty("url");
            textSimilarity = prop.getProperty("textSimilarity");
            numberOfKeywords = prop.getProperty("numberOfKeywords");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AppConfig init(AssetManager assetManager) {
        if(config == null) {
            config = new AppConfig(assetManager);
        }
        return config;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getUrl() {
        return url;
    }

    public String getTextSimilarity() {
        return textSimilarity;
    }

    public String getNumberOfKeywords() {
        return numberOfKeywords;
    }
}
