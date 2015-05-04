package Server;
import java.util.Date;


public class Post {
		private Integer id_user;
		private Integer id_post;
		private String username;
		private String content;
		private Date date;
		
		public Post() {}
		
		public Post(Integer id_user, String content) {
			this.id_user = id_user;
			this.content = content;
		}
		public Post(Integer id_user, String content, Integer id_post) {
			this.id_user = id_user;
			this.id_post = id_post;
			this.content = content;
		}
		public Post(Integer id_user, String content, Integer id_post, String username, Date date) {
			this.id_user = id_user;
			this.id_post = id_post;
			this.content = content;
			this.date = date;
			this.username = username;
		}
		
		public Integer getIdUser() {
			return id_user;
		}
		
		public void setIdUser(Integer id_user) {
			this.id_user = id_user;
		}
		public Integer getIdPost() {
			return id_post;
		}
		
		public void setIdPost(Integer id_post) {
			this.id_post = id_post;
		}
		public String getUsernamePost() {
			return username;
		}
		
		public void setUsernamePost(String username) {
			this.username = username;
		}
		public String getContent() {
			return content;
		}
		
		public void setContent(String content) {
			this.content = content;
		}
		public Date getDate() {
			return date;
		}
		
		public void setDate(Date date) {
			this.date = date;
		}
		@Override
		public String toString() {
			return "Content [username= " + id_user + ", " + "content=" + content + "]";
		}
	}