package com.sotiris.datumboxapp.controllers;

import android.content.res.AssetManager;

import com.sotiris.datumboxapp.utils.HitApiService;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Sotiris Poulias
 *
 * Singleton Class that controls does the bussiness between the activity
 * and the call of the service.
 */
public class SingletonController {

    private static SingletonController controller = null;

    private AssetManager assetManager;
    private ExecutorService service;
    private Future<InputStream> hitApiService;
    private String responseData;

    /**
     * Constructor called if there is not any instance
     * constructed already.
     *
     * @param assetManager need to open the config file
     */
    private SingletonController(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    /**
     * Is called to return an instance of SingletonController object.
     * If an object is already created then returns it without construct any new one.
     *
     * @param assetManager need to open the config file
     * @return the instance of SingletonController object that is created or that is already in life.
     */
    public static SingletonController getController(AssetManager assetManager) {
        if(controller == null) {
            controller = new SingletonController(assetManager);
            return controller;
        }
        return controller;
    }

    /**
     * Creates a new thread in which the HitApiService object is called
     * to handle the connection with the API.
     *
     * @param text The text that the user wants to analyse
     * @param method The method of analysis that the user wants
     * @return The string that the API call returned
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String analyzeText(String text, String method) throws IOException, ExecutionException, InterruptedException {

        //create a new fixed thread
        service = Executors.newFixedThreadPool(1);

        //construct a new HitApiService callable service in the thread
        hitApiService = service.submit(new HitApiService(assetManager, method, text));

        //it calles the default "call" method in the callable object
        InputStream inputStream = hitApiService.get();

        //parse the InputStream object that the HitApiService returned
        try {
            String jsonTxt = IOUtils.toString(inputStream);
            responseData = parse(jsonTxt).replace("\"", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseData;

    }

    /**
     * Parses the json that the API responsed with. It gets the string in the "result" tag.
     *
     * @param jsonLine the json that is to be parsed
     * @return the string with the result of the API call
     * @throws JSONException
     */
    public String parse(String jsonLine) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonLine);
        jsonObject = new JSONObject(jsonObject.getString("output"));
        String result = jsonObject.getString("result");
        return result;
    }

}
