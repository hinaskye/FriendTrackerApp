package mad.friend.model;

import android.location.Location;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Meeting model
 */
public class Meeting implements MeetingInterface {
    private String id;
    private String title;
    private Date startTime;
    private Date endTime;
    private List<Friend> friendsInvited;
    private Location location;

    public Meeting() {}

    public Meeting(String title)
    {
        this.title = title;
    }

    public Meeting(String id, String title, Date startTime, Date endTime,
                   List<Friend> invited, Location location)
    {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        friendsInvited = invited;
        this.location = location;
    }

    @Override
    public String getId() {
        return id;
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
    public Date getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public Date getEndTime() {
        return null;
    }

    @Override
    public void setEndTime(Date endTime) {
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
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }
}
