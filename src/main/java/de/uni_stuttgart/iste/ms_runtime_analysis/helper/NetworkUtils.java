package de.uni_stuttgart.iste.ms_runtime_analysis.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Defines helper functions for networking
 */
public class NetworkUtils {

    private NetworkUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Fetches a http document from an url
     * 
     * @param url url of document
     * @return fetched document
     * @throws IOException error while fetching the document
     */
    private static String getHTTP(String url) throws IOException {

        URL myURL = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        rd.close();

        return result.toString();
    }

    /**
     * Fetches a https document from an url
     * 
     * @param url url of document
     * @return fetched document
     * @throws IOException error while fetching the document
     */
    private static String getHTTPS(String url) throws IOException {

        URL myURL = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) myURL.openConnection();
        conn.setRequestMethod("GET");

        String line;
        StringBuilder result = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        rd.close();

        return result.toString();
    }

    /**
     * Fetches a document from an url
     * 
     * @param url url of document
     * @return fetched document or null if protocol not supported
     * @throws IOException error while fetching the document
     */
    public static String fetchDocument(String url) throws IOException {

        if (url.startsWith("https://")) {
            return getHTTPS(url);
        }

        if (url.startsWith("http://")) {
            return getHTTP(url);
        }

        return null;
    }

}
