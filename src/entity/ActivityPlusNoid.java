package entity;

import java.util.Map;

import org.json.JSONObject;

public class ActivityPlusNoid extends Entity{
	public ActivityPlusNoid() {
		super();
		String[] attribute={"hdid","hdm","stime","etime","place","shdjj","hdjj","pic",
				"classify","timeflag","maxpeo","delflag","delday","crashflag",
				"joinnum","likenum","fqr"};
		super.attribute=attribute;
		super.tableName="hdplusnoidv";
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
		//add limit
		if(limit[0]!=null) {
			sqlbuf.append(" limit ").append(limit[0]).append(",").append(limit[1]);
		}
		String sql=new String(sqlbuf);
		return submitDatabaseResult(sql);
	}
	public String getValue(String key) {
		if(map.get(key)!=null) {
			return(map.get(key));
		}else {
			if(key.equals("addflag")) {
				return "未报名";
			}
		}
		return "0";
	}
	public JSONObject toJsonObject() {
		JSONObject jsonObject= new JSONObject();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}
		jsonObject.put("jointemp", "0");
		jsonObject.put("liketemp", "0");
		jsonObject.put("hyjoinnum", "0");
		jsonObject.put("hylikenum", "0");
		jsonObject.put("addflag", "未参加");
		return jsonObject;
	}
}
