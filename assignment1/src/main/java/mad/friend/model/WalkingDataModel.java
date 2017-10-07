package mad.friend.model;

import java.util.ArrayList;
import java.util.List;

/**
 * WalkingData singleton class
 * Contains a list of WalkingData objects for central access
 */
public class WalkingDataModel {
    private static final WalkingDataModel instances = new WalkingDataModel();
    public static final int you = 1;
    public static final int theirs = 2;
    private List<WalkingData> walkingList = new ArrayList<>();
    public static int currentIndex = 0;
    private int listSize;

    public static WalkingDataModel getInstance()
    {
        return instances;
    }

    public List<WalkingData> getWalkingList()
    {
        return walkingList;
    }

    /**
     * Insert by sorting list in place, by order of max walking time between you and your friend
     * @param walkingData
     * @return true if added to model
     */
    public boolean addToWalkingList(WalkingData walkingData)
    {
        boolean added = false;
        if(walkingList.isEmpty())
        {
            walkingList.add(walkingData);
            return true;
        }
        else
        {
            for(WalkingData item : walkingList)
            {
                if(walkingData.maxWalkingTime()<item.maxWalkingTime())
                {
                    walkingList.add(walkingList.indexOf(item), walkingData);
                    added = true;
                    return true;
                }
            }
        }
        if(!added)
        {
            // Add to end of list
            walkingList.add(walkingData);
            return true;
        }
        return false;
    }// addToWalkingList

    public void incrementIndex()
    {
        currentIndex++;
    }

    public void resetIndex()
    {
        currentIndex = 0;
    }

    public int getListSize()
    {
        listSize = walkingList.size();
        return listSize;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(WalkingData data : walkingList)
        {
            sb.append(data.toString());
        }
        return sb.toString();
    }
}
