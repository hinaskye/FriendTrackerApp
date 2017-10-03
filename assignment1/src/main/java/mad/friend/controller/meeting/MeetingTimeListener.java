package mad.friend.controller.meeting;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;

import mad.friend.model.Meeting;

/**
 * Meeting time listener
 * Used for both the start and end time selection for the meeting
 * Opens a TimePicker Dialog and sets meeting time to the selected
 * Handles validation to make sure start time is before end time
 */
public class MeetingTimeListener implements View.OnClickListener, TimePickerDialog.OnTimeSetListener
{
    Activity current;
    TextView editText;
    Meeting meeting;
    int desiredTime;
    int startTime = 0, endTime = 1;

    /**
     * @param caller Parent activity
     * @param editText EditText which asks for time
     * @param meeting Meeting object to modify
     * @param desiredTime specify if setting meeting start time (0) or end time (1)
     */
    public MeetingTimeListener(Activity caller, TextView editText, Meeting meeting, int desiredTime)
    {
        current = caller;
        this.editText = editText;
        this.meeting = meeting;
        this.desiredTime = desiredTime;
    }

    /**
     *
     */
    @Override
    public void onClick(View v) {
        // Current Time
        final Calendar c = Calendar.getInstance();
        int cHour = c.get(Calendar.HOUR_OF_DAY);
        int cMinute = c.get(Calendar.MINUTE);

        // Open Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(current, this, cHour, cMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        editText.setText(hourOfDay + ":" + minute);
        Time timeSet = new Time(hourOfDay, minute, 0);

        // check if user clicked start time or end time
        if(desiredTime == startTime)
        {
            // check if start time is before end time, set if end time is null
            if(meeting.getEndTime() == null ||
                    (meeting.getEndTime()!=null) && (timeSet.getTime() < meeting.getEndTime().getTime()))
            {
                meeting.setStartTime(timeSet);
            }
            else
            {   // display error toast and change text if not before
                Toast.makeText(current, "Start Time must be before End Time!",Toast.LENGTH_SHORT).show();
                editText.setText("");
            }
        }
        else if(desiredTime == endTime)
        {
            // check if end time is after start time, set if start time is null
            if(meeting.getStartTime() == null ||
                    (meeting.getStartTime()!=null) && (timeSet.getTime() > meeting.getStartTime().getTime()))
            {
                meeting.setEndTime(timeSet);
            }
            else
            {
                Toast.makeText(current, "End Time must be before Start Time!",Toast.LENGTH_SHORT).show();
                editText.setText("");
            }
        }
    }
}
