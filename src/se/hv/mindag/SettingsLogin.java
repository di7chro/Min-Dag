package se.hv.mindag;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * User: imcoh
 * Date: 2013-03-01
 * Time: 12:54
 */
public class SettingsLogin extends Activity {
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_login);
        Button okButton = (Button) findViewById(R.id.settingLoginButton);
        final TextView user = (TextView) findViewById(R.id.settingsUsername);
        final TextView pass = (TextView) findViewById(R.id.settingsPassword);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String savedUsername = settings.getString("USERNAME", "");
        String savedPassword = settings.getString("PASSWORD", "");
        user.setText(savedUsername);
        pass.setText(savedPassword);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("USERNAME", user.getText().toString());
                editor.putString("PASSWORD", pass.getText().toString());

                editor.commit();

                Intent go = new Intent(getApplicationContext(), Start.class);
                startActivity(go);
            }
        });
    }
}
