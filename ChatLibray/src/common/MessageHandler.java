package common;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
public class MessageHandler extends JPanel{
	private static final long serialVersionUID = 1L;
	EventListenerList listenerList = new EventListenerList();
	public void AddMessageListener(MessageListener listener) {
		listenerList.add(MessageListener.class, listener);
	}
	public void RemoveMessageListener(MessageListener listener) {
		listenerList.remove(MessageListener.class, listener);
	}
	protected void FireMessage(MessageEvent event){
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == MessageListener.class) {
				((MessageListener)listeners[i + 1]).MessageReceived(event);
			}
		}
	}
}
