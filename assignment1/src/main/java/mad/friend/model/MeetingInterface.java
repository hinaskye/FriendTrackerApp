package mad.friend.model;

import android.location.Location;

import java.util.Date;
import java.util.List;

/**
 * Meeting interface
 */
public interface MeetingInterface {
    public String getId();
    public String getTitle();
    public void setTitle(String title);
    public Date getStartTime();
    public void setStartTime(Date startTime); //will need to add mmddyy hhmmss
    public Date getEndTime();
    public void setEndTime(Date endTime); //will need to add mmddyy hhmmss
    public List<Friend> getInvited();
    public void addFriend(Friend friend);
    public void removeFriend(Friend friend);
    public Location getLocation();
    public void setLocation(Location location);
}
