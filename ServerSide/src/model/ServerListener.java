package model;

import java.util.EventListener;

public interface ServerListener extends EventListener{
	public void OccurredEvent(ServerEvent event); 
}
