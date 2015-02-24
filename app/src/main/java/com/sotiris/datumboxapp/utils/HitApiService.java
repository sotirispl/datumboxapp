package com.sotiris.datumboxapp.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sotiris on 24/2/2015.
 */
public class HitApiService {

    public InputStream postURL(HttpURLConnection connection, URL url,
                               String urlParameters, String request) throws IOException {

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
        connection.setUseCaches(false);

        DataOutputStream wr = null;

        try {
            wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
        } finally {
            wr.close();
        }
        InputStream is = connection.getInputStream();
        return is;
    }

}
