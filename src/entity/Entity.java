package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import database.Connect;

public class Entity {
	protected String[] attribute;
	protected String tableName;
	protected String condition;
	
	protected Connect connect;
	protected Connection conn;
	protected PreparedStatement pstm;
	public ResultSet rs;
	protected Map<String, String> map;
	protected Map<String, String> mapCondition;
	protected Integer[] limit;
	protected String order;
	protected Entity() {
		connect=new Connect();
		pstm=null;
		rs=null;
		map=new LinkedHashMap<String, String>();
		mapCondition=new LinkedHashMap<String, String>();
		limit=new Integer[2];
		order=null;
	}
	
	protected boolean submitDatabaseResult(String sql) {
		boolean issuccess=false;
		System.out.println("sql> Entity/submitDatabaseResult:"+sql);
		try {
			conn=connect.getConnection();
			pstm= conn.prepareStatement(sql) ;
			rs = pstm.executeQuery();
			System.out.println("success> Entity/submitDatabaseResult:");
			issuccess=true;return issuccess;
		}catch (SQLException e) {
			System.out.println("false> Entity/submitDatabaseResult:");
			e.printStackTrace();
		}
		return issuccess;
	}
	public boolean isHasNextResult() {
		boolean isClosed=true;
		if(rs==null) {
			System.out.println("return> "+this.getClass().getName()+"/isHasNextResult:rs is null");
			return false;
		}
		try {
			isClosed = rs.isClosed();
		} catch (SQLException e1) {
			System.out.println("false> "+this.getClass().getName()+"/isClosed:");
			e1.printStackTrace();
		}
		if(isClosed) {
			//System.out.println("return> "+this.getClass().getName()+"/isHasNextResult:rs is closed");
			return false;
		}
		try {
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("false> "+this.getClass().getName()+"/isHasNextResult:");
			e.printStackTrace();
		}
		Connect.closeRs(rs);
		Connect.closePstm(pstm);
		connect.endConnection();
		return false;
	}
	public boolean isHasResult() {
		boolean isClosed=true;
		if(rs==null) {
			System.out.println("return> "+this.getClass().getName()+"/isHasNextResult:rs is null");
			return false;
		}
		try {
			isClosed = rs.isClosed();
		} catch (SQLException e) {
			System.out.println("false> "+this.getClass().getName()+"/isClosed:");
			e.printStackTrace();
		}
		if(isClosed) {
			//System.out.println("return> "+this.getClass().getName()+"/isHasNextResult:rs is closed");
			return false;
		}
		try {
			if(!rs.isAfterLast()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("false> "+this.getClass().getName()+"/isHasResult:");
			e.printStackTrace();
		}
		Connect.closeRs(rs);
		Connect.closePstm(pstm);
		connect.endConnection();
		return false;
	}
	protected boolean submitDatabase(String sql) {
		boolean issuccess=false;
		System.out.println("sql> Entity/submitDatabase:"+sql);
		try {
			conn=connect.getConnection();
			pstm= conn.prepareStatement(sql) ;
			pstm.execute();
			System.out.println("success> Entity/submitDatabase:");
			issuccess=true;return issuccess;
		} catch (SQLException e) {
			System.out.println("false> Entity/submitDatabase:");
			e.printStackTrace();
		} finally {
			Connect.closePstm(pstm);
			connect.endConnection();
		}
		return issuccess;
	}
	
	public boolean setObject() {
		try {
			if(this.isHasNextResult()) {
				for(int i=0;i<attribute.length;i++) {
					map.put(attribute[i],rs.getString(i+1));
				}
				return true;
			}
		} catch (SQLException e) {
			System.out.println("false> "+this.getClass().getName()+"/setObject:");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("false> "+this.getClass().getName()+"/setObject:");
			//System.out.println(e);
			e.printStackTrace();
		}
		return false;
	}
	public String getValue(String key) {
		return(map.get(key));
	}
	public void setValue(String key,String value) {
		map.put(key, value);
	}
	
	public boolean sqlSelect() {
		StringBuffer sqlbuf=new StringBuffer();
		//piece sql
		sqlbuf.append("select ");
		for (String i:attribute) {
			sqlbuf.append(i).append(",");
		}
		sqlbuf.deleteCharAt(sqlbuf.length()-1);
		sqlbuf.append(" from ").append(tableName);
		//add where condition
		if(!mapCondition.isEmpty()) {
			sqlbuf.append(" where 1=1");
			sqlAddCondition(sqlbuf);
		}
		//add order by
		if(order!=null) {
			sqlbuf.append(" order by ").append(order);
		}
		//add limit
		if(limit[0]!=null) {
			sqlbuf.append(" limit ").append(limit[0]).append(",").append(limit[1]);
		}
		String sql=new String(sqlbuf);
		return submitDatabaseResult(sql);
	}
	public boolean sqlUpdate() {
		StringBuffer sqlbuf=new StringBuffer();
		sqlbuf.append("update ").append(tableName).append(" set ");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sqlbuf.append(entry.getKey()).append("='").append(entry.getValue()).append("',");
		}
		sqlbuf.deleteCharAt(sqlbuf.length()-1);
		//sqlbuf.append(" where ").append(primaryKey).append("='").append(map.get(primaryKey)).append("'");
		//add where condition
		if(!mapCondition.isEmpty()) {
			sqlbuf.append(" where 1=1");
			sqlAddCondition(sqlbuf);
		}
		String sql=new String(sqlbuf);
		return submitDatabase(sql);
	}
	public boolean sqlUpdateNoQuot() {
		StringBuffer sqlbuf=new StringBuffer();
		sqlbuf.append("update ").append(tableName).append(" set ");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sqlbuf.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
		}
		sqlbuf.deleteCharAt(sqlbuf.length()-1);
		//sqlbuf.append(" where ").append(primaryKey).append("='").append(map.get(primaryKey)).append("'");
		//add where condition
		if(!mapCondition.isEmpty()) {
			sqlbuf.append(" where 1=1");
			sqlAddCondition(sqlbuf);
		}
		String sql=new String(sqlbuf);
		return submitDatabase(sql);
	}
	public boolean sqlInsert() {
		StringBuffer sqlbuf=new StringBuffer();
		sqlbuf.append("insert into ").append(tableName).append(" (");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sqlbuf.append(entry.getKey()).append(",");
		}
		sqlbuf.deleteCharAt(sqlbuf.length()-1).append(") values (");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sqlbuf.append("'").append(entry.getValue()).append("',");
		}
		sqlbuf.deleteCharAt(sqlbuf.length()-1).append(")");
		String sql=new String(sqlbuf);
		return submitDatabase(sql);
	}
	public boolean sqlInsertNoQuot() {
		StringBuffer sqlbuf=new StringBuffer();
		sqlbuf.append("insert into ").append(tableName).append(" (");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sqlbuf.append(entry.getKey()).append(",");
		}
		sqlbuf.deleteCharAt(sqlbuf.length()-1).append(") values (");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sqlbuf.append(entry.getValue()).append(",");
		}
		sqlbuf.deleteCharAt(sqlbuf.length()-1).append(")");
		String sql=new String(sqlbuf);
		return submitDatabase(sql);
	}
	public boolean sqlDelete() {
		StringBuffer sqlbuf=new StringBuffer();
		sqlbuf.append("delete from ").append(tableName);
		//sqlbuf.append(" where ").append(primaryKey).append("='").append(map.get(primaryKey)).append("'");
		//add where condition
		if(!mapCondition.isEmpty()) {
			sqlbuf.append(" where 1=1");
			sqlAddCondition(sqlbuf);
		}
		String sql=new String(sqlbuf);
		return submitDatabase(sql);
	}
	public void clearObject() {
		map.clear();
	}
	public void setSqlCondition(String key,String value) {
		mapCondition.put(key, value);
	}
	public void delSqlCondition(String key) {
		mapCondition.remove(key);
	}
	public void clearSqlCondition() {
		mapCondition.clear();
	}
	protected void sqlAddCondition(StringBuffer sqlbuf) {
		if(!mapCondition.isEmpty()) {
			for (Map.Entry<String, String> entry : mapCondition.entrySet()) {
				sqlbuf.append(" and ").append(entry.getKey()).append("='").append(entry.getValue()).append("'");
			}
		}
	}

	/*
	public boolean isHasPrimaryKey() {
		if(primaryKey==null) {
			try {
				throw new Exception("this child class has no primary key and can only excute select");
			} catch (Exception e) {
				System.out.println("false> "+this.getClass().getName()+"/isHasPrimaryKey:");
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}*/
	
	//json
	public JSONObject toJsonObject() {
		JSONObject jsonObject= new JSONObject();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}
		return jsonObject;
	}
	public void setSqlLimit(int begin,int length) {
		limit[0]=begin;
		limit[1]=length;
	}
	public void setSqlOrder(String order) {
		this.order=order;
	}
	public void sqlDelLimit() {
		limit[0]=null;
		limit[1]=null;
	}
}

