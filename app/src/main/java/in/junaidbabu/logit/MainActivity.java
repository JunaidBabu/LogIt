package in.junaidbabu.logit;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity {
    static MySQLiteHelper db;
    com.cengalabs.flatui.views.FlatEditText logbox;
    com.cengalabs.flatui.views.FlatButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MySQLiteHelper(this);
        setContentView(R.layout.activity_main);
        FlatUI.setDefaultTheme(FlatUI.GRASS);
        FlatUI.setActionBarTheme(this, FlatUI.GRASS, false, false);
        logbox = (com.cengalabs.flatui.views.FlatEditText)findViewById(R.id.logbox);
        button = (com.cengalabs.flatui.views.FlatButton)findViewById(R.id.button);
        logbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0) {
                    button.setEnabled(false);
                }
                else {
                    button.setEnabled(true);
                    button.setText("Log this");
                }
            }
        });

    }
    public void Logthis(View v){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String Text = logbox.getText().toString();
        String Type = "Log";
        String Time = sdf.format(new Date());
        db.addEntry(new dataClass(Type, Time, Text));
        Toast.makeText(this, Type+" "+Time+" "+Text, Toast.LENGTH_SHORT).show();
        logbox.setText("");
        button.setText("Logged!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
