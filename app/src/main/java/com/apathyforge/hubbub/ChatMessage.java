package com.apathyforge.hubbub;

import java.util.Date;

public class ChatMessage
{
    private String userName;
    private String message;
    private Long messageTime;

    public ChatMessage(String userIn, String messageIn)
    {
        userName = userIn;
        message = messageIn;
        //set time
        messageTime = new Date().getTime();
    }

    public ChatMessage()
    {

    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String messageIn)
    {
        message = messageIn;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userNameIn)
    {
        userName = userNameIn;
    }

    public long getMessageTime()
    {
        return messageTime;
    }

    public void setMessageTime(Long messageTimeIn)
    {
        messageTime = messageTimeIn;
    }

}
