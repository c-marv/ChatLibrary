package view;
import model.Server;
public class Main {
	public static void main(String[] args) {
		Server server = new Server(8000);
		server.StartServer();
	}
}
