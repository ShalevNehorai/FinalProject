package ilanBondarevsky_shalevNehorai.application;

public class EmployeeView {
	
	private int employeeId;
	private int roleId;
	private String departmentName;

	public EmployeeView(String departmentName, int roleId, int employeeId) {
		this.employeeId = employeeId;
		this.roleId = roleId;
		this.departmentName = departmentName;
	}
	
	public int getEmployeeId() {
		return employeeId;
	}

	public int getRoleId() {
		return roleId;
	}

	public String getDepartmentName() {
		return departmentName;
	}
	
	
}
