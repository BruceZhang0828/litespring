package org.litespring.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepbay on 2018/8/23.
 */
public class MessageTracker {
    private static List<String> MESSAGES = new ArrayList<String>();

    public static void addMsg(String msg){
        MESSAGES.add(msg);
    }
    public static void clearMsgs(){
        MESSAGES.clear();
    }
    public static List<String> getMsgs(){
        return MESSAGES;
    }
}
