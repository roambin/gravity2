package entity;

public class EntityAttributeTableManual extends EntityManual{
	public EntityAttributeTableManual() {
		super();
		super.attribute=null;
		super.tableName=null;
	}
	public EntityAttributeTableManual(String attributeName,String tableName) {
		super();
		String[] attribute={attributeName};
		super.attribute=attribute;
		super.tableName=tableName;
	}
	public EntityAttributeTableManual(String[] attribute,String tableName) {
		super();
		super.attribute=attribute;
		super.tableName=tableName;
	}
	public void setAttribute(String attributeName) {
		String[] attribute={attributeName};
		this.attribute=attribute;
	}
	public void setAttribute(String[] attribute) {
		this.attribute=attribute;
	}
	public void setTableName(String tableName) {
		this.tableName=tableName;
	}
}
