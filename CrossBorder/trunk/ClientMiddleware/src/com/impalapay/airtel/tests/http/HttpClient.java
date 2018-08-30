package com.impalapay.airtel.tests.http;
/**
 * used for testing http
 */

/**
 * @author eugene
 *
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class HttpClient {

  public static void main(String[] args) throws Exception {
    URL url = new URL("http://localhost/test/");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setDoOutput(true);
    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

    writer.write("sessionid=1werr45567890");
    writer.flush();
    String line;
    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    while ((line = reader.readLine()) != null) {
      System.out.println(line);
    }
    writer.close();
    reader.close();

  }
}