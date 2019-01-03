package javaBean;

public class TokenObject {
	private String userid;
	private String username;
	private String nname;
	private String headpic;

	public TokenObject() {
		
	}
	public TokenObject(String userid,String username,String nname,String headpic) {
		this.userid=userid;
		this.username=username;
		this.nname=nname;
		this.headpic=headpic;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNname() {
		return nname;
	}

	public void setNname(String nname) {
		this.nname = nname;
	}

	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

}

