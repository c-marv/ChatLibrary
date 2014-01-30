package common;
import javax.swing.event.EventListenerList;
public class MessageHandler {
	EventListenerList listenerList = new EventListenerList();
	public void AddMessageListener(MessageListener listener) {
		listenerList.add(MessageListener.class, listener);
	}
	public void RemoveMessageListener(MessageListener listener) {
		listenerList.remove(MessageListener.class, listener);
	}
	void FireMessage(MessageEvent event){
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == MessageListener.class) {
				((MessageListener)listeners[i + 1]).MessageReceived(event);
			}
		}
	}
}
