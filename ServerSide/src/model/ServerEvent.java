package model;

import java.util.EventObject;

public class ServerEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private Object param;
	private int type;
	public ServerEvent(Object source, Object param, int type) {
		super(source);
		this.param = param;
		this.type = type;
	}
	public Object getParam() {
		return this.param;
	}
	public int getType() {
		return this.type;
	}
}
