

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Server.Follower;
import Server.User;

import com.google.gson.Gson;
import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class add_follow
 */
public class add_follow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public add_follow() {
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
		Follower follower = new Follower();
		Connection conn = null;
		String dbuser = "epitech";
		String dbpassword = "epitechroot";
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

		follower = gson.fromJson(sb.toString(),  Follower.class);
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://epitech.cuc1hj6ibtko.eu-west-1.rds.amazonaws.com:3306/socialdb", dbuser, dbpassword);
				String query = "INSERT INTO following (id_user, id_following) VALUES (?,?)";
				PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
				preparedStmt.setInt(1, follower.getIdUser());
				preparedStmt.setInt(2, follower.getIdFollower());
				preparedStmt.execute();
				Gson rGson = new Gson();
				response.setContentType("application/json");      
				out.print(rGson.toJsonTree(follower));
				out.flush();
				response.setStatus(HttpServletResponse.SC_OK);
				preparedStmt.close();
				conn.close();
			}
			catch (SQLException e) { System.out.println("fail");
			// TODO Auto-generated catch block
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			e.printStackTrace();
			}
			catch (ClassNotFoundException e)
			{
				System.out.println("MySQL Driver NOT Found");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}
	}

}
