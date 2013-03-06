package se.hv.mindag;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * User: imcoh
 * Date: 2013-03-01
 * Time: 12:30
 */
public class SettingsTwitter extends Activity {
    public static final String PREFS_NAME = "MyPrefsFile";
    private RadioGroup rg;
    private Button b;

    /**
     * OBS! Allt som är bortkommenterat i denna fil har att göra med det tredje valet
     * man kan göra i Twitterströmmen, nämligen en egen sökterm. Jag har inte riktigt l
     * öst hur jag skall göra detta, så jag sparar det till senare. Koden är i alla fall
     * nästan klar, men som sagt: jag kommenterar bort det så länge.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_twitter);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String savedTwitter = settings.getString("TWITTER", "");
        rg = (RadioGroup) findViewById(R.id.settingsRadioTwitter);

        RadioButton rbOfficial = (RadioButton) findViewById(R.id.settingsTwitterOfficial);
        RadioButton rbTaggat = (RadioButton) findViewById(R.id.settingsTwitterTaggat);
        //RadioButton rbEget = (RadioButton) findViewById(R.id.settingTwitterEget);
        //Toast.makeText(getApplicationContext(), "Laddat in " + savedTwitter,
        //        Toast.LENGTH_LONG).show();

        if (savedTwitter.contains("officiella")) {
            rbOfficial.setSelected(true);
            rbTaggat.setSelected(false);
            //rbEget.setSelected(false);

        } else if (savedTwitter.contains("Taggat")) {
            rbOfficial.setSelected(false);
            rbTaggat.setSelected(true);
            //rbEget.setSelected(false);
        }
        /*else {
            rbOfficial.setSelected(false);
            rbTaggat.setSelected(false);
            rbEget.setSelected(true);
            EditText egenURL = (EditText) findViewById(R.id.settingsTwitterEgenText);
            egenURL.setText(savedTwitter);
        }
        */


        b = (Button) findViewById(R.id.settingTwitterOKButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rg = (RadioGroup) findViewById(R.id.settingsRadioTwitter);
                //EditText egenURL = (EditText) findViewById(R.id.settingsTwitterEgenText);

                // get selected radio button from radioGroup
                int selectedId = rg.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                b = (RadioButton) findViewById(selectedId);
                String chosen = b.getText().toString();
                //Toast.makeText(getApplicationContext(), chosen,
                //        Toast.LENGTH_LONG).show();

                //String baseURL = "http://search.twitter.com/search.json?q=";
                String twitterSearch = "";
                if (chosen.contains("officiella"))
                    twitterSearch += "http://search.twitter.com/search.json?q=from:University_West";
                else if (chosen.contains("Taggat"))
                    twitterSearch += "http://search.twitter.com/search.json?q=hogskolanvast";
                //else if (chosen.contains("Egen"))
                //    twitterSearch += egenURL.getText().toString();

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("TWITTER", twitterSearch);

                editor.commit();

                Intent go = new Intent(getApplicationContext(), Start.class);
                startActivity(go);
            }
        });
    }

}
