package com.sotiris.datumboxapp.controllers;

import android.content.res.AssetManager;

import com.sotiris.datumboxapp.utils.AppConfig;
import com.sotiris.datumboxapp.utils.HitApiService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by sotiris on 21/2/2015.
 */
public class SingletonController {

    private static SingletonController controller = null;

    private AssetManager assetManager;
    private ExecutorService service;
    private Future<InputStream> hitApiService;
    private String responseData;

    private SingletonController(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public static SingletonController getController(AssetManager assetManager) {
        if(controller == null) {
            controller = new SingletonController(assetManager);
            return controller;
        }
        return controller;
    }

    public String analyzeText(String text, String method) throws IOException, ExecutionException, InterruptedException {


        service = Executors.newFixedThreadPool(1);
        hitApiService = service.submit(new HitApiService(assetManager, method, text));
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
