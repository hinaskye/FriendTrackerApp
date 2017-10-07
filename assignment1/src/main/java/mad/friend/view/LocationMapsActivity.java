package mad.friend.view;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import hinaskye.assignment1.R;

/**
 * Template as from android studio
 * Added method to get latitude and longitude from intent so can display that location
 * Also zooms in automatically on specified location intent
 */
public class LocationMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private String LOG_TAG = this.getClass().getName();
    private GoogleMap mMap;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            latitude = (double) extras.get("latitude");
            longitude = (double) extras.get("longitude");
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
        mMap = googleMap;
        LatLng friend = new LatLng(latitude, longitude);
        Log.i(LOG_TAG, String.format("latitude %f longitude %f", latitude, longitude));
        mMap.addMarker(new MarkerOptions().position(friend).title("Marker of Friend"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(friend));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(friend, 15)); // Zoom in on location

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
}
