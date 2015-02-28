package com.sotiris.datumboxapp.controllers;

import com.sotiris.datumboxapp.utils.AppConfig;
import com.sotiris.datumboxapp.utils.HitApiService;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;


/**
 * Created by sotiris on 21/2/2015.
 */
public class SingletonController {

    private static SingletonController controller = null;

    private static AppConfig config;
    private ExecutorService service;
    private Future<InputStream> hitApiService;
    private String responseData;

    private SingletonController() {
        config = AppConfig.init();
    }

    public static SingletonController getController() {
        if(controller == null) {
            controller = new SingletonController();
            return controller;
        }
        return controller;
    }

    public String analyzeText(String text) throws IOException, ExecutionException, InterruptedException {
        String urlParameters = "api_key=" + config.getApi_key() + "&text=" + text;
        String request = config.getUrl();
        URL url = new URL(request);

        service = Executors.newFixedThreadPool(1);
        hitApiService = service.submit(new HitApiService(url, urlParameters, request));
        InputStream inputStream = hitApiService.get();
        try {
            String jsonTxt = IOUtils.toString(inputStream);
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
