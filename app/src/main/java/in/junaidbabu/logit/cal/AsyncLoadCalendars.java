package in.junaidbabu.logit.cal;

import com.google.api.services.calendar.model.CalendarList;

import java.io.IOException;

import in.junaidbabu.logit.MainActivity;

/**
 * Created by jun on 21/9/14.
 */
public class AsyncLoadCalendars extends CalendarAsyncTask {

    AsyncLoadCalendars(MainActivity calendarSample) {
        super(calendarSample);
    }

    @Override
    protected void doInBackground() throws IOException {
        CalendarList feed = client.calendarList().list().setFields(CalendarInfo.FEED_FIELDS).execute();
        model.reset(feed.getItems());
    }

    public static void run(MainActivity calendarSample) {
        new AsyncLoadCalendars(calendarSample).execute();
    }
}
