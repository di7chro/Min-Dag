package se.hv.mindag;

import android.app.Activity;
import android.os.Bundle;

/**
 * Fires up the DinDagMenu
 * 
 * @author imcoh
 * 
 */
public class DinDagMenu extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.menu.menu_start);
	}
}