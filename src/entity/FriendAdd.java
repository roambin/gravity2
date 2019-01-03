package entity;

import java.sql.SQLException;

public class FriendAdd extends Entity{
	String userid;
	public FriendAdd(String userid) {
		super();
		String[] attribute={"userid","headpic","nname","name","email","adress","job","area","flag"};
		super.attribute=attribute;
		super.tableName="user";
		this.userid=userid;
	}
	public boolean sqlSelect() {
		String[] attribute={"user.userid","headpic","nname","name","email","adress","job","area","flag"};
		StringBuffer sqlbuf=new StringBuffer();
		//piece sql
		sqlbuf.append("select ");
		for (String i:attribute) {
			sqlbuf.append(i).append(",");
		}
		sqlbuf.deleteCharAt(sqlbuf.length()-1);
		sqlbuf.append(" from ").append(tableName);
		//add left join
		sqlbuf.append(" left join hyv on hyid=user.userid and hyv.userid="+this.userid);
		//add where condition
		sqlbuf.append(" where 1=1");
		if(!mapCondition.isEmpty()) {
			sqlAddCondition(sqlbuf);
		}
		//select friend add condition
		sqlbuf.append(" and user.userid<>"+this.userid);
		//add limit
		if(limit[0]!=null) {
			sqlbuf.append(" limit ").append(limit[0]).append(",").append(limit[1]);
		}
		String sql=new String(sqlbuf);
		return submitDatabaseResult(sql);
	}
	public void setSqlCondition(String key,String value) {
		if(key.equals("userid")) {
			key="user.userid";
		}
		mapCondition.put(key, value);
	}
	public boolean setObject() {
		try {
			if(this.isHasNextResult()) {
				for(int i=0;i<attribute.length;i++) {
					map.put(attribute[i],rs.getString(i+1));
				}
				if(map.get("flag")==null) {
					map.put("flag","未添加");
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
}