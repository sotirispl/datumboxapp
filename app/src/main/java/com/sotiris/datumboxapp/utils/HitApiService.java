package com.sotiris.datumboxapp.utils;


import android.content.res.AssetManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * @author Sotiris Poulias
 *
 * HitApiService is called as a thread service through the call() method
 * and returns an InputStream object.
 */
public class HitApiService implements Callable<InputStream> {

    private HttpURLConnection connection;
    private String urlParameters;
    private URL url;
    private String method;

    private static AppConfig config;

    /**
     * Method to construct a new HitApiService object
     *
     * @param assetManager is needed to read config files from assets
     * @param methodSelected selected by the user
     * @param input the text that the user wants to analyze
     */
    public HitApiService(AssetManager assetManager, String methodSelected, String input) throws IOException {
        method = methodSelected;
        config = AppConfig.init(assetManager);
        url = new URL(new URL(config.getUrl()), method+".json");

        //called to handle the parameters that are posted in datumbox API
        handleUrlParameters(input);

        //open a connection with the API
        connection = (HttpURLConnection) url.openConnection();
    }

    /**
     * Handle the parameters that are needed by the API.
     *
     * @param input the text that the user wants to analyze
     */
    private void handleUrlParameters(String input) {
        urlParameters = "api_key=" + config.getApiKey();
        switch (method) {
            case "DocumentSimilarity":
                urlParameters += "&original=" + input + "&copy=" + config.getTextSimilarity();
                break;
            case "KeywordExtraction":
                urlParameters += "&n=" + config.getNumberOfKeywords();
            default:
                urlParameters += "&text=" + input;
                break;
        }
    }

    /**
     * Posts the parameters through the connection opened in the constructor
     *
     * @return InputStream with the response from the API
     * @throws IOException
     */
    private InputStream postURL() throws IOException {

        //set the properties for this connection
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
        connection.setUseCaches(false);

        //open the OutputStream through which the parameters are posted
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

        //write the parameters
        try {
            wr.writeBytes(urlParameters);
            wr.flush();
        } finally {
            wr.close();
        }

        //get the response
        InputStream inputStream = connection.getInputStream();
        return inputStream;
    }

    @Override
    public InputStream call() {
        try {
            return postURL();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
