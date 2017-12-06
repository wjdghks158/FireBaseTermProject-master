package org.techtown.firebasetermproject.calender;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Date;

/**
 * Created by 408 on 2017-12-06.
 */

public class OnDayDecorator implements DayViewDecorator {

    private com.prolificinteractive.materialcalendarview.CalendarDay date;

    public OnDayDecorator() {
        date = com.prolificinteractive.materialcalendarview.CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(com.prolificinteractive.materialcalendarview.CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new RelativeSizeSpan(1.4f));
        view.addSpan(new ForegroundColorSpan(Color.GREEN));
    }

    public void setDate(Date date) {
        this.date = com.prolificinteractive.materialcalendarview.CalendarDay.from(date);

    }
}
