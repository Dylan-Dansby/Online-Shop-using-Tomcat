import java.io.*;
import java.sql.*;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;

public class AuthServlet extends HttpServlet{
	Properties prop;
	
	public void init() {
	    prop = new Properties();
	    String relativePath = "/WEB-INF/conf/systemapp.properties";
	    String absolutePath = getServletContext().getRealPath(relativePath);
	    
	    try (FileInputStream fis = new FileInputStream(absolutePath)) { 
	        prop.load(fis);
	        System.out.println("Successfully loaded DB URL: " + prop.getProperty("db.url"));
	    } catch (IOException ex) {
	        System.out.println("Could not find file at: " + absolutePath);
	        ex.printStackTrace();
	    }
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String url = prop.getProperty("db.url");
		String user = prop.getProperty("db.username");
		String pass = prop.getProperty("db.password");
		String sql = "SELECT * FROM usercredentials WHERE login_username = ? AND login_password = ?";
		
		try {
		    // 1. Explicitly load the MySQL driver
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    
		    // 2. Connect and execute
		    try (Connection conn = DriverManager.getConnection(url, user, pass);
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {    
		    	
		    	pstmt.setString(1, username);
		    	pstmt.setString(2, password);
		    	
		    	try (ResultSet RS = pstmt.executeQuery()){
		    		if(RS.next()) {
		    			if(username.equals("root")){
		    				response.sendRedirect("rootHome.jsp");
		    			} else if (username.equals("client")) {
		    				response.sendRedirect("clientHome.jsp");
		    			} else if (username.equals("theaccountant")) {
		    				response.sendRedirect("accountantHome.jsp");
		    			} else if (username.equals("dataentry")) {
		    				response.sendRedirect("dataEntryHome.jsp");
		    			} else {
		    				//credentials don`t match roles
		    				response.sendRedirect("Authenticate.html");
		    			}
		    		} else {
		    			response.sendRedirect("Errorpage.jsp");		    		
		    		}
		    	} 
		    }
		} catch (Exception e) {
            e.printStackTrace();
            try {
            	request.setAttribute("errorMessage", e.getMessage());
            	request.getRequestDispatcher("Errorpage.jsp").forward(request, response);

            } catch (Exception e2) {
            	e.printStackTrace();
            }
        } 
    }
}