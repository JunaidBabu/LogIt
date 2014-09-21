package in.junaidbabu.logit;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.betaturtle.calendar.R;
import com.cengalabs.flatui.FlatUI;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

import in.junaidbabu.logit.cal.AsyncInsertEvent;
import in.junaidbabu.logit.cal.AsyncLoadCalendars;
import in.junaidbabu.logit.cal.CalendarModel;
import in.junaidbabu.logit.misc.MyLocation;




public class MainActivity extends Activity {
    static MySQLiteHelper db;
    TextView debuglog;
    com.cengalabs.flatui.views.FlatEditText logbox;
    com.cengalabs.flatui.views.FlatButton button;
    int flag=0;
    String start, end, latlong="0,0";


    public int numAsyncTasks;
    GoogleAccountCredential credential;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String PREF_CAL_ID = "CalId";
    public static final String TAG = "TAG Title";
    public static final int REQUEST_AUTHORIZATION = 1;
    private static final int REQUEST_ACCOUNT_PICKER = 2;
    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 0;
    public CalendarModel model = new CalendarModel();
    public com.google.api.services.calendar.Calendar client;
    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MySQLiteHelper(this);

        setContentView(R.layout.activity_main);
        FlatUI.setDefaultTheme(FlatUI.GRASS);
        debuglog = (TextView) findViewById(R.id.debuglogs);
        credential =
                GoogleAccountCredential.usingOAuth2(this, Collections.singleton(CalendarScopes.CALENDAR));
        settings = getPreferences(Context.MODE_PRIVATE);

        debuglog.setText(debuglog.getText()+settings.getString(PREF_CAL_ID, null)+"\n");

        credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
        client = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, credential).setApplicationName("Google-CalendarAndroidSample/1.0")
                .build();

        //FlatUI.setActionBarTheme(this, FlatUI.GRASS, false, false);
        logbox = (com.cengalabs.flatui.views.FlatEditText)findViewById(R.id.logbox);
        button = (com.cengalabs.flatui.views.FlatButton)findViewById(R.id.button);
        try {
            logbox.setText(db.getAllEntries().get(0).getEverything());
        }catch (Exception e){}

        logbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0) {
                    button.setEnabled(false);
                    flag=0;
                }
                else {
                    if(flag==0){
                        start = new Date().toString();
                    }
                    flag=1;
                    button.setEnabled(true);
                    button.setText("Log this");
                }
            }
        });


        new MyLocation().getLocation(getApplicationContext(), new MyLocation.LocationResult(){
            @Override
            public void gotLocation (Location location) {
                latlong = location.getLatitude()+","+location.getLongitude();
                Log.w("latlong", latlong);
            }
        });

    }

    private static Event newEvent() {
        Event event = new Event();
        event.setDescription(db.getAllEntries().get(0).getDatatext());
        event.setSummary(db.getAllEntries().get(0).getDatatext());
        event.setLocation(db.getAllEntries().get(0).getLatlong());

        Date startDate = new Date(db.getAllEntries().get(0).getStart());
        Date endDate = new Date(db.getAllEntries().get(0).getEnd());
        DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
        event.setStart(new EventDateTime().setDateTime(start));
        DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
        event.setEnd(new EventDateTime().setDateTime(end));
        return event;
    }
    public void SyncTest(View v){
        Toast.makeText(this, "Syncing", Toast.LENGTH_SHORT).show();
        Event event = newEvent();
        new AsyncInsertEvent(this, settings.getString(PREF_CAL_ID, null), event).execute();
    }
    public void Logthis(View v){
        end = new Date().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String Text = logbox.getText().toString();
        String Type = "Log";
        String Time = sdf.format(new Date());

        db.addEntry(new dataClass(start, end, Type, latlong, Text, "", Time));
        Toast.makeText(this, Type+" "+Time+" "+Text, Toast.LENGTH_SHORT).show();
        logbox.setText("");
        button.setText("Logged!");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        credential.setSelectedAccountName(accountName);
                        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);

                        editor.commit();
                        AsyncLoadCalendars.run(this);
                        debuglog.setText(debuglog.getText()+accountName);
                    }
                }
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
               // AsyncLoadCalendars.run(this);
                break;
            case R.id.menu_accounts:
                chooseAccount();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshView() {
        String callist = null;
        for(int i=0; i<model.toSortedArray().length; i++){
            callist+=" " + model.toSortedArray()[i].summary;
        }
        debuglog.setText(debuglog.getText()+"\n"+callist);

        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_CAL_ID, model.toSortedArray()[3].id);
        editor.commit();
    }

    private void chooseAccount() {
        startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }


}
