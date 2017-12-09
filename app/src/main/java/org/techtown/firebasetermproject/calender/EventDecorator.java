package org.techtown.firebasetermproject.calender;

import com.prolificinteractive.materialcalendarview.*;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

public class EventDecorator implements DayViewDecorator {

    private int color;
    private final com.prolificinteractive.materialcalendarview.CalendarDay day;

    public EventDecorator(int color, com.prolificinteractive.materialcalendarview.CalendarDay day) {
        this.color = color;
        this.day = day;
    }

    @Override
    public boolean shouldDecorate(com.prolificinteractive.materialcalendarview.CalendarDay day) {
        return (this.day.equals(day));
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(8, color));
    }
}