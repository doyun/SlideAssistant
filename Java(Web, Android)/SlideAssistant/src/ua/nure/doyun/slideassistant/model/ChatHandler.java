package ua.nure.doyun.slideassistant.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import ua.nure.doyun.slideassistant.model.entities.SlidePackage;
import android.content.Context;
import android.util.Log;

public class ChatHandler {

	Socket socket;

	PrintWriter writer;
	BufferedReader reader;

	List<IUpdateChatListener> chatListeners;
	SlidePackage pack;


	public ChatHandler(Context context) {
		chatListeners = new ArrayList<IUpdateChatListener>();
	}

	public void setSocketConnection(Socket givenSocket) {
		socket = givenSocket;
		try {
			InputStreamReader streamReader = new InputStreamReader(
					socket.getInputStream());
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(socket.getOutputStream());
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void addListener(IUpdateChatListener cL) {
		chatListeners.add(cL);

	}

	public void runChatThread() {
		Log.d("MYTAG", "#runChatThread");
		Thread chatThread = new Thread(new Runnable() {
			@Override
			public void run() {
				String income;
				try {
					while ((income = reader.readLine()) != null) {
						pack = new SlidePackage(income);
						
						for (IUpdateChatListener l : chatListeners) {
							l.onUpdateChat(pack);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		chatThread.start();

	}

	public void sendMessage(SlidePackage slidePackage) {		
		Log.d("MYTAG", "#sendMessage" + slidePackage.toString());
		writer.println(slidePackage.toString());
		writer.flush();
	}

}
