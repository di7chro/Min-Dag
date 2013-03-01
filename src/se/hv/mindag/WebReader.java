package se.hv.mindag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Opens up a new WebView and loads the URL of the chosen element from the feed
 * in the activity_webreader
 * 
 * @author imcoh
 * 
 */

public class WebReader extends Activity {
	WebView webview;

	/*
	 * Creates a WebView, gets the URL from previous activity and starts the
	 * WebView.
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_webreader);
		Intent in = getIntent();
		String url = in.getExtras().getString("KEY_LINK");

		try {
			WebSinglePost gsn = new WebSinglePost();
			gsn.execute(url);

			WebView webview;
			webview = (WebView) findViewById(R.id.webreader);

			String html = gsn.get();
			String mime = "text/html";
			String encoding = "iso-8859-1";
			webview.loadData(html, mime, encoding);

		} catch (Exception e) {
			System.out.println("======== JSOUP Error");
			e.printStackTrace();
		}

	}
}
