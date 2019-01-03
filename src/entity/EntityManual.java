package entity;

import java.sql.Connection;
import java.sql.SQLException;

import database.Connect;

public class EntityManual extends Entity{
	protected boolean submitDatabaseResult(String sql) {
		boolean issuccess=false;
		System.out.println("sql> Entity/submitDatabaseResult:"+sql);
		try {
			pstm= conn.prepareStatement(sql) ;
			rs = pstm.executeQuery();
			System.out.println("success> Entity/submitDatabaseResult:");
			issuccess=true;return issuccess;
		} catch (SQLException e) {
			System.out.println("false> Entity/submitDatabaseResult:");
			e.printStackTrace();
		} finally {
			//Connect.closePstm(pstm);
		}
		return issuccess;
	}
	protected boolean submitDatabase(String sql) {
		boolean issuccess=false;
		System.out.println("sql> Entity/submitDatabase:"+sql);
		try {
			pstm= conn.prepareStatement(sql) ;
			pstm.execute();
			System.out.println("success> Entity/submitDatabase:");
			issuccess=true;return issuccess;
		} catch (SQLException e) {
			System.out.println("false> Entity/submitDatabase:");
			e.printStackTrace();
		} finally {
			Connect.closePstm(pstm);
			//Connect.closePstm(pstm);
		}
		return issuccess;
	}
	public void setConnection(Connection conn) {
		this.conn=conn;
	}
}

