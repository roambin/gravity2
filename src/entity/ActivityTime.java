package entity;

public class ActivityTime extends Entity{
	String userid,stime,etime;
	public ActivityTime(String userid,String stime,String etime) {
		super();
		String[] attribute={"hdid"};
		super.attribute=attribute;
		super.tableName="hd";
		this.userid=userid;
		this.stime=stime;
		this.etime=etime;
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
		sqlbuf.append(" where hdid in(select hdid from userjoin where userid='").append(userid).append("') and crashflag='可兼报' and (stime>='").append(etime).append("' or etime<='").append(stime).append("')");
		if(!mapCondition.isEmpty()) {
			sqlAddCondition(sqlbuf);
		}
		String sql=new String(sqlbuf);
		return submitDatabaseResult(sql);
	}
}
