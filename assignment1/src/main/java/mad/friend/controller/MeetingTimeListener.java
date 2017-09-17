package mad.friend.controller;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import mad.friend.model.Meeting;

public class MeetingTimeListener implements View.OnClickListener, TimePickerDialog.OnTimeSetListener
{
    Activity current;
    TextView editText;
    Meeting meeting;
    int desiredTime;
    int startTime = 0, endTime = 1;

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

        if(desiredTime == startTime)
        {
            meeting.setStartTime(timeSet);
        }
        else if(desiredTime == endTime)
        {
            meeting.setEndTime(timeSet);
        }
    }
}
