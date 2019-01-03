package listener;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import database.Connect;
import entity.EntityAttributeTableManual;

public class FriendRecommend {  
	public static void main(String args[]){
		FriendRecommend.setFriendRecommend();
	}
    public static void setFriendRecommend() {  
    		System.out.println("listener>FriendRecommend.setFriendRecommend");
        System.out.println("        >开始更新推荐表（协同过滤）"); 
        Connect connect=new Connect();
        Connection conn=connect.getConnection();
        int begin=0,length=1000,precision=1000,recommendLength=10;
        ArrayList<ArrayList<Integer>> joinList=new ArrayList<ArrayList<Integer>>();
        List<Integer> userList=new ArrayList<Integer>();
        Integer[][] similarity;
        EntityAttributeTableManual tableJoin=new EntityAttributeTableManual("hdid","userjoinlikev");
        EntityAttributeTableManual tableId=new EntityAttributeTableManual("userid","user");
        EntityAttributeTableManual tableRecommend=new EntityAttributeTableManual(new String[]{"userid","hyid"},"friend_recommend");
        tableJoin.setConnection(conn);
        tableId.setConnection(conn);
        tableRecommend.setConnection(conn);
        tableId.setSqlOrder("rand()");
        tableId.sqlSelect();
		tableRecommend.sqlDelete();
        while(tableId.isHasResult()) {
        		//set join activities list
        		String userid=null;
        		for(begin=0;tableId.setObject()&&begin<length;begin++) {
        			userid=tableId.getValue("userid");
        			userList.add(Integer.parseInt(userid));
        			tableJoin.setSqlCondition("userid",userid);
        			tableJoin.sqlSelect();
        			ArrayList<Integer> joinListElem=new ArrayList<Integer>();
        			while(tableJoin.setObject()) {
        				joinListElem.add(Integer.parseInt(tableJoin.getValue("hdid")));
        			}
        			joinList.add(joinListElem);
        		}
        		/*//print userid: joinList
        		for(int i=0;i<joinList.size();i++) {
        			System.out.print(userList.get(i)+": ");
        			for(int j=0;j<joinList.get(i).size();j++) {
        				System.out.print(joinList.get(i).get(j)+" ");
        			}
        			System.out.println();
        		}*/
        		//calculate similarity
        		int thisIndex,comIndex;
        		similarity=new Integer[begin][begin];
        		for(int i=0;i<begin;i++) {
        			ArrayList<Integer> thisList=joinList.get(i);
        			for(int j=i+1;j<begin;j++) {
        				ArrayList<Integer> comList=joinList.get(j);
        				thisIndex=comIndex=0;
        				similarity[i][j]=0;
        				while(thisIndex<thisList.size()&&comIndex<comList.size()) {
        					if(thisList.get(thisIndex)<comList.get(comIndex)) {
        						thisIndex++;
        					}else if(thisList.get(thisIndex)>comList.get(comIndex)) {
        						comIndex++;
        					}else {
        						similarity[i][j]++;
        						thisIndex++;
        						comIndex++;
        					}
        				}
        				similarity[i][j]*=precision*precision;
        				int division=(int)(precision*Math.sqrt(thisList.size()*comList.size()));
        				similarity[i][j]=division!=0?similarity[i][j]/division:0;
        				similarity[j][i]=similarity[i][j];
        			}
        		}
        		/*//print similarity
        		for(int i=0;i<begin;i++) {
        			for(int j=0;j<begin;j++) {
        				System.out.printf("%6d",similarity[i][j]);
        			}
        			System.out.println();
        		}*/
        		//set friend_recommend
        		ArrayList<Integer> recommendList=new ArrayList<Integer>();
        		for(int i=0;i<userList.size();i++) {
        			//set recommendList
        			for(int j=0;j<userList.size();j++) {
        				if(j==i) {
        					continue;
        				}
        				if(recommendList.size()<recommendLength||similarity[i][j]>similarity[i][recommendList.get(0)]) {
        					if(recommendList.size()==0) {
        						recommendList.add(0,j);
        						continue;
        					}else if(recommendList.size()>=recommendLength) {
	        					recommendList.remove(0);
	        				}
	        				//binary search the adding index
	        				int index,left=0,right=recommendList.size()-1;
        					while(right-left>1) {
        						int mid=left+(right-left)/2;
        						//System.out.println(left+" "+right+" "+mid);
        						if(similarity[i][j]>similarity[i][recommendList.get(mid)]) {
        							left=mid;
        						}else {
        							right=mid;
        						}
        					}
        					if(similarity[i][j]<similarity[i][recommendList.get(left)]) {
        						index=left;
        					}else if(similarity[i][j]>similarity[i][recommendList.get(right)]) {
        						index=right+1;
        					}else {
        						index=right;
        					}
	        				recommendList.add(index,j);
        				}
        			}
        			/*//print recommendList
        			for(int j=0;j<recommendList.size();j++) {
        				System.out.printf("%6d",recommendList.get(j));
        			}*/
        			System.out.println();
        			//insert into friend_recommend
        			for(int j=0;j<recommendList.size();j++) {
	        			tableRecommend.setValue("userid", userList.get(i).toString());
	        			tableRecommend.setValue("hyid", userList.get(recommendList.get(j)).toString());
	        			tableRecommend.sqlInsert();
        			}
        			recommendList.clear();
        		}
        }
        connect.endConnection();
    }  
}  
