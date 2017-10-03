package mad.friend.controller.meeting;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;

import mad.friend.model.Meeting;

/**
 * Created by Hinaskye on 3/10/2017.
 */

public class MeetingLocationListener implements TextWatcher {

    Activity current;
    Meeting meeting;
    int latitude = 0, longitude = 1;
    int position;

    /**
     * @param caller Parent activity
     * @param meeting Meeting object to modify
     * @param position Specifies to add either a latitude (0) or longitude (1)
     */
    public MeetingLocationListener(Activity caller, Meeting meeting, int position)
    {
        current = caller;
        this.meeting = meeting;
        this.position = position;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(position == latitude)
        {
            meeting.setLatitude(Double.parseDouble(s.toString()));
        }
        else if(position == longitude)
        {
            meeting.setLongitude(Double.parseDouble(s.toString()));
        }
        else
        {
            System.err.println("Error in MeetingLocationListener, specified position is not" +
                    "a latitude or a longitude");
        }
    }
}
