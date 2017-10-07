package mad.friend.controller.meeting;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import mad.friend.model.Meeting;

/**
 * MeetingTitleListener
 * Simply sets the meeting title when edit text has finish changing
 */
public class MeetingTitleListener implements TextWatcher {

    Activity current;
    Meeting meeting;

    /**
     *
     * @param caller
     * @param meeting Meeting whose title is being edited
     */
    public MeetingTitleListener(Activity caller, Meeting meeting)
    {
        current = caller;
        this.meeting = meeting;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {

    }

    /**
     *  set meeting title to user defined title
     */
    @Override
    public void afterTextChanged(Editable s)
    {
        meeting.setTitle(s.toString());
    }
}
