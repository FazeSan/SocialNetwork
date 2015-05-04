package Server;

import java.util.List;

public class User {
	private Integer id;
	private String username;
	private String password;
	private String mail;
	private String follow;
	private List<Integer> friends;
	
	public List<Integer> getFollowings() {
		return friends;
	}

	public void setFollowings(List<Integer> friends) {
		this.friends = friends;
	}

	public User() {}
	
	public User(Integer id, String username, String mail) {
		this.id = id;
		this.username = username;
		this.mail = mail;
	}
	
	public User(Integer id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public User(Integer id, String username, List<Integer> friends) {
		this.id = id;
		this.username = username;
		this.friends = friends;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFollow() {
		return follow;
	}
	
	public void setFollow(String follow) {
		this.follow = follow;
	}
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	@Override
	public String toString() {
		return "User [username= " + username + ", " + "password=" + password + ", " + "mail= " + mail + ", " + "follow=" + follow +"]";
	}
}
