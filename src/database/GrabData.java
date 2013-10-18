package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ethan.Li
 * 
 */
public class GrabData {
	private List<SearchResult> searchresult = new ArrayList<SearchResult>();
	private List<SearchShift> searchshift = new ArrayList<SearchShift>();
	private SearchResult tempresult = new SearchResult();
	private SearchShift tempshift = new SearchShift();
	
	public GrabData() {
	}
	
	public List<SearchResult> SearchResult(){
		searchresult.clear();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/sengine", "admin", "1111");

			stmt = conn.createStatement();

			rs = stmt.executeQuery("select * from searchresult");

			while (rs.next()) {
				tempresult = new SearchResult();
				tempresult.setIndex(rs.getString("index"));
				tempresult.setUrl(rs.getString("url"));
				tempresult.setDescription(rs.getString("description"));
				searchresult.add(tempresult);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}		
		
		
		return this.searchresult;
	}
	
	
	public List<SearchShift> SearchShift(){
		searchshift.clear();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/sengine", "admin", "1111");

			stmt = conn.createStatement();

			rs = stmt.executeQuery("select * from searchshift");

			while (rs.next()) {
				tempshift = new SearchShift();
				tempshift.setIndex(rs.getString("index"));
				tempshift.setDescription(rs.getString("description"));
				searchshift.add(tempshift);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}		
		
		return this.searchshift;
	}
	
}
