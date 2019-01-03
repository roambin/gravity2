package entity;

public class FriendRecommend extends Entity{
	public FriendRecommend() {
		super();
		String[] attribute={"userid","headpic","nname","name","email","adress","job","area"};
		super.attribute=attribute;
		super.tableName="user";
	}
	public boolean sqlSelect(String userid) {
		StringBuffer sqlbuf=new StringBuffer();
		//piece sql
		sqlbuf.append("select ");
		for (String i:attribute) {
			sqlbuf.append(i).append(",");
		}
		sqlbuf.deleteCharAt(sqlbuf.length()-1);
		sqlbuf.append(" from ").append(tableName);
		//add in condition
		sqlbuf.append(" where userid not in (select hyid from hyv where userid="+userid+") and userid in (select hyid from friend_recommend where userid=").append(userid).append(")");
		//add where condition
		if(!mapCondition.isEmpty()) {
			sqlAddCondition(sqlbuf);
		}
		//add limit
		if(limit[0]!=null) {
			sqlbuf.append(" limit ").append(limit[0]).append(",").append(limit[1]);
		}
		String sql=new String(sqlbuf);
		return submitDatabaseResult(sql);
	}
}