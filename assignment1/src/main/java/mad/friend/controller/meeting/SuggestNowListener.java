package mad.friend.controller.meeting;

import android.app.Activity;
import android.view.View;

import mad.friend.controller.DistanceCalcThread;

/**
 * Created by Hinaskye on 6/10/2017.
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
