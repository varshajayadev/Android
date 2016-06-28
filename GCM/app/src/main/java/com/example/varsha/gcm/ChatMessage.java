package com.example.varsha.gcm;

/**
 * Created by Varsha on 5/7/2016.
 */
public class ChatMessage {

    public boolean left;
    public String message;
    public String number;
    public ChatMessage(boolean left, String message, String number)    {
        super();
        this.left = left;
        this.message = message;
        this.number = number;
    }

}
