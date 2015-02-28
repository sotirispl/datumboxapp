package com.sotiris.datumboxapp.utils;

/**
 * Created by sotiris on 24/2/2015.
 */
public class AppConfig {

    private static AppConfig config;
    private static String api_key = "e69b9f2a99c73fd3993a7446e7bd5a2e";
    private static String url = "http://api.datumbox.com/1.0/SentimentAnalysis.json";

    private AppConfig() {
        //TODO: Load properties from properties file
    }

    public static AppConfig init() {
        if(config == null) {
            config = new AppConfig();
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
