package common;
import java.io.PrintStream;
import java.util.Scanner;
public class Client extends MessageHandler implements Runnable {
	protected Scanner input;
	protected PrintStream output;
	protected Thread clientThread;
	
	public Client() {
		
	}
	public void WriteOutputMessage(Message message) {
		String jsonmessage = JsonConverter.MessageToJsonString(message);
		this.output.println(jsonmessage);
	}
	public void StartReadMessages() {
		clientThread = new Thread(this);
		clientThread.start();
	}
	public void StopReadMessages() {
		clientThread.interrupt();
	}
	@Override
	public synchronized void run() {
		while (!Thread.interrupted()) {
			try {
				if (input.hasNextLine()) {
					Message message = JsonConverter.JsonStringToMessage(this.input.nextLine());
					MessageEvent event = new MessageEvent(this, message);
					this.FireMessage(event);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
