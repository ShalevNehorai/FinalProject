package ilanBondarevsky_shalevNehorai.application;

import java.util.ArrayList;

import ilanBondarevsky_shalevNehorai.listeners.ViewListenable;
import ilanBondarevsky_shalevNehorai.logic.EmployeeType;

public interface CompanyViewable {
	void registerListener(ViewListenable l);
	
	void askCompanyName();
	void askCompanyProfit();
	
	void askDeparmnetNames();
	boolean isDeparmentSync(String departmentName);
	boolean isDepatmentChangeable(String departmentName); 
	double askDeparmentProfit(String deparmentName);
	
	ArrayList<Integer> askRolesInDeparment(String deparmentName);
	String askRoleName(String departmentName, int id);
	String askRoleEmployeeName(String deparmentName, int id);
	double askEmployeeProfit(String deparmentName, int roleId);
	boolean isRoleSync(String deparmentName, int roleId);
	boolean isRoleChangeable(String departmentName, int roleId);
	String askRoleData(String deparmentName, int roleId);
	
	void addDepartment(String name, boolean isSync, boolean isChangeable);
	void addRole(String deparmentName, String roleName, boolean isRoleChangeable);
	void addEpmloyee(String deparmentName, int roleId, String employeeName, EmployeeType type, int preferWorkingRime, boolean prefWorkingHome, int salary, double monthlyPersentage, int monthlySales);
	
	void changeEmployeePersentage(String deparmentName, int roleId, String employeeName, double percentage, double monthlySales);
	
	void addedDeparment(String deparmentName);
	void addedRoleToDeparment(String deparmentName, int roleId);
	void addedEmployee(String deparmentName, int roleId);
	
	void showMsg(String msg);
	void showError(String errorMsg);
}