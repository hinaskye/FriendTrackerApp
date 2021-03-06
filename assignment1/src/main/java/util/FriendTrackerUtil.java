package util;

import java.util.List;

import mad.friend.model.Friend;
import mad.friend.model.FriendModel;

/**
 * Friend Tracker app utility class
 */
public class FriendTrackerUtil {

    //Result code
    public static final int EDIT_FRIEND = 1;
    public static final int MEETING_ADD_FRIEND = 2;
    public static final int UPCOMING_MEETING_DISMISS = 3;
    public static final int UPCOMING_MEETING_REMIND = 4;
    public static final int UPCOMING_MEETING_CANCEL = 5;
    public static final int NOTIFY_OF_NOTIFICATION = 6;
    public static final int REPEAT_NOTIFICATION = 7;
    public static final int LOCATION_PERMISSION = 8;
    public static final int LOCATION_CHANGED = 9;
    public static final int SUGGEST_NOW_NOTIFICATON_ID = 10;
    public static final int CREATE_SUGGESTTED_MEETING = 11;
    public static final int SUGGEST_NOW_NOTIFICATION = 12;
    public static final int SUGGEST_NOW_NOTIFICATION_DISMISS = 13;
    public static final int ALARM_FOR_MEETING = 14;

    /**
     * Constructor
     */
    public FriendTrackerUtil()
    {

    }

    /**
     * @return a unique id of int length
     */
    public static String uniqueId(int length)
    {
        boolean unique = false;
        List<Friend> friends = FriendModel.getInstance().getFriends();
        RandomString newId = new RandomString(length);

        while(!unique)
        {
            // assume unique first then check if not unique
            unique = true;
            for(Friend friend : friends)
            {
                // if generated id is already assigned then not unique
                if(friend.getId().compareTo(newId.getString())==0)
                {
                    unique = false;
                    // generate new id
                    newId = new RandomString(length);
                }
            }
        }
        return newId.getString();
    }

    /**
     *
     * @param email the email is checked against friend model to see if this person is unique
     *              before adding them to our model
     * @return true if they are unique otherwise false
     */
    public static boolean isUniqueEmail(String email)
    {
        boolean unique = false;
        List<Friend> friends = FriendModel.getInstance().getFriends();

        while(!unique)
        {
            // assume unique first then check if not unique
            unique = true;
            for(Friend friend : friends)
            {
                if (friend.getEmail().compareTo(email) == 0) {
                    unique = false;
                    System.err.printf("isUniqueEmail(): Friend %s already added\n", friend.getName());
                    return false;
                }
            }
        }
        return true;
    }
}
