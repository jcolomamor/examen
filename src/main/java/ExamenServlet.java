import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ExamServlet
 */
@WebServlet("/ExamenServlet")
public class ExamenServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Connection conn = null;
		Statement stmt = null;
		String sqlquery = null;
		try {

			String username = "examenuser";
			String password = "examenpass";
			
			String url= "jdbc:mysql://34.202.254.120:3306/examendaw2";
			conn = DriverManager.getConnection(url, username, password);
			stmt = conn.createStatement();
			
			sqlquery = "SELECT alumnos.nombre, alumnos.apellido , proyectos.nombre as proyecto \r\n"
				+ "from alumnos \r\n"
				+ "join proyectos on alumnos.IdAlumno = proyectos.IdAlumno\r\n"
				+ "join profesores on profesores.IdProfesor = proyectos.IdProfesor \r\n"
				+ "where profesores.nombre =" + "'" + request.getParameter("nombreProfesor") + "'";

			out.println("<html><head><title>Proyectos DAW2 </title>");
		    out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">");
		    out.println("</head><body>");
			out.println("<h1>Los proyectos que van a ser liderados por ");
	        out.println(request.getParameter("nombreProfesor"));
	        out.println("son:</h1>");

			ResultSet rset = stmt.executeQuery(sqlquery);
            int count = 0;

            out.println("<table>");
            out.println("<tr><th>Nombre</th><th>Apellido</th><th>Proyecto</th></tr>");

            while (rset.next()) {
                out.println("<tr>");
                out.println("<td>" + rset.getString("nombre") + "</td>");
                out.println("<td>" + rset.getString("apellido") + "</td>");
                out.println("<td>" + rset.getString("proyecto") + "</td>");
                out.println("</tr>");
                count++;
            }
            out.println("</table>");
            out.println("<p>====" + count + " restultados encontrados ========</p>");

			out.println("</body></html>");
		} catch (Exception ex) {
			out.println(ex.toString());
			ex.printStackTrace();;
		} finally {
			out.println("<p>Tu consulta era: " + sqlquery + "</p>");
			out.close();
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}