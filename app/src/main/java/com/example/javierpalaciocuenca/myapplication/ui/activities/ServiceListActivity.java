package com.example.javierpalaciocuenca.myapplication.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.javierpalaciocuenca.myapplication.R;
import com.example.javierpalaciocuenca.myapplication.resources.JSONResource;
import com.example.javierpalaciocuenca.myapplication.resources.impl.ATMSource;
import com.example.javierpalaciocuenca.myapplication.resources.impl.BowlingSource;
import com.example.javierpalaciocuenca.myapplication.resources.impl.CasinoSource;
import com.example.javierpalaciocuenca.myapplication.resources.impl.CitizenATMSource;
import com.example.javierpalaciocuenca.myapplication.resources.impl.RecreationalAreaSource;
import com.example.javierpalaciocuenca.myapplication.resources.impl.SoccerFieldSource;
import com.example.javierpalaciocuenca.myapplication.ui.activities.utils.MapItem;
import com.example.javierpalaciocuenca.myapplication.ui.custom.CustomList;
import com.example.javierpalaciocuenca.myapplication.utils.ExceptionDialogBuilder;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ServiceListActivity extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private HashMap<String, Class> classMap = new HashMap<String, Class>() {
        {
            put("Clothes Containers", CitizenATMSource.class);
            put("Bowling", BowlingSource.class);
            put("ATMs", ATMSource.class);
            put("Recreational Areas", RecreationalAreaSource.class);
            put("Casinos", CasinoSource.class);
            put("Soccer Fields", SoccerFieldSource.class);
            put("Drupal", null);
            put("Twitter", null);
        }
    };

    private GoogleApiClient client;
    private ListView listView;
    private CustomList adapter;
    private ProgressDialog progressDialog;
    private List<String> headers = new ArrayList<>(classMap.keySet());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        try {

            if (this.adapter == null) {
                this.adapter = new CustomList(ServiceListActivity.this, classMap);
            }

            if (this.progressDialog == null) {
                this.progressDialog = new ProgressDialog(ServiceListActivity.this);
                this.progressDialog.setTitle("Connecting");
                this.progressDialog.setMessage("Resource is being downloaded and processed");
            }

            this.listView = (ListView) findViewById(R.id.listview);
            this.listView.setAdapter(this.adapter);
            this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //TODO: Create a factory that creates the object or maybe create a naming code like in MyJet with the upserts
                    try {
                        Class usableClass = classMap.get(headers.get(position));
                        if (usableClass != null) {
                            JSONResource jsonResource = (JSONResource) classMap.get(headers.get(position)).newInstance();
                            jsonResource.setContext(ServiceListActivity.this);
                            jsonResource.setProgressDialog(progressDialog);

                            ArrayList<MapItem> mapItems = new ArrayList<>(jsonResource.execute());

                            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                            intent.putExtra("mapItems", mapItems);

                            startActivity(intent);
                        } else {
                            Toast.makeText(ServiceListActivity.this, "The "+ headers.get(+position) + " is not available yet ", Toast.LENGTH_SHORT).show();
                        }

                    } catch (InstantiationException e) {
                        ExceptionDialogBuilder.createExceptionDialog(ServiceListActivity.this, e.getMessage()).show();
                    } catch (IllegalAccessException e) {
                        ExceptionDialogBuilder.createExceptionDialog(ServiceListActivity.this, e.getMessage()).show();
                    }

                }
            });

            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        }catch (Exception e) {
            ExceptionDialogBuilder.createExceptionDialog(ServiceListActivity.this, e.getMessage()).show();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("My Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onResume() {
        super.onResume();

        //Hides the progress dialog anytime coming from another activity
        if (this.progressDialog != null && this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        this.client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        this.client.disconnect();
    }
}
