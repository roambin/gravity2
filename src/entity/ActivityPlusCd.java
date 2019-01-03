package entity;

public class ActivityPlusCd extends Entity{
	String userid,delCondition;
	public ActivityPlusCd(String userid,String delSel) {
		super();
		String[] attribute={"userid","hdid","hdm","stime","etime","place","shdjj","hdjj","pic",
				"classify","timeflag","maxpeo","delflag","delday","crashflag",
				"addflag","jointemp","liketemp","joinnum","likenum","hyjoinnum","hylikenum","fqr"};
		super.attribute=attribute;
		super.tableName="hdplusv";
		this.userid=userid;
		this.delCondition=delSel.equals("delete")?"=":"<>";
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
			sqlbuf.append(" where delflag").append(delCondition).append("'已删除'");
			sqlAddCondition(sqlbuf);
		}
		sqlbuf.append(" and hdid in(select hdid from usercreate where userid='").append(userid).append("')");
		//add limit
		if(limit[0]!=null) {
			sqlbuf.append(" limit ").append(limit[0]).append(",").append(limit[1]);
		}
		String sql=new String(sqlbuf);
		return submitDatabaseResult(sql);
	}
}
