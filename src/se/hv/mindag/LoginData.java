package se.hv.mindag;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Sets up an ASynkTask to the credentials-server asking if the
 * Username/password combination was correct.
 * 
 * @author immp
 * 
 */

public class LoginData extends AsyncTask<String, Void, ArrayList<String>> {

	@Override
	protected ArrayList<String> doInBackground(String... urlHash) {
		String answer = null;
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(urlHash[0]);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			answer = EntityUtils.toString(httpEntity);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<String> loginList = new ArrayList<String>();
		loginList.add(answer);
		return loginList;
	}

	// Dummy method
	protected void onPostExecute(Integer result) {
		Log.d("TestP", "AsyncTask done and returned: " + result);
	}
}
