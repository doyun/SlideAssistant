package ua.nure.doyun.slideassistant.chat;

import java.io.*;
import java.net.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class SimpleChatClient {

	JTextArea incoming;
	JTextField outgoing;
	PrintWriter writer;
	BufferedReader reader;
	Socket socket;

	private String name = "";

	public static void main(String[] args) {
		SimpleChatClient client1 = new SimpleChatClient();
		if (client1.name == null)
			return;
		// SimpleChatClient client2 = new SimpleChatClient();
		client1.go();
		// client2.go();
	}

	public SimpleChatClient() {
		while (name.equals("")) {
			String temp = JOptionPane.showInputDialog("",
					"Enter your ChatName:");
			if (temp == "")
				name = "";
			else if (temp != "")
				name = temp.replace("Enter your ChatName:", "");
			else
				name = "Undefined User";
		}
	}

	private void go() {
		JFrame frame = new JFrame("Simple Chat Program For " + name);
		JPanel mainPanel = new JPanel();
		incoming = new JTextArea(15, 35);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane qScroller = new JScrollPane(incoming);
		qScroller
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outgoing = new JTextField(20);
		outgoing.addKeyListener(new SendListener());
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendListener());
		mainPanel.add(qScroller);
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		setUpNetworking();

		Thread reading = new Thread(new IncomingReader());
		reading.start();

		frame.setSize(420, 380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private void setUpNetworking() {
		try {
			socket = new Socket("127.0.0.1", 50000); /* "159.253.134.253" */

			InputStreamReader streamReader = new InputStreamReader(
					socket.getInputStream());
			reader = new BufferedReader(streamReader);

			writer = new PrintWriter(socket.getOutputStream());
			System.out.println("Соединение установлено");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private class SendListener implements ActionListener, KeyListener {
		public void Send() {
			writer.println(name + ": " + outgoing.getText());
			writer.flush();
			outgoing.setText("");
			outgoing.requestFocus();
			System.out.println(outgoing.getText());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Send();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
				Send();
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

	}

	private class IncomingReader implements Runnable {

		@Override
		public void run() {
			String message;

			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("Прочитано: " + message);
					if (message.contains(name + ":")) {
						System.out.println("replace");
						message = message.replace(name, "Me");
					}
					incoming.append(message + "\n");
				}

			} catch (NullPointerException e) {

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
