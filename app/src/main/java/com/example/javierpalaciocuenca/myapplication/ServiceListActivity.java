package com.example.javierpalaciocuenca.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import impl.LeisureService;
import utilities.ExceptionDialogBuilder;


public class ServiceListActivity extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ListView listView;

    String[] web = {
            "Contenedores de ropa",
            "Twitter",
            "Windows",
            "Bing",
            "Itunes",
            "Wordpress",
            "Drupal"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        try {

            CustomList adapter = new CustomList(ServiceListActivity.this, web);
            listView = (ListView) findViewById(R.id.listview);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        //TODO: Extract this logic to an strategy pattern with all the available items
                        //TODO: Create a factory that creates the object or maybe create a naming code like in MyJet with the upserts
                        AsyncTask<String, Void, JSONObject> asyncTask = LeisureService.getCitizenATM(ServiceListActivity.this);

                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        intent.putExtra("json", asyncTask.get().toString());

                        startActivity(intent);

                        Toast.makeText(ServiceListActivity.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
                    } catch (InterruptedException e) {
                        ExceptionDialogBuilder.createExceptionDialog(ServiceListActivity.this, e.getMessage()).show();
                    } catch (ExecutionException e) {
                        ExceptionDialogBuilder.createExceptionDialog(ServiceListActivity.this, e.getMessage()).show();
                    }
                }
            });

            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
            throw new Exception("TEST");
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
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
