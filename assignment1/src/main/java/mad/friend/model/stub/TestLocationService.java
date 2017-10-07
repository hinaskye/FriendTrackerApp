package mad.friend.model.stub;

import android.content.Context;
import android.util.Log;

import java.util.Date;
import java.util.List;

// Support code by Caspar for MAD assignment s2 2017
// Simple example to test dummy location service (for demonstration only)
// Usage: add class in appropriate package (also see TestLocationService.java)
//       TestLocationService.test(this); // pass a valid Context (NOTE: Activity extends Context)
public class TestLocationService
{
    private static final String LOG_TAG = DummyLocationService.class.getName();

    // call this method to run simple hard coded test
    public static void test(Context context)
    {
        DummyLocationService dummyLocationService = DummyLocationService.getSingletonInstance(context);
        Log.i(LOG_TAG, "File Contents:");
        dummyLocationService.logAll();

        // 2 mins either side of 9:46:30 AM
        // note the relaxed precondition for Assignment 2 can pass any type of Date()
        // but will need to change system clock or dummy_data.txt to get a match!
        List<DummyLocationService.FriendLocation> matched = dummyLocationService
                .getFriendLocationsForTime(new Date(), 2, 0);
        Log.i(LOG_TAG, "Matched Query:");
        dummyLocationService.log(matched);

        // Will add any matched result of friend's location to our friend model
        for(DummyLocationService.FriendLocation match : matched)
        {
            match.addToFriendModel();
        }
    }
}
