package se.hv.mindag;

import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Makes a connection the the URL of the HV-website and by using JSOUP we can
 * point out wich DIV-tag we want to retrive. The result is stored in a String,
 * wich is returned as a result.
 *
 * @param url of a certain website
 * @author imcoh
 * @return The String of text contained in a specific DIV-tag
 */
public class WebSinglePost extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... theURL) {
        String url = theURL[0];
        String html = null;
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            if (url.contains("http://www.hv.se")) {
                Elements ele;
                ele = doc.select("div.twocolumns");
                html = ele.toString();
            }
            else if (url.contains("http://m.ttela.se")) {
                Elements ele;
                ele = doc.select("div#avdelning");
                html = ele.toString();
            }
            else
                html = doc.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return html;
    }

}
