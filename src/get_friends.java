

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class get_friends
 */
public class get_friends extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public get_friends() {
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

	    Connection conn = null;
	    String dbuser = "epitech";
	    String dbpassword = "epitechroot";
	    
	    User user = new User();
	    Gson gson = new Gson();
	    
	    List<User> listuser = new  ArrayList<User>();
	    
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
	    try {
	    	  Class.forName("com.mysql.jdbc.Driver");
	    	  conn = DriverManager.getConnection("jdbc:mysql://epitech.cuc1hj6ibtko.eu-west-1.rds.amazonaws.com:3306/socialdb", dbuser, dbpassword);
	    	  String query = "SELECT * FROM user WHERE id != ?";
	    	  PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
	    	  preparedStmt.setInt(1, user.getId());
	    	  ResultSet rs = preparedStmt.executeQuery();
	    	  while (rs.next()) {
	    		  int id = rs.getInt("id");
	    		  String username = rs.getString("username");
	    		  user = new User(id, username);
	    		  listuser.add(user);
	    	  }
	    	  rs.close();
	    	  preparedStmt.close();
	    	  conn.close();
	    } catch (ClassNotFoundException e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    } catch (SQLException e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
		} finally {
	        reader.close();
	    }
		response.setContentType("application/json");      
		out.print(gson.toJsonTree(listuser));
		out.flush();
		response.setStatus(HttpServletResponse.SC_OK);
	    


	}

}
