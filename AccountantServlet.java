import java.io.*;
import java.sql.*;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;

public class AccountantServlet extends HttpServlet{
	Properties prop;
	public void init() {
	    prop = new Properties();
	    
	    // 1. Define the relative path starting from the root of your webapp
	    // Note: It MUST start with a forward slash "/"
	    String relativePath = "/WEB-INF/conf/accountant.properties";
	    
	    // 2. Ask the Servlet Context to translate that into an absolute hard-drive path
	    String absolutePath = getServletContext().getRealPath(relativePath);
	    
	    
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
		
		String reportOption = request.getParameter("ReportSelection"); //JSP uses a select menu titled ReportSelection
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try(Connection conn = DriverManager.getConnection(url,username,password)) {
				CallableStatement cs = null;
				ResultSet RS = null;
				StringBuilder html = new StringBuilder();
				
				if("MaxStatus".equals(reportOption)) {
					cs = conn.prepareCall("{call Get_The_Maximum_Status_Of_All_Suppliers()}");
				} else if ("TotalWeight".equals(reportOption)) {
					cs = conn.prepareCall("{call Get_The_Sum_Of_All_Parts_Weights()}");
				} else if ("TotalShipments".equals(reportOption)) {
					cs = conn.prepareCall("{call Get_The_Total_Number_Of_Shipments()}");
				} else if ("MostWorkers".equals(reportOption)) {
					cs = conn.prepareCall("{call Get_The_Name_Of_The_Job_With_The_Most_Workers()}");
				} else if("AllSuppliers".equals(reportOption)) {
					cs = conn.prepareCall("{call List_The_Name_And_Status_Of_All_Suppliers()}");
				}
				
				if(cs != null) {
					RS = cs.executeQuery();
					
					ResultSetMetaData rsmd = RS.getMetaData();
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
				}
				request.setAttribute("results", html.toString());
				request.getRequestDispatcher("accountantHome.jsp").forward(request, response);
				
				
			} catch (Exception e) {
				try{
					request.setAttribute("errorMessage", e.getMessage());
	            	request.getRequestDispatcher("Errorpage.jsp").forward(request, response);
	            	e.printStackTrace();
				} catch (Exception e2) {
					e2.printStackTrace();
					//html.append(e2.printStackTrace());
				}
				
			}
		} catch (Exception e) {
			try {
				request.setAttribute("errorMessage", e.getMessage());
            	request.getRequestDispatcher("Errorpage.jsp").forward(request, response);
            	e.printStackTrace();
			} catch (Exception e5) {
				e5.printStackTrace();
			}
		}
		
		
	}
}
