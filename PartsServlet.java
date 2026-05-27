import java.io.*;
import java.sql.*;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;
public class PartsServlet extends HttpServlet{
	Properties prop;
	public void init() {
	    prop = new Properties();
	    
	    // 1. Define the relative path starting from the root of your webapp
	    // Note: It MUST start with a forward slash "/"
	    String relativePath = "/WEB-INF/conf/dataentry.properties";
	    
	    // 2. Ask the Servlet Context to translate that into an absolute hard-drive path
	    String absolutePath = getServletContext().getRealPath(relativePath);
	    String url = prop.getProperty("db.url");
		String user = prop.getProperty("db.username");
		String pass = prop.getProperty("db.password");
	    try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    Connection con = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    
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
		
		// 1. Extract the parameters directly from the dataentry HTML form
        String pnum = request.getParameter("pnum");
        String pname = request.getParameter("pname");
        String color = request.getParameter("color");
        String weight = request.getParameter("weight");
        String city = request.getParameter("city");
        
        StringBuilder html = new StringBuilder();
		
		try(Connection conn = DriverManager.getConnection(url,username,password)){
			Statement stmt = conn.createStatement();
			
			String sql = "INSERT INTO parts (pnum, pname, color, weight, city) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			// 3. Bind the extracted parameters to the statement
            pstmt.setString(1, pnum);
            pstmt.setString(2, pname);
            pstmt.setInt(3, Integer.parseInt(color)); // Color must be cast to an integer
            pstmt.setString(4, weight);
            pstmt.setString(5, city);
            
			int rows = pstmt.executeUpdate();
			if (rows > 0) {
                html.append("<div style='background-color: blue; color: white; border: 2px solid yellow; padding: 5px;'>");
                html.append("New parts record: (").append(pnum).append(", ")
                    .append(pname).append(", ").append(color).append(", ")
                    .append(city).append(") - successfully entered into database.");
                html.append("</div>");
            }
			
			
		} catch(SQLException e) {
			html.append("<div style='background-color: red; color: white; padding: 5px;'>");
            html.append("Error executing the SQL statement:<br>");
            html.append(e.getMessage());
            html.append("</div>");
			
		} catch (NumberFormatException e3) {
			html.append("<div style='background-color: red; color: white; padding: 5px;'>");
            html.append("Error: Color must be a valid integer.");
            html.append("</div>");
		}
		
		// 7. Send the results back to the unified JSP page [cite: 225]
        request.setAttribute("results", html.toString());
        try {
        	request.getRequestDispatcher("dataEntryHome.jsp").forward(request, response);
        } catch (Exception e4) {
        	e4.printStackTrace();
        }
        
	}
	
}
