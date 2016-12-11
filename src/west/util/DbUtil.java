package west.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class DbUtil {
	
	private static Connection con = null;

	private DbUtil() {}
	
	public static Connection getConnection() {
		//객체를 리턴시켜주는 static한 getter 메소드
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "west";	
		String pwd = "1234";
		
		if (con == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection(url, user, pwd);
			} catch (Exception e) {
				System.out.println("예외 : " + e.toString());
			}
		}
		return con;
	}
	
	public static void close() {
		if (con == null) return;

		try {
			if (!con.isClosed())
				con.close();
		} catch (Exception e) {
			System.out.println("예외 : " + e.toString());
		}
		con = null;
	}
	
	public static void close(Statement stmt) {
		try {
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Vector<String> getColumnNames(ResultSet rs) {
	    if (rs == null) return null;
	    
	    Vector<String> columnName = new Vector<>();
		try {
			ResultSetMetaData rsMetaData = rs.getMetaData();
		
		    int numberOfColumns = rsMetaData.getColumnCount();
		    
		    for (int i = 1; i <= numberOfColumns; i++)
		      columnName.add(rsMetaData.getColumnName(i));
	    
		} catch (SQLException se) {
			se.printStackTrace();
		}
		
		return columnName;
	}
	
	public static Vector<Vector<Object>> getRecordAll
							(Vector<String> columnNames, ResultSet rs){
		Vector<Vector<Object>> list = new Vector<>();
		
		try {
			while (rs.next()) {
				Vector<Object> row = new Vector<>();
				columnNames.forEach(name -> {
					try {
//						if (name.equals("RESDATE") || name.equals("startdate") ||
//								name.equals("enddate")) {
//							SimpleDateFormat format = 
//									new SimpleDateFormat("yyyyMMdd");
//							Date Date = 
//									format.parse(rs.getObject(name).toString());
//							System.out.println(rs.getObject(name).toString());
//							row.add(Date);
//						} else 
							row.add(rs.getObject(name)); 
						
						}
					catch (SQLException e) {
						System.err.println("DefaultTable 예외");
					}
				});
				list.add(row);
			}
		} catch(Exception e) {
			System.err.println("전체 레코드 반환시 오류: " + e.getMessage());
		}
		
		return list;
	}

}

