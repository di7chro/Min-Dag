package se.hv.mindag;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * User: imcoh
 * Date: 2013-03-01
 * Time: 12:53
 */
public class SettingsMail extends Activity {
    public static final String PREFS_NAME = "MyPrefsFile";

    private RadioGroup rg;
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_mail);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String savedMail = settings.getString("MAIL", "");
        rg = (RadioGroup) findViewById(R.id.settingsRadioMail);

        RadioButton rbGmail = (RadioButton) findViewById(R.id.settingsRadioGmail);
        RadioButton rbLive = (RadioButton) findViewById(R.id.settingsRadioLive);
        RadioButton rbEget = (RadioButton) findViewById(R.id.settingsRadioEget);

        if (savedMail.contentEquals(this.getString(R.string.settingMailGmail))) {
            rbGmail.setSelected(true);
            rbLive.setSelected(false);
            rbEget.setSelected(false);

        } else if (savedMail.contentEquals(this.getString(R.string.settingMailLive))) {
            rbGmail.setSelected(false);
            rbLive.setSelected(true);
            rbEget.setSelected(false);
        } else {
            rbGmail.setSelected(false);
            rbLive.setSelected(false);
            rbEget.setSelected(true);
            EditText egenURL = (EditText) findViewById(R.id.settingsMailEgetText);
            egenURL.setText(savedMail);
        }


        b = (Button) findViewById(R.id.settingsMailButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rg = (RadioGroup) findViewById(R.id.settingsRadioMail);
                EditText egenURL = (EditText) findViewById(R.id.settingsMailEgetText);

                // get selected radio button from radioGroup
                int selectedId = rg.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                b = (RadioButton) findViewById(selectedId);

                String chosen = b.getText().toString();
                String mailURL;
                if (chosen.contains("GMail"))
                    mailURL = "http://mail.google.com";
                else if (chosen.contains("Live"))
                    mailURL = "http://login.live.com";
                else if (chosen.contains("Annat"))
                    mailURL = egenURL.getText().toString();
                else
                    mailURL = "http://live.com";

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("MAIL", mailURL);

                editor.commit();

                Intent go = new Intent(getApplicationContext(), Start.class);
                startActivity(go);
            }
        });
    }
}
