package mad.friend.model;

import mad.friend.model.Friend;

/**
 * WalkingData models an object that contains your walking time and your friends walking time
 * towards a particular destination as obtained from Google Distance Matrix API
 * ideally destination should be the mid point between you and your friends location
 */
public class WalkingData {
    // In Minutes
    private int yours, theirs;
    private Friend friend;
    private String dest; // may use for future
    private String origin; // may use for future

    public WalkingData()
    {

    }

    public WalkingData(Friend friend)
    {
        this.friend = friend;
    }

    public WalkingData(int yours, int theirs, Friend friend)
    {
        this.yours = yours;
        this.theirs = theirs;
        this.friend = friend;
    }

    public int getYours()
    {
        return yours;
    }

    public void setYours(int yours)
    {
        this.yours = yours;
    }

    public int getTheirs()
    {
        return theirs;
    }

    public void setTheirs(int theirs)
    {
        this.theirs = theirs;
    }

    public Friend getFriend()
    {
        return friend;
    }

    public void setFriend(Friend friend)
    {
        this.friend = friend;
    }

    public int maxWalkingTime()
    {
        return Math.max(yours, theirs);
    }

    public boolean timeIsSet()
    {
        if(yours != 0 && theirs != 0)
        {
            return true;
        }
        return false;
    }

    public String toString()
    {
        return String.format("WalkingData: YourWalkTime:%d FriendWalkTime:%d of Friend:%s",
                yours, theirs, friend.toString());
    }
}
