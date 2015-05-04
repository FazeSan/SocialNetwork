package Server;

public class Follower {
	private Integer id_user;
	private Integer id_follower;
	
	public Follower() {}
	
	public Follower(Integer id_user, Integer id_follower) {
		this.id_user= id_user;
		this.id_follower = id_follower;
	}
	
	public Integer getIdUser() {
		return id_user;
	}
	
	public void setIdUser(Integer id_user) {
		this.id_user = id_user;
	}
	public Integer getIdFollower() {
		return id_follower;
	}
	
	public void setIdFollower(Integer id_follower) {
		this.id_follower = id_follower;
	}	
}
