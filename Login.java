package comtech.naman;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class Login extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		PrintWriter out=  resp.getWriter();
		String name=  req.getParameter("name");
		String email=  req.getParameter("email");
		String password=  req.getParameter("pass");
		
		try {
			if(name.isEmpty() ||email.isEmpty() ||password.isEmpty() )
			{
				resp.setContentType("text/html");
				out.print("<h4 style='color:red;'>Data is Unable to Submit</h4>");
				RequestDispatcher rd= req.getRequestDispatcher("/login.html");
				rd.include(req, resp);
			}
			else {

				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/namancomtech","root","2164");
				String sqlquery= "select * from signup where email= ? AND pass= ? AND name= ?";
				PreparedStatement ps= con.prepareStatement(sqlquery);
				ps.setString(1,email);
				ps.setString(2,password);
				ps.setString(3,name);
				ResultSet result=  ps.executeQuery();
				
				if(result.next())
				{
					ps.close();
					con.close();
					resp.setContentType("text/html");
					out.print("<h4 style='color:red;font-family:TimesNewRoman;'>Successfully Login</h4>");
					out.print(" Welcome: "+ name);
					RequestDispatcher rd= req.getRequestDispatcher("/index.html");
					rd.include(req, resp);
					
				}
				else {
					ps.close();
					con.close();
					resp.setContentType("text/html");
					out.print("<h4 style='color: red;font-family: TimesNewRoman'>Cannot Login</h4>");
					RequestDispatcher rd= req.getRequestDispatcher("/login.html");
					rd.include(req, resp);
				}
				
				
			}
			
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
			
	}
	
	
	
}
