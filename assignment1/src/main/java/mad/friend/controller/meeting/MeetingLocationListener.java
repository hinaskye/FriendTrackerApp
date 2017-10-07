package mad.friend.controller.meeting;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;

import mad.friend.model.Meeting;

/**
 * MeetingLocationListener
 * Used for both latitude and longitude changes to their edit text
 * Sets the meeting latitude or longitude after edit text has changed
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

    /**
     *  sets latitude or longitude based on position as shown in constructor
     */
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
