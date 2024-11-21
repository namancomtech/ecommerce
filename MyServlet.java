package comtech.naman;

import java.io.IOException;
import java.lang.Class;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class MyServlet extends HttpServlet 
{	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String name = request.getParameter("client_name");
		String email = request.getParameter("client_email");
		String password = request.getParameter("client_password");
		
		System.out.println(name+" "+email+" "+password);
		
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/namancomtech","root","2164");
				PreparedStatement ps = con.prepareStatement("insert into register values(?,?,?)");
				ps.setString(1,name);
				ps.setString(2,email);
				ps.setString(3,password);
				
				int result = ps.executeUpdate();
				if(result > 0)
				{
					System.out.println("Sql Query Successfully Executed");
				}
				else {
					System.out.println("Sql Query Cannot Executed");
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} 
				
}
