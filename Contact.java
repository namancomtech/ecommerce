package comtech.naman;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Contact extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
				
		PrintWriter out= resp.getWriter();
		
		String name= req.getParameter("user_name");
		String email= (String) req.getParameter("user_email");
		String msg= (String)  req.getParameter("user_msg");
		
		try {
			if(name.isEmpty() ||email.isEmpty() ||msg.isEmpty() )
			{
				resp.setContentType("text/html");
				out.println("<h4 style='color:red;font-family:TimesNewRoman;'>Datafield is Blank, Cannot be Submitted !!</h4>");
				RequestDispatcher rd= req.getRequestDispatcher("/contact.html");
				rd.include(req, resp);
			}
			
			else {	
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/namancomtech","root","2164");
				
				PreparedStatement ps= con.prepareStatement("insert into contactus values(?,?,?)");
				ps.setString(1,name);
				ps.setString(2,email);
				ps.setString(3,msg);
				
				int result= ps.executeUpdate();
				if(result > 0)
				{
					ps.close();
					con.close();
					resp.setContentType("text/html");
					out.print("<h3 style='color:blue;'>Data Submitted Successfully</h3>");
					RequestDispatcher rd= req.getRequestDispatcher("/contact.html");
					rd.include(req, resp);
				}
				
				else {
					ps.close();
					con.close();
					resp.setContentType("text/html");
					out.print("<h2 style='color:red;font-family:TimesNewRoman;'>Data is Unable to Submit</h2>");
					RequestDispatcher rd= req.getRequestDispatcher("/contact.html");
					rd.include(req, resp);
				}
					
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}

}
