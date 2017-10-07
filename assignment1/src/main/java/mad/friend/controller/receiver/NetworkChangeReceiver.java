package mad.friend.controller.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Hinaskye on 5/10/2017.
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
            }
            else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                Toast.makeText(context, "Mobile Data On", Toast.LENGTH_SHORT).show();
                Intent suggestNowIntent = new Intent(context, LocationReceiver.class);
                suggestNowIntent.setAction("SUGGEST_NOW");
                context.sendBroadcast(suggestNowIntent);
            }
        }
    }
}
