package com.cogent.BankServlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/read")
public class ReadCustomer extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String URL = "jdbc:mysql://localhost/batch65";
	final String USER = "root";
	final String PASSWORD = "betterme";
	final String DRIVER = "com.mysql.cj.jdbc.Driver";
	Connection conn = null;

	public void init() throws ServletException {

		// Database connection through Driver Manager
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Database connection established successfully.");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			// Set the response content type and
			// get the PrintWriter object.
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			// Set up HTML table formatting for the output
			out.println("<html><body>");
			out.println("<h3>Account Details</h3>");
			out.println("<table border=1><tr>" + "<td><b>ID</b></td>" + "<td><b>Name</b></td>" + "<td><b>Type</b></td>" + "<td><b>Address</b></td>");

			// Create JDBC statement object, construct
			// the SQL query and execute the query.
			Statement stmt = conn.createStatement();
			String sql = "select * from Bank;";
			System.out.println("SQL query: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			// Loop through the result set to
			// retrieve the individual data items.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String type = ((ServletRequest) rs).getParameter("type");
		        String address = ((ServletRequest) rs).getParameter("address");
//				String processor = rs.getString("processor");

				out.println("<tr>" + "<td>" + id + "</td>" + "<td>" + name + "</td>" + type + "</td>" + "<td>" + address + "</td> + </tr>" );

			}
			out.println("</table></body></html>");

			// Close Result set, Statement
			// and PrintWriter objects.
			rs.close();
			stmt.close();
			out.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void destroy() {

		// Close connection object.
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
