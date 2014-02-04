package view;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import common.Message;

public class ChatControl extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField inputText;
	private JTextArea chathistory;
	public ChatControl() {
		setLayout(new BorderLayout(0, 0));
		inputText = new JTextField();
		inputText.setMargin(new Insets(10, 10, 10, 10));
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
