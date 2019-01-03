package entity;

import java.util.Arrays;

public class ManageLj extends Entity{
	String tableJoin;
	public ManageLj(String tableJoin) {
		super();
		String[] attribute={"hdid","userid","headpic","nname","name","email","adress","job","area","flag"};
		if(tableJoin.equals("userjoin")) {
			super.attribute=attribute;
		}else {
			super.attribute=Arrays.copyOfRange(attribute,0,attribute.length-2);
		}
		super.tableName="user,"+tableJoin;
		this.tableJoin=tableJoin;
	}
	public boolean sqlSelect() {
		String[] attribute=new String[this.attribute.length];
		for(int i=0;i<attribute.length;i++) {
			attribute[i]=this.attribute[i];
		}
		attribute[1]="user.userid";
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
		//add join condition
		sqlbuf.append(" and user.userid="+tableJoin+".userid");
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
}