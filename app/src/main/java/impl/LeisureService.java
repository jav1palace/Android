package impl;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import org.json.JSONObject;

import utilities.JSONReader;

/**
 * Created by javierpalaciocuenca on 14/10/2016.
 */
public class LeisureService {
    public static AsyncTask<String, Void, JSONObject> getSoccerFields(ProgressBar progressBar, Context context) {
        return new JSONReader(progressBar, context).execute("http://datos.gijon.es/doc/deporte/campos-futbol.json");
    }

    public static AsyncTask<String, Void, JSONObject> getCitizenATM(Context context) {
        return getCitizenATM(null, context);
    }

    public static AsyncTask<String, Void, JSONObject> getCitizenATM(ProgressBar progressBar, Context context) {
        return new JSONReader(progressBar, context).execute("http://opendata.gijon.es/descargar.php?id=7&tipo=JSON");
    }

}
