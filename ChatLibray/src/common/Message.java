package common;

public class Message {
	private String sourceIP, destinationIP, text;
	public Message(String destinationIP, String text) {
		this.destinationIP = destinationIP;
		this.text = text;
	}
	public Message(String sourceIP, String destinationIP, String text) {
		this(destinationIP, text);
		this.sourceIP = sourceIP;
	}
	public String getSourceIP() {
		return sourceIP;
	}
	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}
	public String getDestinationIP() {
		return destinationIP;
	}
	public void setDestinationIP(String destinationIP) {
		this.destinationIP = destinationIP;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "From : " + this.sourceIP + " To : " + this.destinationIP + " Text : " + this.text;
	}
}
