
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Server.User;

import com.google.gson.Gson;
import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		User user = new User();
	    Connection conn = null;
	    String dbuser = "epitech";
	    String dbpassword = "epitechroot";
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
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

	    user = gson.fromJson(sb.toString(),  User.class);
	    System.out.println("Username = " + user.getUsername());
	    System.out.println("Password = " + user.getPassword());
	    System.out.println(sb.toString());
	    try {
	    	  Class.forName("com.mysql.jdbc.Driver");
	    	  conn = DriverManager.getConnection("jdbc:mysql://epitech.cuc1hj6ibtko.eu-west-1.rds.amazonaws.com:3306/socialdb", dbuser, dbpassword);
	    	  String query = "SELECT * FROM user WHERE mail=? AND password=md5(?)";
		   	  PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
		   	  preparedStmt.setString(1, user.getMail());
			  preparedStmt.setString(2, user.getPassword());
			  ResultSet rs = preparedStmt.executeQuery();
			  User rUser = new User();
			  Gson rGson = new Gson();
			  while (rs.next() ) {
				  System.out.println(rs.getInt("id"));
				  rUser.setId(rs.getInt("id"));
				  rUser.setUsername(rs.getString("username"));
				}
		   	  rs.close();
			  String query2 = "SELECT id_following FROM following WHERE id_user = ?";
			  PreparedStatement preparedStmt2 = (PreparedStatement) conn.prepareStatement(query2);
		   	  preparedStmt2.setInt(1, rUser.getId());
		   	  ResultSet rs2 = preparedStmt2.executeQuery();
		   	  List<Integer> following = new ArrayList();
		   	  while (rs2.next()) {
		   		  following.add(rs2.getInt("id_following"));
		   	  }
		   	  rs2.close();
		   	  conn.close();
		   	  rUser.setFollowings(following);
		   	  response.setContentType("application/json");      
		   	  out.print(rGson.toJsonTree(rUser));
		   	  out.flush();
		   	  response.setStatus(response.SC_OK);
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
