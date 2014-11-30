package com.chat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import ua.nure.jfdi.conferenceapp.entities.Message;

public class SimpleObjectChatServer {

	static FileWriter fw;

	ArrayList<ObjectOutputStream> clientOutputStreams;

	static boolean stop = false;

	private class ClientHandler implements Runnable {
		private int number;

		Socket socket;

		public ClientHandler(Socket clientSocket, int number) {
			this.number = number;
			socket = clientSocket;
		}

		@Override
		public void run() {
			Message message;
			try {
				ObjectInputStream reader = new ObjectInputStream(
						socket.getInputStream());
				while ((message = ((Message) reader.readObject())) != null) {
					fw.write("New Message: " + message.getText() + "\r\n");
					fw.flush();
					sendEveryone(message);
				}
				fw.write("Connection " + number + " closed\r\n\r\n");
				fw.flush();
			} catch (Exception e) {

			}

		}

	}

	private class ServerHandler implements Runnable {
		private int count;
		String port;

		public ServerHandler(String port) {
			this.port = port;
		}

		@Override
		public void run() {
			clientOutputStreams = new ArrayList<ObjectOutputStream>();

			try {
				ServerSocket serv = new ServerSocket(Integer.valueOf(port));

				while (!stop) {
					Socket socket = serv.accept();
					ObjectOutputStream writer = new ObjectOutputStream(
							socket.getOutputStream());
					clientOutputStreams.add(writer);

					count++;

					Thread t = new Thread(new ClientHandler(socket, count));
					t.start();

					fw.write("Connection " + count + " opened\r\n");
					fw.flush();
				}
				
				serv.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) throws IOException {
		fw = new FileWriter(new File("Log.txt"));
		fw.write("Server started\r\n\r\n");
		fw.flush();

		String s = null;
		String[] params = new String[10];

		while (true) {
			System.out.print("Enter command to execute: ");

			try {
				BufferedReader bufferRead = new BufferedReader(
						new InputStreamReader(System.in));
				s = bufferRead.readLine();
				params = s.split(" ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (params[0].equals("stop")) {
				fw.write("Server stopped\r\n ~~~~~~~~ \r\n");
				fw.flush();
				fw.close();
				System.exit(0);
			} else if (params[0].equals("start")) {
				Thread t = new Thread(
						new SimpleObjectChatServer().new ServerHandler(
								params[1]));
				t.start();
			} else
				System.out.println(s);
		}

	}

	public void sendEveryone(Message message) {
		Iterator<ObjectOutputStream> i = clientOutputStreams.iterator();
		while (i.hasNext()) {
			ObjectOutputStream writer = (ObjectOutputStream) i.next();
			try {
				writer.writeObject(message);
				writer.flush();
				fw.write("Message: " + message.getText() + "send\r\n");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
