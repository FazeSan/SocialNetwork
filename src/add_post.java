
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Server.Post;
import Server.User;

import com.google.gson.Gson;
import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class add_post
 */
public class add_post extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public add_post() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
	    response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
	    response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
	    response.addHeader("Access-Control-Max-Age", "1728000");

		Gson gson = new Gson();
		Post post = new Post();
	    Connection conn = null;
	    java.util.Date dt = new java.util.Date();

	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String currentTime = sdf.format(dt);
	    String dbuser = "epitech";
	    String dbpassword = "epitechroot";
		
		StringBuilder sb = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    try {
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	sb.append(line).append('\n');
	        }
	    } finally {
	        reader.close();
	    }

	    post = gson.fromJson(sb.toString(),  Post.class);
	    System.out.println("id_user = " + post.getIdUser());
	    System.out.println("Content = " + post.getContent());
	    System.out.println(sb.toString());
	    try {
	    	  Class.forName("com.mysql.jdbc.Driver");
	    	  conn = DriverManager.getConnection("jdbc:mysql://epitech.cuc1hj6ibtko.eu-west-1.rds.amazonaws.com:3306/socialdb", dbuser, dbpassword);
		   	  String query = "INSERT INTO posts (id_user, date, content)" + " values (?, ?, ?)";
		   	  PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
		   	  preparedStmt.setInt(1, post.getIdUser());
			  preparedStmt.setString(2, currentTime);
			  preparedStmt.setString(3, post.getContent());
			  
			  preparedStmt.execute();
			  response.setStatus(response.SC_OK);
			  PrintWriter out = response.getWriter();
			  User rUser = new User();
			  rUser.setId(post.getIdUser());
			  Gson rGson = new Gson();
			  response.setContentType("application/json");      
			  out.print(rGson.toJsonTree(rUser));
			  out.flush();
			  response.setStatus(response.SC_OK);
			  
			  preparedStmt.close();
			  conn.close();
	    }
	    catch (ClassNotFoundException e)
	  	  {
		    	  System.out.println("MySQL Driver NOT Found");
		    	  e.printStackTrace();
	  	  }
	    catch (SQLException e) { System.out.println("fail");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			/*System.out.println(gson.fromJson(sb.toString(),  User.class));*/
			
		
	}

}
