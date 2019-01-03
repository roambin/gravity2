package entity;

public class ActivityPlusLj extends Entity{
	String userid,tableSon;
	public ActivityPlusLj(String userid,String tableSel) {
		super();
		String[] attribute={"userid","hdid","hdm","stime","etime","place","shdjj","hdjj","pic",
				"classify","timeflag","maxpeo","delflag","delday","crashflag",
				"addflag","jointemp","liketemp","joinnum","likenum","hyjoinnum","hylikenum","fqr"};
		super.attribute=attribute;
		super.tableName="hdplusv";
		this.userid=userid;
		this.tableSon="user"+tableSel;
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
			sqlbuf.append(" where delflag<>'已删除'");
			sqlAddCondition(sqlbuf);
		}
		sqlbuf.append(" and hdid in(select hdid from ").append(tableSon).append(" where userid='").append(userid).append("')");
		//add limit
		if(limit[0]!=null) {
			sqlbuf.append(" limit ").append(limit[0]).append(",").append(limit[1]);
		}
		String sql=new String(sqlbuf);
		return submitDatabaseResult(sql);
	}
}
