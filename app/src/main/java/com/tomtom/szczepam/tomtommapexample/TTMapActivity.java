package com.tomtom.szczepam.tomtommapexample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class TTMapActivity extends FragmentActivity {

    private static final String TOMTOM_MAPKIT_URL = "https://api.tomtom.com/lbs/map/3/basic/1/%d/%d/%d.png?key=<ENTER_YOUR_KEY_HERE>";
    private static final String TOMTOM_NDS_URL = "http://a.api.tomtom.com/map/1/tile/basic/main/%d/%d/%d.png?key=<ENTER_YOUR_KEY_HERE>";
    private static final String TOMTOM_TRAFFIC_URL = "https://api.tomtom.com/lbs/map/3/traffic/s3/%d/%d/%d.png?key=<ENTER_YOUR_KEY_HERE>&t=-1";

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttmap);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);

        TileProvider tomtomMapTileProvider = new UrlTileProvider(256,256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                URL url = null;
                String s = String.format(Locale.CANADA, TOMTOM_MAPKIT_URL,zoom,x,y);
                try {
                    url = new URL(s);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return url;
            }
        };

        TileProvider tomtomTrafficProvider = new UrlTileProvider(256,256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                URL url = null;
                String s = String.format(Locale.CANADA, TOMTOM_TRAFFIC_URL,zoom,x,y);
                try {
                    url = new URL(s);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return url;
            }
        };

        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(tomtomMapTileProvider));
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(tomtomTrafficProvider));
    }
}
