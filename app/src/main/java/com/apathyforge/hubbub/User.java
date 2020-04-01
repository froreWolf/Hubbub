package com.apathyforge.hubbub;

import com.google.firebase.auth.FirebaseUser;

public class User
{
    private String userName;
    private String userEmail;
    private String userIntro;
    private String userID;



    public User(String userName, String userEmail, String userIntro,
                FirebaseUser user)
    {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userIntro = userIntro;
        userID = user.getUid();

    }

    public User(){}

    public String getUserName()
    {
        return userName;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public String getUserIntro()
    {
        return userIntro;
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    public void setUserIntro(String userIntro)
    {
        this.userIntro = userIntro;
    }

    public void setUserID(String userIDIn)
    {
        userID = userIDIn;
    }
}
