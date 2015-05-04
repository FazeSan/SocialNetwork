
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Server.Post;
import Server.User;

import com.google.gson.Gson;
import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class get_post
 */
public class get_post extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public get_post() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
	    
	    PrintWriter out = response.getWriter();
		StringBuilder sb = new StringBuilder();
	    BufferedReader reader = request.getReader();

	    try {
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	sb.append(line).append('\n');
	        }
	        //System.out.println("line : " + line);
	    } finally {
	        reader.close();
	    }

	    user = gson.fromJson(sb.toString(),  User.class);
	    List<Post> listpost = null;
	    try {
	    	  Class.forName("com.mysql.jdbc.Driver");
	    	  conn = DriverManager.getConnection("jdbc:mysql://epitech.cuc1hj6ibtko.eu-west-1.rds.amazonaws.com:3306/socialdb", dbuser, dbpassword);
	    	  //String query = "SELECT * FROM posts WHERE id_user=?";
	    	  String query = "SELECT id_post, id_user,date,content, username FROM posts INNER JOIN user ON user.id = posts.id_user WHERE id_user IN (SELECT id_following FROM following WHERE id_user = ?) ORDER BY date DESC";
	    	  PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
	    	  preparedStmt.setInt(1, user.getId());
	    	  ResultSet rs = preparedStmt.executeQuery();
	    	  listpost = new ArrayList(); 
	    	  while (rs.next()) {
	    		  listpost.add(new Post(rs.getInt("id_user"), rs.getString("content"), rs.getInt("id_post"), rs.getString("username"),rs.getDate("date")));
	    	  }
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
		out.print(gson.toJsonTree(listpost));
		out.flush();
		response.setStatus(HttpServletResponse.SC_OK);

	}

}
