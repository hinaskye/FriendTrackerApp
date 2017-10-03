package mad.friend.controller.meeting;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hinaskye.assignment1.R;
import mad.friend.model.Meeting;

/**
 * Displays the content of the meeting listview based on our meeting model class
 */
public class MeetingListAdapter extends ArrayAdapter<Meeting> {

    private List<Meeting> meetings;

    public MeetingListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        meetings = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View newView = convertView;

        // inflat if null
        if(newView == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            newView = inflater.inflate(R.layout.meeting_list_content, null);
        }

        Meeting currentMeeting = meetings.get(position);

        // Set view of current friend object
        if(currentMeeting != null)
        {
            // More information to be added
            TextView title = (TextView) newView.findViewById(R.id.meeting_title_text);
            TextView startTime = (TextView) newView.findViewById(R.id.meeting_start_time_text);

            if(title != null)
            {
                title.setText(currentMeeting.getTitle());
            }

            if(startTime != null)
            {
                startTime.setText(currentMeeting.getStartTime().toString());
            }
        }

        return newView;
    }
}
