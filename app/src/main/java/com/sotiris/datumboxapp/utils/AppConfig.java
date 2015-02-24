package com.sotiris.datumboxapp.utils;

/**
 * Created by sotiris on 24/2/2015.
 */
public class AppConfig {

    private String api_key = "e69b9f2a99c73fd3993a7446e7bd5a2e";
    private String url = "http://api.datumbox.com/1.0/SentimentAnalysis.json";

    public String getApi_key() {
        return api_key;
    }

    public String getUrl() {
        return url;
    }
}
