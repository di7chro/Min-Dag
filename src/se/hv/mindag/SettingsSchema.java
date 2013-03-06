package se.hv.mindag;

import android.app.Activity;
import android.os.Bundle;

/**
 * File: se.hv.mindag in project Min Dag
 * **************************************************************
 * Description:
 * <p/>
 * User: crille
 * Date: 2013-03-06
 * Time: 21:18
 */
public class SettingsSchema extends Activity {
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_schema);

    }
}
