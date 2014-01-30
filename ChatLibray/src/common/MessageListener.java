package common;
import java.util.EventListener;
public interface MessageListener extends EventListener {
	public void MessageReceived(MessageEvent e);
}
