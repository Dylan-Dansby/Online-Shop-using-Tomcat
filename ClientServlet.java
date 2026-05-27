import java.io.*;
import java.sql.*;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;

public class ClientServlet extends HttpServlet{

	Properties prop;
	public void init() {
	    prop = new Properties();
	    
	    // 1. Define the relative path starting from the root of your webapp
	    // Note: It MUST start with a forward slash "/"
	    String relativePath = "/WEB-INF/conf/client.properties";
	    
	    // 2. Ask the Servlet Context to translate that into an absolute hard-drive path
	    String absolutePath = getServletContext().getRealPath(relativePath);
	    String url = prop.getProperty("db.url");
		String user = prop.getProperty("db.username");
		String pass = prop.getProperty("db.password");
	    
	    
	    // 3. Use the dynamic absolute path in your FileInputStream
	    try (FileInputStream fis = new FileInputStream(absolutePath)) { 
	        prop.load(fis);
	        System.out.println("Successfully loaded DB URL: " + prop.getProperty("db.url"));
	    } catch (IOException ex) {
	        System.out.println("Could not find file at: " + absolutePath);
	        ex.printStackTrace();
	    }
	}//EO INIT
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String url = prop.getProperty("db.url");
		String username = prop.getProperty("db.username");
		String password = prop.getProperty("db.password");
		String sql = request.getParameter("sqlCommand");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try(Connection conn = DriverManager.getConnection(url,username,password)){
				Statement stmt = conn.createStatement();
				StringBuilder html = new StringBuilder();
				
				if(sql.toLowerCase().startsWith("select")) {
					ResultSet RS = stmt.executeQuery(sql);
					//Build HTML table from RS
					ResultSetMetaData rsmd = RS.getMetaData(); //Meta Data for result set
					int colCount = rsmd.getColumnCount();
					
					html.append("<table border='1'>");
				    
				    // Header row from column names
				    html.append("<tr>");
				    for (int i = 1; i <= colCount; i++) {
				        html.append("<th>").append(rsmd.getColumnName(i)).append("</th>");
				    }
				    html.append("</tr>");
				    
				    // Data rows
				    while (RS.next()) {
				        html.append("<tr>");
				        for (int i = 1; i <= colCount; i++) {
				            html.append("<td>").append(RS.getString(i)).append("</td>");
				        }
				        html.append("</tr>");
				    }
				    
				    html.append("</table>");

					
				} else {
					int rows = stmt.executeUpdate(sql);
					System.out.println("UPDATE: "+rows+" rows affected");
					
					
				}
				
				request.setAttribute("results", html.toString());
				request.getRequestDispatcher("clientHome.jsp").forward(request, response);
			} catch (Exception e) {
				try {
					request.setAttribute("errorMessage", e.getMessage());
	            	request.getRequestDispatcher("Errorpage.jsp").forward(request, response);
	            	e.printStackTrace();
				} catch (Exception e5) {
					e5.printStackTrace();
				}
				
			}
		} catch (NumberFormatException e2) {
			try {
				request.setAttribute("errorMessage", e2.getMessage());
            	request.getRequestDispatcher("Errorpage.jsp").forward(request, response);
            	e2.printStackTrace();
			} catch (Exception e3) {
				e3.printStackTrace();
			}
			
		} catch (ClassNotFoundException e4) {
			try {
				request.setAttribute("errorMessage", e4.getMessage());
            	request.getRequestDispatcher("Errorpage.jsp").forward(request, response);
            	e4.printStackTrace();
			} catch (Exception e3) {
				try {
					request.setAttribute("errorMessage", e3.getMessage());
	            	request.getRequestDispatcher("Errorpage.jsp").forward(request, response);
	            	e3.printStackTrace();
				} catch (Exception e5) {
					e4.printStackTrace();
				}
				
			}
		}
		
		
		
}
}

		
