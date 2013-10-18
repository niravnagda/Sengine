package database;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ethan.Li
 * 
 */
public class Test {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//
		// Test Database
		//
//		@SuppressWarnings("unused")
//		List<SearchResult> searchresult = new ArrayList<SearchResult>();
//		@SuppressWarnings("unused")
//		List<SearchShift> searchshift = new ArrayList<SearchShift>();
//		GrabData temp = new GrabData();
//		searchresult = temp.SearchResult();
//		searchshift = temp.SearchShift();
//		for (SearchShift sf : searchshift){
//			System.out.println(sf.getDescription());
//		}
//		for (SearchResult sr : searchresult){
//			System.out.println(sr.getDescription());
//		}		
		//
		// insert database
		//
		 String tempstring = null;
		 Connection conn = null;
		 Statement stmt = null;
		 List<IndexItem> shiftedLines = null;
		 try {
		 FileReader fr = new FileReader("input.txt");
		 BufferedReader br = new BufferedReader(fr);
		 String myreadline;
		 String temp[];
		
		 try {
		 Class.forName("com.mysql.jdbc.Driver");
		 } catch (ClassNotFoundException ex) {
		 ex.printStackTrace();
		 }
		 try {
		 conn = DriverManager.getConnection(
		 "jdbc:mysql://127.0.0.1:3306/sengine", "admin", "1111");
		
		 stmt = conn.createStatement();
		
		 while (br.ready()) {
		 myreadline = br.readLine();
		 temp = myreadline.split(":");
		 tempstring =
		 "insert into searchresult (`index`,`url`,`description`) values(\""
		 + temp[0] + "\",\"" + temp[1] + "\",\"" + temp[2] + "\");";
		 System.out.println(tempstring);
		 stmt.executeUpdate(tempstring);
		 shiftedLines = IndexBuilder.execute(temp[2]);
		 SortLines.sort(shiftedLines);
		 for (IndexItem l : shiftedLines) {
		 tempstring =
		 "insert into searchshift (`index`,`description`) values(\""
		 + temp[0] + "\",\"" + l.getSentence() + "\");";
		 System.out.println(tempstring);
		 stmt.executeUpdate(tempstring);
		 }
		 }
		
		 } catch (Exception ex) {
		 ex.printStackTrace();
		 } finally {
		 try {
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
		
		 br.close();
		 fr.close();
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
	}
	
}
