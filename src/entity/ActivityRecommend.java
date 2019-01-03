package entity;

public class ActivityRecommend extends ActivityPlus{
	public ActivityRecommend() {
		super();
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
		sqlbuf.append(" where hdid in (select hdid from activity_recommend where userid=").append(userid).append(")");
		//add where condition
		if(!mapCondition.isEmpty()) {
			sqlbuf.append(" and delflag<>'已删除'");
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