package com.sotiris.datumboxapp.utils;


import android.content.res.AssetManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * Created by sotiris on 24/2/2015.
 */
public class HitApiService implements Callable<InputStream> {

    private HttpURLConnection connection;
    private String urlParameters;
    private URL url;
    private String method;

    private static AppConfig config;

    public HitApiService(AssetManager assetManager, String method, String input) throws IOException {
        this.method = method;
        config = AppConfig.init(assetManager);
        url = new URL(new URL(config.getUrl()), method+".json");

        handleUrlParameters(input);

        connection = (HttpURLConnection) url.openConnection();
    }

    /*
    Parameter text:
        Sentimental
        Twitter
        Subjectivity
        Topic Classification
        Spam
        Adult content
        Readability
        Language
        Commercial
        Educational
        Gender
        Text
    Parameter text and number of keywords to get:
        Keyword Extraction
    Parameter text and copy to compare
        Document Similarity
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

    private InputStream postURL() throws IOException {

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
        connection.setUseCaches(false);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

        try {
            wr.writeBytes(urlParameters);
            wr.flush();
        } finally {
            wr.close();
        }
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
