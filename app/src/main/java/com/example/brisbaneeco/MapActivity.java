package com.example.brisbaneeco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    private void sendDataToServer() {
        new SendDataRequest().execute("Temperature: [20, 25, 40]");
    }

    private static class SendDataRequest extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            if (params.length == 0) return null;
            String data = params[0];
            try {
                URL url = new
                        URL("http://192.168.1.100:4567/receiveData");
                HttpURLConnection conn = (HttpURLConnection)
                        url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                int responseCode = conn.getResponseCode();
                System.out.println("Response Code: " + responseCode);
                String responseMessage = conn.getResponseMessage();
                System.out.println("Response Message: " + responseMessage);
                // Read the response
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();
                System.out.println("Response Message: " + response.toString());
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Response Code: " + e);
            }
            return null;
        }
    }
}