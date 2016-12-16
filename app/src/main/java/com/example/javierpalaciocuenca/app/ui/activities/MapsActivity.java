package com.example.javierpalaciocuenca.app.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.example.javierpalaciocuenca.app.persistence.model.MapItem;
import com.example.javierpalaciocuenca.app.resources.impl.BusLocationSource;
import com.example.javierpalaciocuenca.app.ui.activities.utils.ActivityStatus;
import com.example.javierpalaciocuenca.app.utils.ExceptionDialogBuilder;
import com.example.javierpalaciocuenca.app.utils.MapItemsData;
import com.example.javierpalaciocuenca.app.utils.constants.Constants;
import com.example.javierpalaciocuenca.myapplication.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "MapsActivity";

    private GoogleMap mMap;
    private GoogleApiClient client;

    private List<Marker> markers;
    private ArrayList<MapItem> mapItems;
    private String intentType;
    private Handler mHandler;
    private LatLngBounds.Builder builder;

    private Runnable busLive = new Runnable() {
        @Override
        public void run() {
            try {
                fetchBusData(); //fetching bus information
            } catch (InterruptedException e) {
                ExceptionDialogBuilder.createExceptionDialog(MapsActivity.this, e.getMessage()).show();
            } finally {
                mHandler.postDelayed(busLive, Constants.BUS_FETCHING_TIME);
            }
        }
    };

    private Runnable blinkBusStops = new Runnable() {
        @Override
        public void run() {
            try {
                for (Marker marker : markers) {
                    if (marker.getTitle().contains("P")) {
                        marker.setVisible(!marker.isVisible());
                    }
                }
            } finally {
                mHandler.postDelayed(blinkBusStops, Constants.BUS_STOP_BLINKING_TIME);
            }
        }
    };

    private boolean isSameLatLng(LatLng latLng, LatLng latLng2) {
        return latLng.latitude == latLng2.latitude &&
                latLng.longitude == latLng2.longitude;
    }

    private boolean isMarkerPresent(Marker marker) {
        for (MapItem mapItem : mapItems) {
            if (isSameLatLng(mapItem.getLatLng(), marker.getPosition())) {
                return true;
            }
        }

        return false;
    }

    private void consolidateMarkers() {
        for (Marker marker : markers) {
            if (!isMarkerPresent(marker)) {
                marker.remove();
            }
        }
    }

    private boolean isMapItemPresent(MapItem mapItem) {
        for (Marker marker : markers) {
            if (isSameLatLng(mapItem.getLatLng(), marker.getPosition())) {
                return true;
            }
        }

        return false;
    }

    private void createNewMarkers() {
        for (MapItem mapItem : mapItems) {
            if (!isMapItemPresent(mapItem)) {
                createSingleMarker(mapItem);
            }
        }
    }


    private void fetchBusData() throws InterruptedException {
        BusLocationSource busSource = new BusLocationSource();

        this.mapItems = (ArrayList<MapItem>) busSource.execute();
        //TODO: Update only the ones changing location
        consolidateMarkers();
        createNewMarkers();
    }

    private void initBusLive() {
        if (this.intentType.equals(Constants.INTENT_TYPE_BUS)) {
            this.busLive.run();
            this.blinkBusStops.run();
        }
    }

    private void initMap() {
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void cleanMarkers() {
        Iterator<Marker> iterator = markers.iterator();

        while (iterator.hasNext()) {
            iterator.next().remove();
            iterator.remove();
        }
    }

    private void initResetButton() {
        Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanMarkers();
            }
        });
    }

    private void initDataStructuresHandlerBuilderAndClient() {
        this.markers = new ArrayList<>();
        this.mapItems = new ArrayList<>();
        this.client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        this.mHandler = new Handler();
        this.builder = new LatLngBounds.Builder();
    }

    private void init() {
        initMap();
        initResetButton();
        initDataStructuresHandlerBuilderAndClient();
    }

    private void receiveIntentAndSetMapItems() {
        Intent intent = getIntent();
        this.intentType = intent.getStringExtra("intentType");

        //Set mapItems locally to be used
        this.mapItems = (ArrayList<MapItem>) MapItemsData.getInstance().getMapItems();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        receiveIntentAndSetMapItems();
    }

    private MarkerOptions createMarkerOptions(MapItem mapItem) {
        MarkerOptions markerOptions = null;

        if (mapItem.getLatLng() != null) {
            markerOptions = new MarkerOptions();
            markerOptions.title(mapItem.getTitle());
            markerOptions.position(mapItem.getLatLng());
            markerOptions.icon(BitmapDescriptorFactory.fromResource(mapItem.getMarker()));

            if (mapItem.getUrl() != null) {
                //Hide URL temporary
                //markerOptions.snippet(mapItem.getUrl());
            }
        }

        return markerOptions;
    }

    private void createSingleMarker(MapItem mapItem) {
        MarkerOptions markerOptions = createMarkerOptions(mapItem);

        if (markerOptions != null) {
            this.markers.add(this.mMap.addMarker(markerOptions));
            this.builder.include(markerOptions.getPosition()); //Include mapItem in the bounds
        }
    }

    private void createMarkers() {
        for (MapItem mapItem : this.mapItems) {
            createSingleMarker(mapItem);
        }
    }

    private void centerBounds() {
        /* Move the camera to take all the markers in case there's any */
        if (this.markers.size() > 0) {
            LatLngBounds bounds = this.builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * Constants.MAP_ACTIVITY_BOUNDS_OFFSET);

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding); //offset from edges of the map in pixels
            this.mMap.moveCamera(cu);
        }
    }

    private void stopActivity() {
        ActivityStatus.activityPaused();
        cleanMarkers();

        if (this.intentType.equals(Constants.INTENT_TYPE_BUS)) {
            mHandler.removeCallbacks(busLive); // Stop repeating busLive
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        try {
            createMarkers();
            centerBounds();
            initBusLive();

        } catch (Exception e) {
            ExceptionDialogBuilder.createExceptionDialog(MapsActivity.this, e.getMessage()).show();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Maps Page") // TODO: Define a title for the content shown.
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
        ActivityStatus.activityResumed();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        this.client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        stopActivity();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        this.client.disconnect();
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityStatus.activityResumed();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopActivity();
    }
}