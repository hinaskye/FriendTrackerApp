package mad.friend.model;

import java.util.Date;

/**
 * Friend Interface
 */
public interface FriendInterface {
    public String getName();
    public void setName(String name);
    public String getId();
    public String getEmail();
    public void setEmail(String email);
    public Date getBirthday();
    public void setBirthday(Date birthday);
}
