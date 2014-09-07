package in.junaidbabu.logit;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.betaturtle.calendar.R;
import com.cengalabs.flatui.FlatUI;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity {
    static MySQLiteHelper db;
    com.cengalabs.flatui.views.FlatEditText logbox;
    com.cengalabs.flatui.views.FlatButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MySQLiteHelper(this);
        setContentView(R.layout.activity_main);
        FlatUI.setDefaultTheme(FlatUI.GRASS);
        //FlatUI.setActionBarTheme(this, FlatUI.GRASS, false, false);
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

}
