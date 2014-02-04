package model;
import java.util.Scanner;
import common.Message;
import common.MessageEvent;
import common.MessageListener;

public class Program {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		User user = new User("yerson", "localhost", 8000);
		user.AddMessageListener(new MessageListener() {
			@Override
			public void MessageReceived(MessageEvent e) {
				System.out.println("New Message: " + e.getMessage().getText());
			}
		});
		user.StartReadMessages();
		while (true) {
			System.out.print(">> ");
			String message = input.nextLine();
			user.WriteOutputMessage(new Message("ALL", message));
		}
	}
}
