package entity;

public class FriendMy extends Entity{
	public FriendMy() {
		super();
		String[] attribute={"userid","hyid","flag","headpic","nname","name","email","adress","job","area"};
		super.attribute=attribute;
		super.tableName="user,hyv";
	}
	public boolean sqlSelect() {
		String[] attribute={"hyv.userid","hyid","flag","headpic","nname","name","email","adress","job","area"};
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
		sqlbuf.append(" and hyid=user.userid and flag='同意'");
		//add limit
		if(limit[0]!=null) {
			sqlbuf.append(" limit ").append(limit[0]).append(",").append(limit[1]);
		}
		String sql=new String(sqlbuf);
		return submitDatabaseResult(sql);
	}
	public void setSqlCondition(String key,String value) {
		if(key.equals("userid")) {
			key="hyv.userid";
		}
		mapCondition.put(key, value);
	}
}