package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connect {
    private static final String driver = "com.mysql.cj.jdbc.Driver" ;
    private static final String url = "jdbc:mysql://localhost:3306/gravity2?characterEncoding=utf8&useSSL=false&serverTimezone=UTC" ;
    private static final String user = "jrb" ;
    private static final String pass = "jrb314159" ;
    private Connection conn=null;
    
    public Connect() {
    	
    }
	public Connection getConnection()  {
		if(conn!=null) {
			//System.out.println("success> get connection");
			return conn;
		}
		//System.out.println("start> connect database:");
		try {
			Class.forName(driver);
		}catch (ClassNotFoundException e) {
			System.out.println("false> connect/forname:"+e);
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url,user,pass) ;
		}catch (SQLException e) {
			System.out.println("false> connect/getConnection:"+e);
			e.printStackTrace();
		}
		//System.out.println("success> connect database:"+conn+" ");
		return conn;
	}
	
	public Connection endConnection()  {
		try {
			if(conn!=null) {
				conn.close();
			}
		}catch (Exception e) {
			System.out.println("false> connect/getConnection:"+e);
			e.printStackTrace();
		}
		System.out.println("end> close connect:"+conn);
		return conn;
	}
	public static boolean closeRs(ResultSet rs)  {
		try {
			if(rs!=null) {
				rs.close();
			}
		}catch (Exception e) {
			System.out.println("false> connect/closeRs:"+e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean closePstm(PreparedStatement pstm)  {
		try {
			if(pstm!=null) {
				pstm.close();
			}
		}catch (Exception e) {
			System.out.println("false> connect/closePstm:"+e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
