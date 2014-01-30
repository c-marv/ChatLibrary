package model;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import common.MessageEvent;
import common.MessageListener;

public class Server implements Runnable, MessageListener {
	private ServerSocket serverSocket;
	private Thread serverThread;
	private HashMap<String, ClientServer> clients;
	
	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			clients = new HashMap<String, ClientServer>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void StartServer() {
		serverThread = new Thread(this);
		serverThread.start();
		System.out.println("Server Running ...");
	}
	public void StopServer() {
		serverThread.interrupt();
		System.out.print("Server Stoped ...");
	}
	@Override
	public synchronized void run() {
		while (!Thread.interrupted()) {
			try {
				Socket clientSocket = serverSocket.accept();
				System.out.println(clientSocket.getLocalAddress().getHostAddress());
				ClientServer client = new ClientServer(clientSocket);
				client.AddMessageListener(this);
				client.StartReadMessages();
				clients.put(client.getIP(), client);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void MessageReceived(MessageEvent e) {
		if (e.getMessage().getDestinationIP().equals("ALL")) {
			for (String IP : clients.keySet()) {
				ClientServer client = clients.get(IP);
				client.WriteOutputMessage(e.getMessage());
			}
		}else {
			ClientServer client = clients.get(e.getMessage().getDestinationIP());
			client.WriteOutputMessage(e.getMessage());
		}
	}
}
