package com.example.javierpalaciocuenca.myapplication.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by javierpalaciocuenca on 14/10/2016.
 */
public class JSONReader extends AsyncTask<String, Void, JSONObject> {
    private static final String TAG = "JSONReader";

    private ProgressDialog progressDialog;
    private Context context;

    public JSONReader(Context context, ProgressDialog progressDialog) {
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public JSONReader(Context context) {
        this.context = context;
    }

    private static String readAll(Reader reader) throws IOException {
        int intChar;
        StringBuilder stringBuilder = new StringBuilder();

        while ((intChar = reader.read()) != -1) {
            stringBuilder.append((char) intChar);
        }

        return formatUnicodeJSON(stringBuilder.toString());
    }

    private static String formatUnicodeJSON(String json) {
        Pattern pattern = Pattern.compile("\\&#([0-9]+);"); //The regex gets the $#123; unicode value
        Matcher matcher = pattern.matcher(json);
        while (matcher.find()) {
            String replace = matcher.group();
            String unicodeValue = matcher.group(1); //Gets the unicode decimal value

            json = json.replace(replace, new String(Character.toChars(Integer.parseInt(unicodeValue))));
        }

        return json;
    }

    @Override
    protected JSONObject doInBackground(String[] resourceUrl) {
        InputStream inputStream = null;

        try {

            HttpURLConnection urlConnection;

            URL url = new URL(resourceUrl[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("GET");

            int statusCode = urlConnection.getResponseCode();
            /* 200 represents HTTP OK */
            if (statusCode == 200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                String jsonText = readAll(reader);
                JSONObject json = new JSONObject(jsonText);

                return json;
            } else {
                throw new Exception("Failed to fetch data!!");
            }

        } catch (MalformedURLException e) {
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        } catch (IOException e) {
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        } catch (JSONException e) {
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        } catch (Exception e) {
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
            }
        }

        return null;
    }


    @Override
    protected void onPreExecute() {
        if (this.progressDialog != null) {
            this.progressDialog.show();
        }
    }
}