package com.chat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import ua.nure.jfdi.conferenceapp.entities.Message;

public class SimpleObjectChatClient {

	JTextArea incoming;
	JTextField outgoing;
	ObjectOutputStream writer;
	ObjectInputStream reader;
	Socket socket;

	private String name = "";

	public static void main(String[] args) {
		SimpleObjectChatClient client1 = new SimpleObjectChatClient();
		if (client1.name == null)
			return;
		// SimpleChatClient client2 = new SimpleChatClient();
		client1.go();
		// client2.go();
	}

	public SimpleObjectChatClient() {
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

			reader = new ObjectInputStream(socket.getInputStream());

			writer = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("Соединение установлено");

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class SendListener implements ActionListener, KeyListener {
		public void Send() throws IOException {
			Message msg = new Message(name, outgoing.getText(),
					System.currentTimeMillis());
			writer.writeObject(msg);
			writer.flush();
			outgoing.setText("");
			outgoing.requestFocus();
			System.out.println(outgoing.getText());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Send();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
				try {
					Send();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private class IncomingReader implements Runnable {

		@Override
		public void run() {
			Message message;
			try {

				while ((message = ((Message) reader.readObject())) != null) {
					System.out.println("Прочитано: " + message);
					if (message.getAuthor().contains(name)) {

						incoming.append("Me: " + message.getText() + "\n");
					} else {
						incoming.append(message.getAuthor() + ": " + message.getText() + "\n");
					}
				}
			} catch (Exception e) {

			}

		}
	}
}
