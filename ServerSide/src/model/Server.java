package model;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import common.JsonConverter;
import common.Message;
import common.MessageEvent;
import common.MessageListener;
import common.UserInformation;

public class Server extends ServerHandler implements Runnable, MessageListener {
	private ServerSocket serverSocket;
	private Thread serverThread;
	private HashMap<UserInformation, ClientServer> clients;
	
	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			clients = new HashMap<UserInformation, ClientServer>();
			//this.OccurredEvent(new ServerEvent(this, "Servidor creado en el puerto " + port + " correctamente", ServerHelper.DisplayText));
		} catch (Exception e) {
			//this.OccurredEvent(new ServerEvent(this, "No se puede crear el servidor en el puerto " + port + " por que esta siendo utilizado por otro proceso.", ServerHelper.Error));
		}
	}
	public void StartServer() {
		serverThread = new Thread(this);
		serverThread.start();
		this.OccurredEvent(new ServerEvent(this, "Servidor corriendo ...", ServerHelper.DisplayText));
		System.out.println("Server Running ...");
	}
	public void StopServer() {
		serverThread.interrupt();
		this.OccurredEvent(new ServerEvent(this, "Servidor detenido ...", ServerHelper.DisplayText));
		System.out.print("Server Stoped ...");
	}
	@Override
	public synchronized void run() {
		while (!Thread.interrupted()) {
			try {
				Socket clientSocket = serverSocket.accept();
				ClientServer client = new ClientServer(clientSocket);
				if (IsValidUser(client.getUserInformation())) {
					client.AddMessageListener(this);
					client.StartReadMessages();
					clients.put(client.getUserInformation(), client);
					// Actualizar la lista de de usuarios conectados a todos los clientes
					System.out.println(client.getUserInformation().toString());
					UpdateListClients();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void UpdateListClients() {
		ArrayList<UserInformation> users = new ArrayList<UserInformation>(clients.keySet());
		String jsonusers = JsonConverter.ListToJsonString(users);
		for (UserInformation user : clients.keySet()) {
			clients.get(user).WriteOutputMessage(new Message("CLIENT", jsonusers));
		}
	}
	private boolean IsValidUser(UserInformation userinformation) {
		for (UserInformation user : clients.keySet()) {
			if (user.equals(userinformation)) {
				return false;
			}
		}
		return true;
	}
	@Override
	public void MessageReceived(MessageEvent e) {
		if (e.getMessage().getDestinationIP().equals("ALL")) {
			for (UserInformation user : clients.keySet()) {
				ClientServer client = clients.get(user);
				client.WriteOutputMessage(e.getMessage());
			}
		} else if (e.getMessage().getDestinationIP().equals("SERVER")) {
			if (e.getMessage().getText().equals("DISCONNECT")) {
				clients.remove(e.getMessage().getSourceIP());
			} else {
				System.out.println("Username: " + e.getMessage().getText());
			}
		} else {
			ClientServer client = clients.get(e.getMessage().getDestinationIP());
			client.WriteOutputMessage(e.getMessage());
		}
	}
}
