package utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.javierpalaciocuenca.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by javierpalaciocuenca on 14/10/2016.
 */
public class JSONReader extends AsyncTask<String, Void, JSONObject> {
    private ProgressBar spinner;
    private Context context;
    private AlertDialog alertDialog;
    public JSONReader(ProgressBar progressBar){
        this.spinner = progressBar;
    }
    public JSONReader(Context context){
        this.context = context;
    }
    public JSONReader(ProgressBar progressBar, Context context){
        this.context = context;
        this.spinner = progressBar;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return formatUnicodeJSON(sb.toString());
    }

    private static String formatUnicodeJSON(String json){
        Pattern pattern = Pattern.compile("\\&#([0-9]+);"); //The regex gets the $#123; unicode value
        Matcher matcher = pattern.matcher(json);
        while (matcher.find()) {
            String replace = matcher.group();
            String unicodeValue = matcher.group(1); //Gets the unicode decimal value

            json = json.replace(replace,
                    new String(Character.toChars(Integer.parseInt(unicodeValue))));
        }

        return json;
    }

    @Override
    protected JSONObject doInBackground(String[] url) {
        try {
            InputStream inputSteam = new URL(url[0]).openStream();
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputSteam, Charset.forName("UTF-8")));
                String jsonText = readAll(reader);
                JSONObject json = new JSONObject(jsonText);

                return json;
            }  catch (JSONException e) {
                ExceptionDialogBuilder.createExceptionDialog(context, e.getMessage()).show();
            }  finally {
                inputSteam.close();
            }
        } catch (MalformedURLException e) {
            ExceptionDialogBuilder.createExceptionDialog(context, e.getMessage()).show();
        } catch (IOException e) {
            ExceptionDialogBuilder.createExceptionDialog(context, e.getMessage()).show();
        }

        return null;
    }

    //TODO: Extract this out of here?
    @Override
    protected void onPostExecute (JSONObject result){
        if(spinner != null){
            spinner.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPreExecute() {
        if(spinner != null){
            spinner.setVisibility(View.VISIBLE);
        }
    }
}
