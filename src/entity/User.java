package entity;

public class User extends Entity{
	public User() {
		super();
		String[] attribute={"userid","headpic","nname","name","email","adress","job","username","password","area"};
		super.attribute=attribute;
		super.tableName="user";
	}
}
