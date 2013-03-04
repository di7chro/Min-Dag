package se.hv.mindag;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * User: imcoh
 * Date: 2013-03-01
 * Time: 12:54
 */
public class SettingsLogin extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_login);
        Button okButton = (Button) findViewById(R.id.settingLoginButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SettingsLogin", "OK-knappen tryckt, spara uppgifterna");

            }
        });
    }
}
