package com.example.luke.fyp.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Luke on 29/09/2017.
 */

public class NetworkUtils {

    private static String apiKey = "enter your API key here!" ;

    final static String BASE_URL =
            "https://api.nal.usda.gov/ndb/";

    final static String REPORTS = "reports/?";
    final static String SEARCH = "search/?";

    final static String SEARCH_QUERY = "q";

    final static String API_QUERY = "api_key";
    final static String TYPE_QUERY = "type";
    final static String FORMAT_QUERY= "format";
    final static String NDBNO_QUERY= "ndbno";

    private static String type = "b";
    private static String format = "json";

    public static URL makeNdbnoUrl(String ndbno) {
        Uri builtUri = Uri.parse(BASE_URL + REPORTS).buildUpon()
                .appendQueryParameter(NDBNO_QUERY, ndbno)
                .appendQueryParameter(TYPE_QUERY, type)
                .appendQueryParameter(FORMAT_QUERY, format)
                .appendQueryParameter(API_QUERY, apiKey)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL makeSearchUrl(String search) {
        Uri builtUri = Uri.parse(BASE_URL + SEARCH).buildUpon()
                .appendQueryParameter(SEARCH_QUERY, search)
                .appendQueryParameter(FORMAT_QUERY, format)
                .appendQueryParameter(API_QUERY, apiKey)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
