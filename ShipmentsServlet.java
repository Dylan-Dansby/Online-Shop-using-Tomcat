import java.io.*;
import java.sql.*;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;

public class ShipmentsServlet extends HttpServlet {
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
	}
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = prop.getProperty("db.url");
        String username = prop.getProperty("db.username");
        String password = prop.getProperty("db.password");
        
        // Extract parameters directly from the Shipments form 
        String snum = request.getParameter("snum");
        String pnum = request.getParameter("pnum");
        String jnum = request.getParameter("jnum");
        String qtyStr = request.getParameter("quantity");
        
        StringBuilder html = new StringBuilder();
        
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Convert quantity to an integer for logic checks
            int quantity = Integer.parseInt(qtyStr);
            
            // 1. Insert the new shipment using PreparedStatement 
            String insertSql = "INSERT INTO shipments (snum, pnum, jnum, quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            
            insertStmt.setString(1, snum);
            insertStmt.setString(2, pnum);
            insertStmt.setString(3, jnum);
            insertStmt.setInt(4, quantity);
            
            int rows = insertStmt.executeUpdate();
            
            if (rows > 0) {
                // Build the base success string 
                html.append("<div style='background-color: blue; color: white; border: 2px solid yellow; padding: 5px;'>");
                html.append("New shipments record: (").append(snum).append(", ")
                    .append(pnum).append(", ").append(jnum).append(", ")
                    .append(quantity).append(") - successfully entered into database.");
                
                // 2. The Business Logic Check 
                if (quantity >= 100) {
                    
                    
                    // Updates status by 5 for ALL suppliers involved in ANY shipment >= 100 
                    /*String updateSql = "UPDATE suppliers SET status = status + 5 WHERE snum IN (SELECT DISTINCT snum FROM shipments WHERE quantity >= 100)";
                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);*/
                    
                    
                    
                     String updateSql = "UPDATE suppliers SET status = status + 5 WHERE snum = ?"; 
                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setString(1, snum); // Only target the directly affected supplier
                    
                    
                    
                    // Execute whichever logic you selected above
                    int logicRows = updateStmt.executeUpdate();
                    
                    // Append the business logic trigger alert [cite: 878]
                    html.append(" Business logic triggered."); 
                }
                
                html.append("</div>");
            }
            
        } catch (SQLException e) {
            html.append("<div style='background-color: red; color: white; padding: 5px;'>");
            html.append("Error executing the SQL statement:<br>");
            html.append(e.getMessage());
            html.append("</div>");
        } catch (NumberFormatException e) {
            html.append("<div style='background-color: red; color: white; padding: 5px;'>");
            html.append("Error: Quantity must be a valid integer.");
            html.append("</div>");
        }
        
        // Return everything to the dataEntryHome.jsp interface
        request.setAttribute("results", html.toString());
        request.getRequestDispatcher("dataEntryHome.jsp").forward(request, response);
    }
}
