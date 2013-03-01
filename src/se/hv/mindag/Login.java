package se.hv.mindag;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Handles the Login-functionality to the University West resources
 * 
 * @author imcoh
 * 
 */
public class Login extends Activity {

	// Login URL to server. if login correct the full user name is returned -
	// else the string "Ingen användare":
	private static final String loginURL = "http://mittkonto.hv.se/public/appfeed/login_response.php?app_key=";
	// Login to min sida returns XML
	private static final String loginXML_URL = "https://mittkonto.hv.se/public/appfeed/app_rss.php?app_key=";

	/*
	 * Starts up the username & password boxes
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Button loginButton = (Button) findViewById(R.id.btnTryLogin);
		loginButton.setOnClickListener(new OnClickListener() {

			/*
			 * The button has been clicked and someone wants to login Get the
			 * information from the textboxes and create a String with theese
			 * two, whicjh is sent to the Hash-maker
			 */
			
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),
						"Kollar dina uppgifter...", Toast.LENGTH_LONG).show();
				// Loginknappen har klickats. Kolla uppgifterna
				EditText username = (EditText) findViewById(R.id.tfUsername);
				EditText password = (EditText) findViewById(R.id.tfPassword);

				String uname = username.getText().toString();
				String pword = password.getText().toString();

				// Concaternate the username with the password
				String userAndPass = uname + pword;

				MessageDigest digest = null;
				String hash;
				try {
					// Select the appropriate Hash-function
					digest = MessageDigest.getInstance("SHA-256");
					digest.update(userAndPass.getBytes());

					// Get the hash-value for the user
					hash = bytesToHexString(digest.digest());

					LoginData loginUser = new LoginData();

					// Appends the hash to the URL
					String loginString = loginURL + hash;

					String[] urlHash = { loginString };

					// call thread tråden and execute doinBackground in
					// Logindata;
					loginUser.execute(urlHash);

					// Get "answer" from LoginData doInBackground with get()
					ArrayList<String> arrayList = loginUser.get();

					// Checks the string returned from loginServer. Displays
					// info to user
					if (arrayList.get(0).contains("Ingen anv")) {
						showWrongLoginPopup();
					} else {
						// loginResult.setText("Välkommen "+arrayList.get(0));
						LoginData loginData = new LoginData();

						// Appends the hash to the URL
						String loginDataStr = loginXML_URL + hash;

						String[] urlDataHash = { loginDataStr };

						// PAss the URL to the data and the users realname to
						// the new Activity
						Intent myIntent = new Intent(Login.this, MyDay.class);
						myIntent.putExtra("URL", urlDataHash[0]);
						myIntent.putExtra("REALNAME", arrayList.get(0));
						startActivity(myIntent);
					}

				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}

				// Malin: tillagt för att fånga ev ytterligare exceptions
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates a hash of the input String
	 * 
	 * @param Username
	 *            and Password
	 * @return The hash-value
	 */
	private static String bytesToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	/**
	 * Displays a popup-windows sayin that the credentials were wrong
	 */
	private void showWrongLoginPopup() {
		AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
		myBuild.setTitle("Fel inloggning");
		myBuild.setMessage("De uppgifter som du skrev in stämmer inte. Logga in med de uppgifter som du fått av IT-enheten vid Högskolan Väst.");
		myBuild.setNeutralButton("OK", null);
		myBuild.show();
	}
}
