package in.junaidbabu.logit.cal;

/**
 * Created by jun on 22/9/14.
 */
/*
 * Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import android.util.Log;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

import in.junaidbabu.logit.MainActivity;


/**
 * Asynchronously inserts an event a with a progress dialog.
 *
 * @author Junaid Babu
 */
public class AsyncInsertEvent extends CalendarAsyncTask {

    private final String calendarId;
    private final Event entry;

    public AsyncInsertEvent(MainActivity calendarSample, String calendarId, Event entry) {
        super(calendarSample);
        this.calendarId = calendarId;
        this.entry = entry;
    }

    @Override
    protected void doInBackground() throws IOException {
        try {
//            Calendar updatedCalendar =
//                    client.calendars().patch(calendarId, entry).setFields(CalendarInfo.FIELDS).execute();
//            Log.w("Calendar ID", calendarId);
//
//            EventCreate(calendarId, entry.getSummary());
            Event result = client.events().insert(calendarId, entry).execute();
            Log.w("Result", result.toString());
        } catch (GoogleJsonResponseException e) {

        }
    }

//    public void EventCreate(String calendarId, String entry) throws IOException {
//        Event event = newEvent(entry);
//        Event result = client.events().insert(calendarId, event).execute();
//        Log.w("Result", result.toString());
//    }


}
