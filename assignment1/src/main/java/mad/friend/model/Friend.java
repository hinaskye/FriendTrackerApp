package mad.friend.model;

import java.util.Date;

/**
 * Friend class
 */
public class Friend implements FriendInterface {

    private String id;
    private String name;
    private String email;
    private Date birthday;
    //add birthday and optional photo

    public Friend(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Friend(String id, String name, String email)
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public String toString() {
        return String.format("id %s name %s email %s",getId(), getName(), getEmail());
    }
}
