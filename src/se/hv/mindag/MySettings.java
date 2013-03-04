package se.hv.mindag;

/**
 * User: imcoh
 * Date: 2013-03-04
 * Time: 10:48
 */
public class MySettings {
    public String savedUsername = "";
    public String savedPassword = "";
    public String savedTwitter = "";
    public String savedMail = "";

    public MySettings() {
        this.loadSettings();
    }

    public void loadSettings() {
        /*
        SharedPreferences sharedPref = getSharedPreferences("FileName",MODE_PRIVATE);
        sharedPref.g
        SharedPreferences settings = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE);

        this.music = settings.getBoolean("music", this.music);
        this.sfx = settings.getBoolean("sfx", this.sfx);
        this.games_played = settings.getInt("games_played", this.games_played);
        this.high_score = settings.getInt("high_score", this.high_score);
    */
    }

    public void saveSettings() {
     /*
        SharedPreferences settings = R.activity.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean("music", this.music);
        editor.putBoolean("sfx", this.sfx);
        editor.putInt("games_played", this.games_played);
        editor.putInt("high_score", this.high_score);

        editor.commit();
    */
    }
}
