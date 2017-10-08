package mad.friend.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * NetworkChangeReceiver
 * Receivers network changes and broadcast a suggest now intent if network is on
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() == ConnectivityManager.CONNECTIVITY_ACTION)
        {
            showNetworkType(context);
        }
    }

    public NetworkInfo getActivityNetworkInfo(Context context)
    {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    public boolean isOnline(Context context)
    {
        NetworkInfo networkInfo = getActivityNetworkInfo(context);
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     *  Displays a toast for network connection changes
     *  Broadcasts a suggest now meeting if there is network available
     */
    public void showNetworkType(Context context)
    {
        NetworkInfo networkInfo = getActivityNetworkInfo(context);

        if(networkInfo == null)
        {
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
            {
                Toast.makeText(context, "Wifi On", Toast.LENGTH_SHORT).show();
                Intent suggestNowIntent = new Intent(context, LocationReceiver.class);
                suggestNowIntent.setAction("SUGGEST_NOW");
                context.sendBroadcast(suggestNowIntent);
            }
            else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                // Broadcast to suggest a meeting now when mobile data is on
                Toast.makeText(context, "Mobile Data On", Toast.LENGTH_SHORT).show();
                // On real phone potentially only use wifi to broadcast
                Intent suggestNowIntent = new Intent(context, LocationReceiver.class);
                suggestNowIntent.setAction("SUGGEST_NOW");
                context.sendBroadcast(suggestNowIntent);
            }
        }
    }
}
