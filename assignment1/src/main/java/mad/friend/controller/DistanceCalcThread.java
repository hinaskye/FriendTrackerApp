package mad.friend.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import mad.friend.model.Friend;
import mad.friend.model.FriendModel;
import mad.friend.model.WalkingData;
import mad.friend.model.WalkingDataModel;

/**
 * DistanceCalcThread
 * Forms a query to send to Google Distance Matrix based on your location and friends location
 * Add walking data (time for you and your friend to meet at mid point) to a WalkingDataModel class
 */
public class DistanceCalcThread extends Thread {

    Context caller;
    private double fLatitude, fLongitude, uLatitude, uLongitude, mLatitude, mLongitude;
    private WalkingData walkingData = new WalkingData();

    private String LOG_TAG = this.getClass().getName();

    public DistanceCalcThread(Context caller)
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
                //Compute mid point
                // Get friend location in form of (lat,long) and grab just the lat and long data
                String fLocation = (String) FriendModel.getInstance().getFriendLocation().get(friend.getName());
                String[] tokens = fLocation.replace("(","").replace(")","").split(",");

                fLatitude = Double.parseDouble(tokens[0]);
                fLongitude = Double.parseDouble(tokens[1]);

                // calc mid point
                mLatitude = (uLatitude + fLatitude)/2.0;
                mLongitude = (uLongitude + fLongitude)/2.0;

                // DEBUG print yours, friends and mid point lat and long values
                Log.d(LOG_TAG, String.format("uLatitude %f uLongitude %f fLatitude %f fLongitude %f " +
                                "mLatitude %f mLongitude %f", uLatitude, uLongitude, fLatitude, fLongitude,
                        mLatitude, mLongitude));

                // WalkingData object set your walk time, friends walk time, and which friend.
                walkingData.setFriend(friend);
                // Your walk time to the mid point
                try {
                    String yourRequest = returnJSONStringfromURL(uLatitude, uLongitude, mLatitude, mLongitude);
                    setWalkingTime(yourRequest, WalkingDataModel.you);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Friend's walk time to mid point
                try {
                    String friendRequest = returnJSONStringfromURL(fLatitude, fLongitude, mLatitude, mLongitude);
                    setWalkingTime(friendRequest, WalkingDataModel.theirs);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Add to WalkingDataModel once both are computed
                if(walkingData.timeIsSet())
                {
                    WalkingDataModel.getInstance().addToWalkingList(walkingData);
                    Log.i(LOG_TAG, walkingData.toString());
                    walkingData = new WalkingData(); // reset walking data for each friend
                }
                else
                {
                    Log.e(LOG_TAG, "walking time of both yours and friends not set");
                    Log.e(LOG_TAG, walkingData.toString());
                }
            }
        }//for
        Log.i(LOG_TAG,WalkingDataModel.getInstance().toString());
        Intent suggestIntent = new Intent(caller, NotificationReceiver.class);
        suggestIntent.setAction("SUGGEST_NOW_NOTIFICATION");
        caller.sendBroadcast(suggestIntent);
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

    public String returnJSONStringfromURL(double sLatitude, double sLongitude, double tLatitude, double tLongitude)
    {
        try {
            // Post to google distance matrix
            // Construct string
            String requestURL = urlString(sLatitude, sLongitude, tLatitude, tLongitude);
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
            //Log.i(LOG_TAG, jsonString.toString());
            return jsonString.toString();
        }
        catch(MalformedURLException me) {
            me.printStackTrace();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param jsonString The JSON request given form Google Distance Matrix API
     * @param you_or_friend WalkingDataModel.you OR WalkingDataModel.theirs. Specifies whos time to set
     * @throws Exception
     */
    public void setWalkingTime(String jsonString, int you_or_friend) throws Exception
    {
        Log.i(LOG_TAG, "setWalkingTime()");
        JSONObject distanceMatrix = new JSONObject(jsonString);
        JSONArray dest = distanceMatrix.getJSONArray("destination_addresses");
        JSONArray origin = distanceMatrix.getJSONArray("origin_addresses");
        JSONArray row = distanceMatrix.getJSONArray("rows");
        JSONArray elements = row.getJSONObject(0).getJSONArray("elements");
        JSONObject distance = elements.getJSONObject(0).getJSONObject("distance");
        JSONObject duration = elements.getJSONObject(0).getJSONObject("duration");
        String walkTimeString = duration.getString("value");
        int walkTime = Math.round(Float.parseFloat(walkTimeString)/60);
        String walkingStatus = elements.getJSONObject(0).getString("status");
        String status = distanceMatrix.getString("status");
        if(you_or_friend == WalkingDataModel.you)
        {
            walkingData.setYours(walkTime);
        }
        else if(you_or_friend == WalkingDataModel.theirs)
        {
            walkingData.setTheirs(walkTime);
        }
    }
}
