package mad.friend.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import mad.friend.model.Friend;
import mad.friend.model.FriendModel;
import mad.friend.model.MeetingModel;

/**
 * Created by Hinaskye on 6/10/2017.
 */

public class DistanceCalcThread extends Thread {

    Activity caller;
    private double fLatitude, fLongitude, uLatitude, uLongitude, mLatitude, mLongitude;
    // friend.getName
    Map<String, String[]> timeMap = new HashMap<>();

    private String LOG_TAG = this.getClass().getName();

    public DistanceCalcThread(Activity caller)
    {
        this.caller = caller;
    }

    @Override
    public void run()
    {
        Log.i(LOG_TAG, "run()");
        // while the app does not have the relevant data
        while (FriendModel.getInstance().getLatitude() == 0 || FriendModel.getInstance().getLongitude() == 0
                || FriendModel.getInstance().getFriendLocation().isEmpty())
        {
            try {
                // sleep for 2 secs
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.i(LOG_TAG, "Calculating distance");
        uLatitude = FriendModel.getInstance().getLatitude();
        uLongitude = FriendModel.getInstance().getLongitude();
        for(Friend friend : FriendModel.getInstance().getFriends())
        {
            if(FriendModel.getInstance().getFriendLocation().containsKey(friend.getName()))
            {
                String fLocation = (String) FriendModel.getInstance().getFriendLocation().get(friend.getName());
                String[] tokens = fLocation.replace("(","").replace(")","").split(",");

                fLatitude = Double.parseDouble(tokens[0]);
                fLongitude = Double.parseDouble(tokens[1]);

                // Compute mid point
                mLatitude = (uLatitude + fLatitude)/2.0;
                mLongitude = (uLongitude + fLongitude)/2.0;

                Log.i(LOG_TAG, String.format("uLatitude %f uLongitude %f fLatitude %f fLongitude %f " +
                                "mLatitude %f mLongitude %f", uLatitude, uLongitude, fLatitude, fLongitude,
                        mLatitude, mLongitude));

                try {
                    // Post to google distance matrix
                    // Construct string
                    String requestURL = urlString(uLatitude, uLongitude, mLatitude, mLongitude);
                    URL url = new URL(requestURL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept", "text/html");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);

                    int statusCode = connection.getResponseCode();
                    if (statusCode != HttpURLConnection.HTTP_OK)
                    {
                        Log.e(LOG_TAG, "Invalid Response Code: " + statusCode);
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder jsonString = new StringBuilder();
                    String line;
                    while((line=br.readLine())!=null)
                    {
                        jsonString.append(line);
                    }
                    Log.i(LOG_TAG, jsonString.toString());
                }
                catch(MalformedURLException me) {
                    me.printStackTrace();
                }
                catch(IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    // google distance matrix code, default mode walking
    public String urlString(double sLatitude, double sLongitude, double tLatitude, double tLongitude)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("https://maps.googleapis.com/maps/api/distancematrix/json?origins=");
        sb.append(String.format("%f,%f&destinations=%f,%f&mode=walking", sLatitude, sLongitude,
                tLatitude, tLongitude));
        Log.i(LOG_TAG, "urlString:"+sb.toString());
        return sb.toString();
    }
}
