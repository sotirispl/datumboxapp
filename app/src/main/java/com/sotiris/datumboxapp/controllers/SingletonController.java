package com.sotiris.datumboxapp.controllers;

import com.sotiris.datumboxapp.utils.AppConfig;
import com.sotiris.datumboxapp.utils.HitApiService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.IOUtils;


/**
 * Created by sotiris on 21/2/2015.
 */
public class SingletonController {

    private static SingletonController controller = null;

    private static Logger logger;
    private static AppConfig config;
    private static HitApiService hitApiService;

    private String responseData;

    private SingletonController() {
        logger = LoggerFactory.getLogger(SingletonController.class);
    }

    public static SingletonController getController() {
        if(controller == null) {
            controller = new SingletonController();
            return controller;
        }
        return controller;
    }

    public String analyzeText(String text) throws IOException {
        String urlParameters = "api_key=" + config.getApi_key() + "&text=" + text;
        String request = config.getUrl();
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        logger.debug("Sending request to datumbox");
        InputStream inputStream = hitApiService.postURL(connection, url, urlParameters, request);

        String jsonTxt = IOUtils.toString(inputStream);
        try {
            responseData = parse(jsonTxt).replace("\"", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseData;

    }

    public String parse(String jsonLine) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonLine);
        jsonObject = new JSONObject(jsonObject.getString("output"));
        String result = jsonObject.getString("result");
        return result;
    }

}
