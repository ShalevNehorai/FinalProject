package ilanBondarevsky_shalevNehorai.listeners;

import java.util.ArrayList;

import ilanBondarevsky_shalevNehorai.logic.EmployeeType;

public interface ViewListenable {
	String viewAskCompanyName();
	double viewAskCompanyProfit();
	
	ArrayList<String> viewAskDeparmentsNames();
	double viewAskDepartmentProfit(String deparmentName);
	boolean viewAskIsDepartmentSync(String deparmentName);
	boolean viewAskIsDepartmentChangeable(String departmentName);
	
	ArrayList<Integer> viewAskRoleIdInDepartment(String deparmentName);
	String viewAskRoleName(String deparmentName, int roleId);
	String viewAskRoleEmployeeName(String deparmentName, int roleId);
	double viewAskEmployeeProfit(String deparmentName, int roleId);
	boolean viewAskIsRoleSync(String deparmentName, int roleId);
	boolean viewAskIsRoleChangeable(String departmentName, int roleId);
	String viewAskRoleData(String deparmentName, int roleId);
	EmployeeType viewAskEmployeeType(String deparmentName, int roleId);
	double viewAskEmployeePercentage(String depName, int roleId);
	int viewAskEmployeeMonthlySales(String depName, int roleId);
	
	void viewAddDepartment(String name, boolean isSync, boolean isChangeable);
	void viewAddRole(String deparmentName, String roleName, boolean isRoleChangeable);
	void viewAddEpmloyee(String deparmentName, int roleId, String employeeName, EmployeeType type, int preferWorkingRime, boolean prefWorkingHome, int salary, double monthlyPersentage, int monthlySales);
	
	void viewChangeEmployeePersentageData(String deparmentName, int roleId, double percentage, int sales);
	void viewChangeDepartmentHours(String depName, boolean workHome, int startHour);
	void viewChangeRoleHour(String depName, int roleId, boolean workFromHome, int startHour);
	
	
}
