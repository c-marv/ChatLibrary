package model;

import javax.swing.event.EventListenerList;
public class ServerHandler {
	EventListenerList listenerlist = new EventListenerList();
	public void AddServerListener(ServerListener listener) {
		listenerlist.add(ServerListener.class, listener);
	}
	public void RemoveServerListener(ServerListener listener) {
		listenerlist.remove(ServerListener.class, listener);
	}
	void OccurredEvent(ServerEvent event){
		Object[] listeners = listenerlist.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == ServerListener.class) {
				((ServerListener)listeners[i + 1]).OccurredEvent(event);
			}
		}
	}
}
