package com.sotiris.datumboxapp.utils;

import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Sotiris Poulias
 *
 * The Singleton Class that contains the configuration data
 * from the config.properties file in assets directory of the project.
 * If you want to change any of the properties, edit /assets/config.properties
 * file.
 */
public class AppConfig {

    private static AppConfig config;
    private static String apiKey;
    private static String url;
    private static String textSimilarity;
    private static String numberOfKeywords;

    /**
     * Constructor called if there is not any instance
     * constructed already. Loads the file and passes the values
     * in the fields of the object.
     *
     * @param assetManager need to open the file
     */
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

    /**
     * Is called to return an instance of config object.
     * If an object is already created then returns it without construct any new one.
     *
     * @param assetManager need to open the file
     * @return the instance of config object that is created or that is already in life.
     */
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
