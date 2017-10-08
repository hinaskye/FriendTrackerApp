package mad.friend.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.RandomString;

/**
 * Friend Model singleton class
 * Provides access to a central list of friends and maps the friends to their location if available
 */
public class FriendModel {
    private static FriendModel instances = new FriendModel();

    private List<Friend> friends = new ArrayList<Friend>();
    private Map<String,String> friendLocation = new HashMap<String,String>();
    private double latitude, longitude; //user's current location
    public static boolean isCalcDist = false;

    private FriendModel() {}

    public static FriendModel getInstance()
    {
        return instances;
    }

    // code as shown in lec 6 by Casper
    public Friend getFriendForPosition(int position)
    {
        return friends.get(position);
    }

    public Friend getFriendById(String id)
    {
        for(Friend friend : friends)
            if(friend.getId().equals(id))
                return friend;

        return null;
    }

    public List<Friend> getFriends()
    {
        return friends;
    }

    /**
     * @param friend new friend object to add to list
     * @return true is success otherwise false
     */
    public boolean addFriend(Friend friend)
    {
        // add friend if new id
        boolean notInFriendList = true;
        for(Friend friendInList : friends)
        {
            if(friend.getId().equals(friendInList.getId()))
            {
                notInFriendList = false;
            }
        }

        if(notInFriendList)
        {
            friends.add(friend);
        }

        return notInFriendList;
        /* // contains does not seem to work when loading friends from database
        if(!friends.contains(friend))
        {
            friends.add(friend);
            return true;
        }
        else
        {
            System.err.printf("FriendModel: Friend %s already in list", friend.toString());
            return false;
        }*/
    }

    /**
     * Remove a friend from the list by id
     */
    public boolean removeFriend(String id)
    {
        for(Friend friend : friends)
        {
            if(friend.getId().compareTo(id)==0)
            {
                friends.remove(friend);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return Map of friend's id (key) to location (value)
     */
    public Map getFriendLocation()
    {
        return friendLocation;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    /* currently redundant */
    public boolean addFriendLocation(String id, String location)
    {
        friendLocation.put(id,location);
        return true;
    }

    /**
     * Debug
     * prints the map with key value pairs
     */
    public void printFriendLocation()
    {
        System.err.println(Arrays.asList(friendLocation));
    }

    /**
     * Prints all friends in our app
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(Friend friend : friends)
        {
            sb.append(friend.toString());
            sb.append('\n');
        }
        return sb.toString();
    }
}
