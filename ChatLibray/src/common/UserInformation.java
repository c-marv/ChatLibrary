package common;

public class UserInformation {
	private String userIP, username;
	public UserInformation(String userIP, String username) {
		this.userIP = userIP;
		this.username = username;
	}
	public String getUserIP() {
		return userIP;
	}
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		UserInformation userInformation = (UserInformation)obj;
		return this.userIP.equals(userInformation.userIP) & this.username.equals(userInformation.username);
	}
	@Override
	public String toString() {
		return this.userIP + " " + this.username;
	}
}
