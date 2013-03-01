package se.hv.mindag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Start-Up activity giving the user a Login-button plus a feed of News
 * 
 * @author imcoh
 */
public class Start extends ListActivity {
	/**
	 * Define the keys in the XML-feed we're interested in
	 */
	static final String KEY_ITEM = "item"; // parent node
	static final String KEY_LINK = "link";
	static final String KEY_TITLE = "title";
	static final String KEY_DESC = "description";
	static final String KEY_DATE = "dc:date";

	// The feed we want to read
	static final String URL = "http://www.hv.se/feeds/nyheter.xml";

	/**
	 * Checks whether the device is connected to the Internet or not.
	 * 
	 * @return true if connected, otherwise false
	 */
	public boolean isOnline() {
		final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
				|| conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Shows a dialog and the exists the app
	 */
	public void graceFullyExit() {
		Log.e("NET", "No network.");
		final AlertDialog.Builder myBuild = new AlertDialog.Builder(Start.this);
		myBuild.setTitle("Ingen anslutning");
		myBuild.setMessage("Du är inte ansluten till Internet. Utan en Internetanslutning kan du väl inte begära att jag ska kunna hämta din information?");
		myBuild.setNeutralButton("OK", new DialogInterface.OnClickListener() {

		
			public void onClick(DialogInterface dialog, int which) {
				Log.i("Start", "Kommit hit");
				finish();
			}
		});
		myBuild.show();
	}

	/**
	 * Fires up the activity_start and waits for the Login-button to be pressed.
	 * Then it starts the ASynkTask to gather the XML-feed in the background.
	 * When this is done we can populate the ListAdapter with the stuff from the
	 * feed.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		if (!isOnline()) {
			graceFullyExit();
		} else {
			Log.i("NET", "Vi har nätverk.");
			Toast.makeText(getApplicationContext(), "Laddar data...",
					Toast.LENGTH_LONG).show();

			Button login = (Button) findViewById(R.id.btnLogin);
			login.setOnClickListener(new OnClickListener() {

			
				public void onClick(View v) {
					// Login-button pressed. Start the Login-activity
					Intent myIntent = new Intent(Start.this, Login.class);
					Start.this.startActivity(myIntent);
				}

			});

			try {
				// Initiate the ASynkTask
				XMLDataHandler generalNews = new XMLDataHandler();

				// Start the task and give it the URL as input
				generalNews.execute(URL);

				// Create a local ArrayList
				ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

				// Fill the ArrayList with the items we got from the ASynkTask
				menuItems = generalNews.get();

				// Add the menuItems to our ListView
				ListAdapter adapter = new SimpleAdapter(this, menuItems,
						R.layout.activity_generic, new String[] { KEY_TITLE,
								KEY_LINK, KEY_DESC, KEY_DATE }, new int[] {
								R.id.generalTitle, R.id.generalLink,
								R.id.generalDescription, R.id.generalDate });

				setListAdapter(adapter);

				// selecting single ListView item
				ListView lv = getListView();

				menuItems = null;

				// Wait for an item in the list to be clicked
				lv.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Toast.makeText(getApplicationContext(),
								"Laddar data...", Toast.LENGTH_LONG).show();
						// getting values from selected ListItem
						String link = ((TextView) view
								.findViewById(R.id.generalLink)).getText()
								.toString();

						// Starting new intent
						Intent in = new Intent(getApplicationContext(),
								WebReader.class);

						// Pass the URL to the new Activity
						in.putExtra("KEY_LINK", link);
						startActivity(in);
					}
				});

			} catch (InterruptedException e) {
				Log.e("IE", "Interrupted");
				e.printStackTrace();
			} catch (ExecutionException e) {
				Log.e("EXE", "Execution");
				e.printStackTrace();
			} catch (Exception e) {
				System.out
						.println("============= MOTHER OF ALL ERRORS IN START ================");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Displays the menu for the startup screen
	 */
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.menu_start, menu);
		return true;
	}

	/**
	 * Handles the different kinds of buttons which can be pressed in the current
	 * Activitys menu
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuAbout:
			Log.i("Menu", "About");
			Intent showAbout = new Intent("se.hv.mindag.ABOUT");
			startActivity(showAbout);
			break;

		case R.id.menuPrefs:
			AlertDialog.Builder settingsPopup = new AlertDialog.Builder(this);
			settingsPopup.setTitle("Inställningar");
			settingsPopup
					.setMessage("Jättekul att du tryckte på den där knappen. Verkligen. Synd bara att den inte gör något. Hade den gjort något hade det säkert varit något fantastiskt som hade hänt.");
			settingsPopup.setNeutralButton("OK", null);
			settingsPopup.show();

			break;

		case R.id.menuTwitter:
			Intent showTwitter = new Intent("se.hv.mindag.TWITTER");
			startActivity(showTwitter);

			break;
		}
		return false;
	}
}
