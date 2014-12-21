package ua.nure.doyun.slideassistant.presenter;

import java.util.ArrayList;
import java.util.List;

import ua.nure.doyun.slideassistant.R;
import ua.nure.doyun.slideassistant.model.IUpdateChatListener;
import ua.nure.doyun.slideassistant.model.entities.SlidePackage;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainFragment extends Fragment implements IUpdateChatListener {

	private MainActivity activity;
	private TextView textViewName;
	private TextView textViewSlideNumber;
	private TextView textViewConnectionsNumber;
	private TextView textViewClear;
	private Button buttonClear;

	private List<SlidePackage> packages = new ArrayList<SlidePackage>();
	private List<Boolean> isClear = new ArrayList<Boolean>();
	private int current = 0;

	public MainFragment(MainActivity activity) {
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		textViewName = (TextView) rootView.findViewById(R.id.name);
		textViewSlideNumber = (TextView) rootView
				.findViewById(R.id.slideNumber);
		textViewConnectionsNumber = (TextView) rootView
				.findViewById(R.id.connectionsNumber);
		textViewClear = (TextView) rootView.findViewById(R.id.clear);
		buttonClear = (Button) rootView.findViewById(R.id.clearButton);
		buttonClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				buttonClear.setEnabled(false);
				isClear.set(current, true);
				packages.get(current).setIsClear("1");
				packages.get(current).setWasSetClear("0");
				activity.controller.sendMessage(packages.get(current));
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				packages.get(current).setWasSetClear("1");
			}
		});
		return rootView;
	}

	@Override
	public void onUpdateChat(final SlidePackage pack) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Log.d("MYTAG", pack.toString());
				boolean isInList = false;
				// ñheck if slide exist
				for (int i = 0; i < packages.size(); i++) {
					if (packages.get(i).getSlideNumber()
							.equals(pack.getSlideNumber())) {
						isInList = true;
						current = i;
						break;
					}
				}
				if (!isInList) {
					packages.add(pack);
					isClear.add(false);
					current = packages.size() - 1;
					packages.get(current).setType("android");
				} else {
					packages.get(current).setClear(pack.getClear());
					packages.get(current).setConnectionsNumber(
							pack.getConnectionsNumber());
				}

				buttonClear.setEnabled(!isClear.get(current));

				textViewName.setText(getString(R.string.name)
						+ packages.get(current).getName());
				textViewSlideNumber.setText(getString(R.string.slideNumber)
						+ packages.get(current).getSlideNumber());
				textViewConnectionsNumber
						.setText(getString(R.string.connectionsNumber)
								+ packages.get(current).getConnectionsNumber());
				textViewClear.setText(getString(R.string.clear)
						+ packages.get(current).getClear());

			}
		});
	}

	public void setActivity(MainActivity activity) {
		this.activity = activity;
	}
}
