package mad.friend.model;

import android.location.Location;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Meeting interface
 */
public interface MeetingInterface {
    public String getId();
    public String getTitle();
    public void setTitle(String title);

    public Date getDate();
    public void setDate(Date date);
    public Time getStartTime();
    public void setStartTime(Time startTime); //will need to add mmddyy hhmmss
    public Time getEndTime();
    public void setEndTime(Time endTime); //will need to add mmddyy hhmmss

    public List<Friend> getInvited();
    public void addFriend(Friend friend);
    public void removeFriend(Friend friend);

    public double getLatitude();
    public void setLatitude(double latitude);
    public double getLongitude();
    public void setLongitude(double longitude);
}
