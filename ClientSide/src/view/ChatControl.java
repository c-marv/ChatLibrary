package view;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.User;

import common.Message;
import common.MessageEvent;
import common.MessageHandler;

public class ChatControl extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField inputText;
	private JTextArea chathistory;
	private String destinationIP;
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getDestinationIP() {
		return destinationIP;
	}
	public void setDestinationIP(String destinationIP) {
		this.destinationIP = destinationIP;
		
	}
	public ChatControl() {
		setLayout(new BorderLayout(0, 0));
		inputText = new JTextField();
		inputText.setMargin(new Insets(10, 10, 10, 10));
		inputText.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					Message message = new Message(ChatControl.this.user.getUserInformation().getUserIP(), ChatControl.this.destinationIP, ChatControl.this.inputText.getText());
					user.WriteOutputMessage(message);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) { }
			@Override
			public void keyPressed(KeyEvent e) { }
		});
		add(inputText, BorderLayout.SOUTH);
		inputText.setColumns(10);
		
		chathistory = new JTextArea();
		chathistory.setEditable(false);
		chathistory.setMargin(new Insets(10, 10, 10, 10));
		add(chathistory, BorderLayout.CENTER);
		chathistory.setColumns(10);
		
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
	}
	public void AddMessage(String username, String text) {
		chathistory.append(username + " : " + text + "\n");
	}
}
