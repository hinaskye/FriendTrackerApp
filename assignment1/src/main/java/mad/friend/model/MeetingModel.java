package mad.friend.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Meeting Model singleton class
 * provides a list of meetings
 */
public class MeetingModel {
    private static final MeetingModel ourInstance = new MeetingModel();

    private List<Meeting> meetings = new ArrayList<Meeting>();

    public static MeetingModel getInstance()
    {
        return ourInstance;
    }

    private MeetingModel()
    {

    }

    public List<Meeting> getMeetings()
    {
        return meetings;
    }

    public boolean addMeeting(Meeting meeting)
    {
        boolean notInList = true;
        for(Meeting meetingInList : meetings)
        {
            if(meetingInList.getId().equals(meeting.getId()))
            {
                notInList = false;
            }
        }

        if(notInList)
        {
            meetings.add(meeting);
        }

        return notInList;

        /* // May error as with adding friend to model
        if(!meetings.contains(meeting))
        {
            meetings.add(meeting);
            return true;
        }
        else
        {
            System.err.printf("Meeting already in list:%s", meeting.toString());
            return false;
        }*/
    }
}
