package mad.friend.model;

import android.location.Location;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Meeting model
 */
public class Meeting implements MeetingInterface {
    private String id;
    private String title;
    private Date date;
    private Time startTime;
    private Time endTime;
    private List<Friend> friendsInvited = new ArrayList<>();
    private double latitude;
    private double longitude;

    // Upon init need to specify a unique id
    public Meeting() {}

    public Meeting(String id)
    {
        this.id = id;
    }

    public Meeting(String title, Time startTime, Time endTime)
    {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Meeting(String id, String title, Time startTime, Time endTime,
                   List<Friend> invited)
    {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        friendsInvited = invited;
    }

    public Meeting(String id, String title, Date date, Time startTime, Time endTime,
                   List<Friend> invited, double latitude, double longitude)
    {
        this.id = id;
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        friendsInvited = invited;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Meeting(String id, String title, Date date, Time startTime, Time endTime,
                   double latitude, double longitude)
    {
        this.id = id;
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Date getDate()
    {
        return date;
    }

    @Override
    public void setDate(Date date)
    {
        this.date = date;
    }

    @Override
    public Time getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    @Override
    public Time getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public List<Friend> getInvited() {
        return friendsInvited;
    }

    @Override
    public void addFriend(Friend friend) {
        friendsInvited.add(friend);
    }

    @Override
    public void removeFriend(Friend friend) {
        friendsInvited.remove(friend);
    }

    @Override
    public double getLatitude()
    {
        return latitude;
    }

    @Override
    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    @Override
    public double getLongitude()
    {
        return longitude;
    }

    @Override
    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    /**
     * @return String containing meeting info minus friends of the meeting.
     */
    public String toString()
    {
        if(startTime!=null && endTime!=null && date!=null)
        {
            return String.format("id: %s\ntitle: %s\ndate: %d\nstart time: %d\n" +
                            "end time: %d\nlatitude: %f\n longitude: %f", id, title, date.getTime(),
                    startTime.getTime(), endTime.getTime(), latitude, longitude);
        }
        return "false";
    }

    /**
     * Debugging will print all friends added to this meeting
     */
    public void printMeetingFriends()
    {
        for(Friend friend : friendsInvited)
        {
            System.out.println("Meeting printMeetingFriends():\n"+friend.toString());
        }
    }
}
