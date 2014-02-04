package model;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import common.Client;
import common.Message;
import common.UserInformation;

public class User extends Client{
	private Socket socket;
	public User(String username, String serverip, int port) {
		this.Initialize(username, serverip, port);
	}
	public void Initialize(String username, String server, int port) {
		try {
			this.socket = new Socket(server, port);
			this.input = new Scanner(this.socket.getInputStream());
			this.output = new PrintStream(this.socket.getOutputStream());
			this.userInformation = new UserInformation(InetAddress.getLocalHost().getHostAddress(), username);
			this.WriteOutputMessage(new Message("SERVER", username));
		} catch (Exception e) {
			
		}
	}
	public void CloseSession() {
		Message message = new Message("SERVER", "DISCONNECT");
		this.WriteOutputMessage(message);
	}
}
