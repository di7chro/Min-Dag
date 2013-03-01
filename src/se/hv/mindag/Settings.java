package se.hv.mindag;

import android.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * User: imcoh
 * Date: 2013-03-01
 * Time: 11:29
 */
public class Settings extends ListActivity {
    String items[] = {"Twitter inställningar", "Mail inställningar", "Spara inloggning"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(Settings.this, R.layout.simple_list_item_1, items));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String val = items[position];

        if (position == 0) {
            // Starting new intent
            Intent inTwitter = new Intent("se.hv.mindag.SETTINGSTWITTER");
            startActivity(inTwitter);
        } else if (position == 1) {
            // Starting new intent
            Intent inMail = new Intent("se.hv.mindag.SETTINGSMAIL");
            startActivity(inMail);
        } else if (position == 2) {
            // Starting new intent
            Intent inSave = new Intent("se.hv.mindag.SETTINGSSAVE");
            startActivity(inSave);
        }

    }
}