package com.sotiris.datumboxapp.utils;

import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sotiris on 24/2/2015.
 */
public class AppConfig {

    private static AppConfig config;
    private static String api_key;
    private static String url;

    private AppConfig(AssetManager assetManager) {
        //TODO: Load properties from properties file
        Properties prop = new Properties();
        try {
            InputStream fileStream = assetManager.open("config.properties");
            prop.load(fileStream);
            fileStream.close();
            api_key = prop.getProperty("api_key");
            url = prop.getProperty("url");
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

    public String getApi_key() {
        return api_key;
    }

    public String getUrl() {
        return url;
    }
}
