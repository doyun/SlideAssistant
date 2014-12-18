package ua.nure.doyun.slideassistant.chat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;

import ua.nure.doyun.slideassistant.dao.DAManager;
import ua.nure.doyun.slideassistant.entities.SlidePackage;

public class SimpleChatServer {

	static FileWriter fw;

	ArrayList<PrintWriter> clientOutputStreams;

	static boolean stop = false;

	private class ClientHandler implements Runnable {
		private int number;

		Socket socket;

		BufferedReader reader;

		public ClientHandler(Socket clientSocket, int number) {
			this.number = number;
			socket = clientSocket;
			InputStreamReader streamReader;
			try {
				streamReader = new InputStreamReader(socket.getInputStream());
				reader = new BufferedReader(streamReader);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					fw.write("New Message: " + message + System.lineSeparator());
					fw.flush();
					System.out.println(message);
					SlidePackage pack = new SlidePackage(message);
					if (pack.getType().equals("desktop")
							&& pack.getId().equals("")) {
						DAManager.newInstance().createPresentation(pack);
					}
					fw.write("Send: " + pack.toString() + System.lineSeparator());
					fw.flush();
					sendEveryone(pack.toString());
					System.out.println(pack.toString());
				}
				fw.write("Connection " + number + " closed\r\n\r\n");
				fw.flush();
			} catch (SocketException e) {

			} catch (IOException e) {
				e.printStackTrace();
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
			clientOutputStreams = new ArrayList<PrintWriter>();

			try {
				ServerSocket serv = new ServerSocket(Integer.valueOf(port));

				while (!stop) {
					Socket socket = serv.accept();
					PrintWriter writer = new PrintWriter(
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
				e.printStackTrace();
			}

			if (params[0].equals("stop")) {
				fw.write("Server stopped\r\n ~~~~~~~~ \r\n");
				fw.flush();
				fw.close();
				System.exit(0);
			} else if (params[0].equals("start")) {
				Thread t = new Thread(new SimpleChatServer().new ServerHandler(
						params[1]));
				t.start();
			} else {
				System.out.println(s);
			}
		}

	}

	public void sendEveryone(String message) {
		Iterator<PrintWriter> i = clientOutputStreams.iterator();
		while (i.hasNext()) {
			PrintWriter writer = (PrintWriter) i.next();
			writer.println(message);
			writer.flush();
		}

	}

}
