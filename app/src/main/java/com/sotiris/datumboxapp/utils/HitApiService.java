package com.sotiris.datumboxapp.utils;


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
    private String request;
    private URL url;

    public HitApiService(URL url, String urlParameters, String request) throws IOException {
        this.urlParameters = urlParameters;
        this.request = request;
        this.url = url;
        this.connection = (HttpURLConnection) url.openConnection();
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
