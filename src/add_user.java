import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Server.User;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.amazonaws.util.json.JSONTokener;
import com.google.gson.Gson;
import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Servlet implementation class add_user
 */
public class add_user extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public add_user() {
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
		PrintWriter out = response.getWriter();
		boolean user_exist = true;

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
		System.out.println(sb.toString());
		if (user_exist) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://epitech.cuc1hj6ibtko.eu-west-1.rds.amazonaws.com:3306/socialdb", dbuser, dbpassword);
				String query = "SELECT mail FROM user WHERE mail = ?";
				PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
				preparedStmt.setString(1, user.getMail());
				preparedStmt.execute();
				ResultSet rs = preparedStmt.executeQuery();
				if (rs.next()) { response.setStatus(HttpServletResponse.SC_CONFLICT);}
				else {
					try {
						Class.forName("com.mysql.jdbc.Driver");
						conn = DriverManager.getConnection("jdbc:mysql://epitech.cuc1hj6ibtko.eu-west-1.rds.amazonaws.com:3306/socialdb", dbuser, dbpassword);
						String query2 = "INSERT INTO user (username, password, mail)" + " values (?, md5(?), ?)";
						PreparedStatement preparedStmt2 = (PreparedStatement) conn.prepareStatement(query2);
						preparedStmt2.setString(1, user.getUsername());
						preparedStmt2.setString(2, user.getPassword());
						preparedStmt2.setString(3, user.getMail());
						preparedStmt2.execute();
						User nUser = new User();
						Gson rGson = new Gson();
						response.setContentType("application/json");      
						out.print(rGson.toJsonTree(nUser));
						out.flush();
						response.setStatus(HttpServletResponse.SC_OK);
						String query3 = "SELECT id from user WHERE mail = ?";
						PreparedStatement preparedStmt3 = (PreparedStatement) conn.prepareStatement(query3);
						preparedStmt3.setString(1, user.getMail());
						preparedStmt3.execute();
						ResultSet rs3 = preparedStmt.executeQuery();
						if (rs3.next()) {
							int id = rs3.getInt("id");
							String query4 = "INSERT INTO following (id_user, id_following) VALUES (?, ?)";
							PreparedStatement preparedStmt4 = (PreparedStatement) conn.prepareStatement(query4);
							preparedStmt4.setInt(1, id);
							preparedStmt4.setInt(2, id);
							preparedStmt4.execute();
							preparedStmt.close();
							preparedStmt2.close();
							preparedStmt3.close();
							rs.close();
							rs3.close();
							conn.close();
						}
					}
					catch (ClassNotFoundException e)
					{
						System.out.println("MySQL Driver NOT Found");
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						e.printStackTrace();
					}
					catch (SQLException e) { System.out.println("fail");
					// TODO Auto-generated catch block
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					e.printStackTrace();
					}
				}
			}
			catch (SQLException e) { out.println("SQL ERROR"); }
			catch (ClassNotFoundException e) { out.println("Driver not found");}
		}

		/*System.out.println(gson.fromJson(sb.toString(),  User.class));*/


	}
}