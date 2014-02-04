package model;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import common.Client;
import common.JsonConverter;
import common.Message;
import common.UserInformation;

public class ClientServer extends Client {
	public ClientServer(Socket socket) {
		super();
		Initialize(socket);
	}
	private void Initialize(Socket socket) {
		try {
			this.input = new Scanner(socket.getInputStream());
			this.output = new PrintStream(socket.getOutputStream());
			Message message = JsonConverter.JsonStringToMessage(this.input.nextLine());
			this.userInformation = new UserInformation(socket.getLocalAddress().getHostAddress(), message.getText());
		} catch (Exception e) {
			System.out.println("Error to initialize client");
		}
	}
	public UserInformation getUserInformation() {
		return this.userInformation;
	}
}
