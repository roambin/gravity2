package entity;

public class FriendMyAsk extends Entity{
	public FriendMyAsk() {
		super();
		String[] attribute={"userid","hyid","flag","headpic","nname","name","email","adress","job","area"};
		super.attribute=attribute;
		super.tableName="user,hy";
	}
	public boolean sqlSelect() {
		String[] attribute={"hy.userid","hyid","flag","headpic","nname","name","email","adress","job","area"};
		StringBuffer sqlbuf=new StringBuffer();
		//piece sql
		sqlbuf.append("select ");
		for (String i:attribute) {
			sqlbuf.append(i).append(",");
		}
		sqlbuf.deleteCharAt(sqlbuf.length()-1);
		sqlbuf.append(" from ").append(tableName);
		//add where condition
		sqlbuf.append(" where 1=1");
		if(!mapCondition.isEmpty()) {
			sqlAddCondition(sqlbuf);
		}
		//select friend add condition
		sqlbuf.append(" and hyid=user.userid");
		//add limit
		if(limit[0]!=null) {
			sqlbuf.append(" limit ").append(limit[0]).append(",").append(limit[1]);
		}
		String sql=new String(sqlbuf);
		return submitDatabaseResult(sql);
	}
	public void setSqlCondition(String key,String value) {
		if(key.equals("userid")) {
			key="hy.userid";
		}
		mapCondition.put(key, value);
	}
}