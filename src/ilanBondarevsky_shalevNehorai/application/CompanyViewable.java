package ilanBondarevsky_shalevNehorai.application;

import java.util.ArrayList;

import ilanBondarevsky_shalevNehorai.listeners.ViewListenable;
import ilanBondarevsky_shalevNehorai.logic.EmployeeType;

public interface CompanyViewable {
	void registerListener(ViewListenable l);
	
	void initViewData();
	void updateProfit();
	
	void askCompanyName();
	void askCompanyProfit();
	
	void askDeparmnetsNames();
	boolean isDeparmentSync(String departmentName);
	boolean isDepatmentChangeable(String departmentName); 
	double askDeparmentProfit(String deparmentName);
	
	ArrayList<Integer> askRolesInDeparment(String deparmentName);
	String askRoleName(String departmentName, int id);
	double askRoleProfit(String departmentName, int id);
	boolean isRoleSync(String deparmentName, int roleId);
	boolean isRoleChangeable(String departmentName, int roleId);
	String askRoleData(String deparmentName, int roleId);
	
	ArrayList<Integer> askEmployeesInRole(String deparmentName, int roleId);
	String askEmployeeName(String departmentName, int roleId, int employeeId);
	double askEmployeeProfit(String departmentName, int roleId, int employeeId);
	String askEmployeeData(String departmentName, int roleId, int employeeId);
	EmployeeType askEmployeeType(String deparmentName, int roleId, int employeeId);
	double askEmployeePercentage(String depName, int roleId, int employeeId);
	int askEmployeeMonthlySales(String depName, int roleId, int employeeId);
	
	void addDepartment(String name, boolean isSync, boolean isChangeable);
	void addRole(String deparmentName, String roleName, boolean isRoleChangeable);
	void addEmployee(String deparmentName, int roleId, String employeeName, EmployeeType type, int preferWorkingRime, boolean prefWorkingHome, int salary, double monthlyPersentage, int monthlySales);
	
	void changeEmployeePercentageData(String deparmentName, int roleId, int employeeId, double percentage, int monthlySales);
	void changeRoleHours(String depName, int roleId, boolean workFromHome, int startHour);
	void changeDepartmentHours(String depName, boolean workHome, int startHour);
	
	void addedDeparment(String deparmentName);
	void addedRoleToDeparment(String deparmentName, int roleId);
	void addedEmployee(String deparmentName, int roleId, int employeeId);
	
	void showMsg(String msg);
	void showError(String errorMsg);
	
	void saveCompanyData();
}
