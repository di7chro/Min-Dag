package se.hv.mindag;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

/**
 * Makes a connection the the URL of the HV-website and by using JSOUP we can
 * point out wich DIV-tag we want to retrive. The result is stored in a String,
 * wich is returned as a result.
 * 
 * @param The
 *            URL of a certain website
 * @return The String of text contained in a specific DIV-tag
 * @author imcoh
 * 
 */
public class WebSinglePost extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... theURL) {
		String url = theURL[0];
		String html = null;
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements ele = doc.select("div.twocolumns");

			html = ele.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return html;
	}

}
