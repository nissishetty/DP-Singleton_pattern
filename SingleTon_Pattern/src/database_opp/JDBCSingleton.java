package database_opp;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class JDBCSingleton {
	// Step 1
	// create a JDBCSingleton class.
	// static member holds only one instance of the JDBCSingleton class.

	private static JDBCSingleton jdbc;

	// JDBCSingleton prevents the instantiation from any other class.
	private JDBCSingleton() {
	}

	// Now we are providing gloabal point of access.
	public static JDBCSingleton getInstance() {
		if (jdbc == null) {
			jdbc = new JDBCSingleton();
		}
		return jdbc;
	}

	// to get the connection from methods like insert, view etc.
	private static Connection getConnection() throws ClassNotFoundException, SQLException {

		Connection con = null;
		Class.forName("com.mysql.jdbc.Driver");
		con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/singletondemo", "root", "");
		return con;

	}

//to insert the record into the database   
	public int insert(String name, String pass) throws SQLException {
		Connection c = null;

		PreparedStatement ps = null;

		int recordCounter = 0;

		try {

			c = this.getConnection();
			ps = (PreparedStatement) c.prepareStatement("insert into userdata(uname,upassword)values(?,?)");
			ps.setString(1, name);
			ps.setString(2, pass);
			recordCounter = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (c != null) {
				c.close();
			}
		}
		return recordCounter;
	}

//to view the data from the database        
	public void view(String name) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = this.getConnection();
			ps = (PreparedStatement) con.prepareStatement("select * from userdata where uname=?");
			ps.setString(1, name);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("Name= " + rs.getString(2) + "\t" + "Paasword= " + rs.getString(3));

			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}

}
