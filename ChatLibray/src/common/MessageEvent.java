package common;
import java.util.EventObject;
public class MessageEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private Message message;
	public MessageEvent(Object source, Message message) {
		super(source);
		this.message = message;
	}
	public Message getMessage() {
		return message;
	}
}
