package org.techtown.firebasetermproject.dto;



/**
 * Created by uncleJ on 2017-12-12.
 */

public class NotiDTO {

    public String to;

    public Notification notification = new Notification();
    public Data data = new Data();

    public static class Notification {
        public String title;
        public String text;

    }
    public static class Data{
        public String title;
        public String text;
    }
}
