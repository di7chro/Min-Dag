package se.hv.mindag;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Start-Up activity giving the user a Login-button plus a feed of News
 *
 * @author imcoh
 */
public class MyDay extends ListActivity {
    /**
     * Define the keys in the XML-feed we're interested in
     */
    static final String KEY_ITEM = "item"; // parent node
    static final String KEY_LINK = "link";
    static final String KEY_TITLE = "title";
    static final String KEY_DESC = "description";
    static final String KEY_DATE = "pubDate";
    static final String KEY_TAG = "tag";

    /*
     * Fires up the activity_myday and waits for the Login-button to be pressed.
     * Then it starts the ASynkTask to gather the XML-feed in the background.
     * When this is done we can populate the ListAdapter with the stuff from the
     * feed.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent in = getIntent();

        // Den personliga URL innehållande URL samt app_key=HEMLIG_HASH
        String url = in.getExtras().getString("URL");

        // Riktiga namnet på den inloggade användaren
        String realName = in.getExtras().getString("REALNAME");

        try {
            // Initiate the ASynkTask
            MyDayHandler myDayNews = new MyDayHandler();

            // Start the task and give it the URL as input
            myDayNews.execute(url);

            // Create a local ArrayList
            ArrayList<HashMap<String, String>> myDayItems = new ArrayList<HashMap<String, String>>();

            // Fill the ArrayList with the items we got from the ASynkTask
            myDayItems = myDayNews.get();

            // Add the menuItems to our ListView
            ListAdapter adapter = new SimpleAdapter(this, myDayItems,
                    R.layout.activity_myday_list, new String[]{KEY_TITLE,
                    KEY_LINK, KEY_DESC, KEY_DATE, KEY_TAG}, new int[]{
                    R.id.mydayTitle, R.id.mydayLink,
                    R.id.mydayDescription, R.id.mydayDate,
                    R.id.mydayTag});
            setListAdapter(adapter);

            ListView lv = (ListView) findViewById(R.id.myDayList);
            for (int i = 0; i <= myDayItems.size(); i++) {
                String theTag = myDayItems.get(i).get(KEY_TAG);
                if (theTag.contains("schema")) {
                    Log.i("TAG", "Schema hittad");
                    // TextView tv = (TextView) myDayItems.get(i);
                } else if (theTag.contains("kronox"))
                    Log.i("TAG", "Kronox hittad");
                else if (theTag.contains("ladok"))
                    Log.i("TAG", "Ladok hittad");
                else if (theTag.contains("disco"))
                    Log.i("TAG", "Disco hittad");
                else
                    Log.i("TAG ANNAT", theTag);
            }
            // Wait for an item in the list to be clicked
            lv.setOnItemClickListener(new OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // getting values from selected ListItem
                    String link = ((TextView) view.findViewById(R.id.mydayLink))
                            .getText().toString();

                    // Starting new intent
                    Intent in = new Intent(getApplicationContext(),
                            WebReader.class);

                    // Pass the URL to the new Activity
                    in.putExtra("KEY_LINK", link);
                    startActivity(in);
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out
                    .println("============= MOTHER OF ALL ERRORS IN MYDAY ================");
            e.printStackTrace();
        }
    }

    /**
     * Displays the menu for the startup screen
     */
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater blowUp = getMenuInflater();
        blowUp.inflate(R.menu.menu_loggedin, menu);
        return true;
    }

    /**
     * Handles the different kinds of buttons wich can be pressed in the current
     * Activitys menu
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSave:
                AlertDialog.Builder savePopup = new AlertDialog.Builder(this);
                savePopup.setTitle(R.string.menuSaveTitle);
                savePopup
                        .setMessage(R.string.menuSaveText);
                savePopup.setNeutralButton("OK", null);
                savePopup.show();

                break;

            case R.id.menuMail:
                AlertDialog.Builder mailPopup = new AlertDialog.Builder(this);
                mailPopup.setTitle(R.string.menuMailTitle);
                mailPopup
                        .setMessage(R.string.menuMailText);
                mailPopup.setNeutralButton("OK", null);
                mailPopup.show();

                break;

        }
        return false;
    }
}
