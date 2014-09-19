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

package com.betaturtle.calendar;

import android.util.Log;
import android.view.View;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

/**
 * Asynchronously updates a calendar with a progress dialog.
 * 
 * @author Yaniv Inbar
 */
class AsyncUpdateCalendar extends CalendarAsyncTask {

  private final String calendarId;
  private final Calendar entry;

  AsyncUpdateCalendar(CalendarSampleActivity calendarSample, String calendarId, Calendar entry) {
    super(calendarSample);
    this.calendarId = calendarId;
    this.entry = entry;
  }

  @Override
  protected void doInBackground() throws IOException {
    try {
      Calendar updatedCalendar =
          client.calendars().patch(calendarId, entry).setFields(CalendarInfo.FIELDS).execute();
      Log.w("Calendar ID", calendarId);
        
        EventCreate(calendarId, entry.getSummary());
    } catch (GoogleJsonResponseException e) {
      // 404 Not Found would happen if user tries to delete an already deleted calendar
      if (e.getStatusCode() != 404) {
        throw e;
      }
      model.remove(calendarId);
    }
  }

    public void EventCreate(String calendarId, String entry) throws IOException {
        Event event = newEvent(entry);
        Event result = client.events().insert(calendarId, event).execute();
        Log.w("Result", result.toString());
    }

    private static Event newEvent(String summary) {
        Event event = new Event();
        event.setSummary(summary);

        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 5*60*1000);
        DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
        event.setStart(new EventDateTime().setDateTime(start));
        DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
        event.setEnd(new EventDateTime().setDateTime(end));
        return event;
    }
}
