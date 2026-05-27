import java.io.*;
import java.sql.*;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;

public class RootServlet extends HttpServlet{
	Properties prop;
	public void init() {
	    prop = new Properties();
	    
	    // 1. Define the relative path starting from the root of your webapp
	    // Note: It MUST start with a forward slash "/"
	    String relativePath = "/WEB-INF/conf/root.properties";
	    
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
		String sql = request.getParameter("sqlCommand");
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
				
				String sql_lower = sql.toLowerCase();
				
				if(sql_lower.contains("insert")||sql_lower.contains("update") && sql_lower.contains("shipments")) {
					
					boolean quantityMeetsThreshold = sql_lower.matches(".*\\b([1-9][0-9]{2,})\\b.*");//regex
					
					if(quantityMeetsThreshold && rows > 0) {
						String update_suppsSQL = "UPDATE suppliers SET status = status + 5 WHERE snum IN (SELECT DISTINCT snum FROM shipments WHERE quantity >= 100)";
						int supprows=stmt.executeUpdate(update_suppsSQL);
						
						html.append("<div style='background-color: lightgreen;'>");
			            html.append("The statement executed successfully. <br>").append(rows).append(" row(s) affected.<br>");
			            html.append("Order >= 100 Detected! - Updating Supplier Status<br>");
			            html.append("Supplier Status updated ").append(supprows).append(" supplier status marks.");
			            html.append("</div>");
						
					} else {
						// Logic not triggered (e.g., quantity was < 100)
			            html.append("<div style='background-color: lightgreen;'>");
			            html.append("The statement executed successfully. <br>").append(rows).append(" row(s) affected.<br>");
			            html.append("Supplier Status Update Not Triggered!");
			            html.append("</div>");
					}
				}
				
				
			}
			
			request.setAttribute("results", html.toString());
			request.getRequestDispatcher("rootHome.jsp").forward(request, response);
			
			
			
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
	
	//after any insert/update on shipments with quantity >= 100, update status +5 for all affected suppliers

}
