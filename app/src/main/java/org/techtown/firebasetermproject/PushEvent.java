package org.techtown.firebasetermproject;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import java.util.List;

public class PushEvent
{
    // the pushlist object being sent using the bus
    private com.prolificinteractive.materialcalendarview.CalendarDay pushlist;
    private boolean on;
    private int color;

    public PushEvent(com.prolificinteractive.materialcalendarview.CalendarDay pushlist, boolean on, int color)
    {
        this.pushlist = pushlist;
        this.on = on;
        this.color = color;
    }

    /**
     * @return the pushlist
     */
    public CalendarDay getList()
    {
        return pushlist;
    }
    public Boolean getOnoff(){
        return on;
    }
    public int getColor() {return color; }
}