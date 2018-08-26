package ws.idroid.urlconnection.util;

import android.util.Log;

import java.io.*;
import java.net.*;

import javax.net.ssl.HttpsURLConnection;

import ws.idroid.urlconnection.constants.Constants;

public class NetworkUtil {
    public static String makeServiceCall(String requestURL) {

        URL url;
        String response = "";

        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    os, "UTF-8"));

            writer.flush();
            writer.close();
            os.close();


            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";

                throw new Exception(responseCode + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(Constants.TAG, ">>>Response - >" + response);
        return response;
    }

}
