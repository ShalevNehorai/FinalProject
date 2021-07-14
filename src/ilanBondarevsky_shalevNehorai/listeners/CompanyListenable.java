package ilanBondarevsky_shalevNehorai.listeners;

public interface CompanyListenable {	
	void updateCompanyProfit();
	void updateDepartmentProfit(String departmentName);
	void updateRoleProfit(String departmentName, int roleId);
	
	void updateDepartment(String lastDepartmentAdded);
	void updateRole(String departmentName, int lastRoleAddedId);
	void updateEmployee(String departmentName, int lastEmployeeAddedRoleId);
	
	void updateChangeDepartmentHour(String departmentName);
	void updateChangeRoleHour(int roleId, String departmentName);
	
	/**********************************************/
	void addedDepartment(String depName);
	void addedRole(String depName, int roleId);
	void addedEmployee(String depName, int roleId);
	
	void changedDepartmentHour(String depName);
	void changedRoleHour(String depName, int roleId);
	void changedPercentageEmployeeData(String depName, int roleId);
	/**********************************************/
	
	void modelExecpetion(String excpetionMsg);
	void modelMsg(String msg);
}
