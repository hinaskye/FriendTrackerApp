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
}
