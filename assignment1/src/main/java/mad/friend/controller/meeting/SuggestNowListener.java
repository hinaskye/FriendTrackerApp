package mad.friend.controller.meeting;

import android.app.Activity;
import android.view.View;

import mad.friend.controller.DistanceCalcThread;

/**
 * SuggestNowListener
 * Creates the thread that calculates the distance and sends a broadcast
 * to display a suggest now notification
 */
public class SuggestNowListener implements View.OnClickListener {

    Activity current;

    public SuggestNowListener(Activity caller)
    {
        current = caller;
    }

    @Override
    public void onClick(View v) {
        Thread distCalcThread = new DistanceCalcThread(current);
        distCalcThread.start();
    }
}
