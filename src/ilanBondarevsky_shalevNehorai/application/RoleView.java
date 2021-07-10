package ilanBondarevsky_shalevNehorai.application;

public class RoleView {
 	private	int roleId;
 	private String deparmentName;
 	
	public RoleView(int roleId, String deparmentName) {
		this.roleId = roleId;
		this.deparmentName = deparmentName;
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public String getDeparmentName() {
		return deparmentName;
	}
}
