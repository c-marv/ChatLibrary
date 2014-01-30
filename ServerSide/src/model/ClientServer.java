package model;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import common.Client;

public class ClientServer extends Client {
	private String clientIP;
	public ClientServer(Socket socket) {
		super();
		Initialize(socket);
	}
	private void Initialize(Socket socket) {
		try {
			this.input = new Scanner(socket.getInputStream());
			this.output = new PrintStream(socket.getOutputStream());
			this.clientIP = socket.getLocalAddress().getHostAddress();
		} catch (Exception e) {
			System.out.println("Error to initialize client");
		}
	}
	public String getIP() {
		return clientIP;
	}
}
