package entity;

public class Activity extends Entity{
	public Activity() {
		super();
		String[] attribute={"hdid","hdm","stime","etime","place","shdjj","hdjj","pic","classify","timeflag","maxpeo","delflag","delday","crashflag"};
		super.attribute=attribute;
		super.tableName="hd";
	}
}
