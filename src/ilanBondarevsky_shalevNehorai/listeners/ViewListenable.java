package ilanBondarevsky_shalevNehorai.listeners;

import java.util.ArrayList;

import ilanBondarevsky_shalevNehorai.logic.EmployeeType;

public interface ViewListenable {
	String askCompanyName();
	double askCompanyProfit();
	
	ArrayList<String> askDeparmentsNames();
	double askDepartmentProfit(String deparmentName);
	boolean isDepartmentSync(String deparmentName);
	boolean isDepartmentChangeable(String departmentName);
	
	ArrayList<Integer> askRoleIdInDepartment(String deparmentName);
	String askRoleName(String deparmentName, int roleId);
	String askRoleEmployeeName(String deparmentName, int roleId);
	double askEmployeeProfit(String deparmentName, int roleId);
	boolean isRoleSync(String deparmentName, int roleId);
	boolean isRoleChangeable(String departmentName, int roleId);
	String askRoleData(String deparmentName, int roleId);
	EmployeeType askEmployeeType(String deparmentName, int roleId);
	double askEmployeePercentage(String depName, int roleId);
	int askEmployeeMonthlySales(String depName, int roleId);
	
	void addDepartment(String name, boolean isSync, boolean isChangeable);
	void addRole(String deparmentName, String roleName, boolean isRoleChangeable);
	void addEpmloyee(String deparmentName, int roleId, String employeeName, EmployeeType type, int preferWorkingRime, boolean prefWorkingHome, int salary, double monthlyPersentage, int monthlySales);
	
	void changeEmployeePersentageData(String deparmentName, int roleId, String employeeName, double percentage, int sales);
	void changeDepartmentHours(String depName, boolean workHome, int startHour);
	void changeRoleHour(String depName, int roleId, boolean workFromHome, int startHour);
	
	
}
