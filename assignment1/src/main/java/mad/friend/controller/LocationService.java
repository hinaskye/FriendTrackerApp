package mad.friend.controller;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import mad.friend.model.FriendModel;
import mad.friend.view.FriendListActivity;
import util.FriendTrackerUtil;

/**
 * Referenced and adapted from https://stackoverflow.com/questions/1513485/how-do-i-get-the-current-gps-location-programmatically-in-android
 */
public class LocationService extends Service implements LocationListener {

    private double latitude, longitude;
    LocationManager locationManager;

    private static final long MIN_DIST_FOR_UPDATE = 10;
    private static final long UPDATE_TIME = 60*1000;

    private String LOG_TAG = this.getClass().getName();

    @Override
    public void onCreate()
    {
        Log.i(LOG_TAG, "onCreate()");
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        //boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_DIST_FOR_UPDATE,
                UPDATE_TIME, this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.i(LOG_TAG, "onStartCommand()");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(LOG_TAG, "onLocationChanged()");
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        FriendModel.getInstance().setLatitude(latitude);
        FriendModel.getInstance().setLongitude(longitude);
        Log.i(LOG_TAG, String.format("latitude %f longitude %f", latitude, longitude));
        Intent locationChangedIntent = new Intent("LOCATION_CHANGED");
        sendBroadcast(locationChangedIntent);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(LOG_TAG, "onStatusChanged()");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(LOG_TAG, "onProviderEnabled()");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(LOG_TAG, "onProviderDisabled()");
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }
}