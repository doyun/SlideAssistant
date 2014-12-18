package ua.nure.doyun.slideassistant.presenter;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import ua.nure.doyun.slideassistant.R;
import ua.nure.doyun.slideassistant.model.ConnectionController;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

	ConnectionController controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			
			MainFragment fragment = new MainFragment(this);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, fragment).commit();
			controller = new ConnectionController(this);
			try {
				controller.setUpConnection(fragment);					
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
