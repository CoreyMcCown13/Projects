package guessinggame.coreymccown.com.guessinggame_cnm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class Settings extends ActionBarActivity {

    private TextView thisText;
    public static final String PREFS_NAME = "Preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        thisText = (TextView) findViewById(R.id.textView3);
        final SeekBar sk=(SeekBar) findViewById(R.id.seekBar);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
        int curNumber = prefs.getInt("maxNumber", 100);
        sk.setProgress(curNumber);
        thisText.setText("Guess 1-" + sk.getProgress());
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(PREFS_NAME, 0).edit();
                int newNumber = ((progress + 9) / 10 * 10);
                if(newNumber < 10) newNumber = 10;
                thisText.setText("Guess 1-" + newNumber);
                editor.putInt("maxNumber", newNumber);
                editor.commit();
                MainActivity.needReset = true;
            }
        });
        final CheckBox ck=(CheckBox) findViewById(R.id.checkBox);
        boolean isItChecked = prefs.getBoolean("unlimited",false);
        ck.setChecked(isItChecked);
        ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(PREFS_NAME, 0).edit();
                if(ck.isChecked()) {
                    Toast.makeText(getApplicationContext(), "SLACKER!", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("unlimited", true);
                    editor.commit();
                    MainActivity.needReset = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Good Luck!", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("unlimited", false);
                    editor.commit();
                    MainActivity.needReset = true;
                }
            }
        });

        final Button button = (Button) findViewById(R.id.backToGameButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
