package ua.nure.doyun.slideassistant.model;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import ua.nure.doyun.slideassistant.model.entities.SlidePackage;
import android.content.Context;

public class ConnectionController {

	ChatHandler cH;

	public ConnectionController(Context context) {
		cH = new ChatHandler(context);
	}

	public boolean setUpConnection(IUpdateChatListener cL)
			throws UnknownHostException, IOException {

		Thread connectionThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Socket socket = new Socket("10.0.2.2", 50000);
					cH.setSocketConnection(socket);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		connectionThread.start();

		try {
			connectionThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cH.addListener(cL);

		cH.runChatThread();

		return true;
	}

	public void sendMessage(SlidePackage pack) {
		cH.sendMessage(pack);
	}

	public void killConnection() {

	}
}
