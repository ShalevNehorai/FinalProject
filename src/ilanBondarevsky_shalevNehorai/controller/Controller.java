package ilanBondarevsky_shalevNehorai.controller;

import java.util.ArrayList;

import ilanBondarevsky_shalevNehorai.application.CompanyViewable;
import ilanBondarevsky_shalevNehorai.listeners.CompanyListenable;
import ilanBondarevsky_shalevNehorai.listeners.ViewListenable;
import ilanBondarevsky_shalevNehorai.logic.Company;
import ilanBondarevsky_shalevNehorai.logic.EmployeeType;

public class Controller implements CompanyListenable, ViewListenable {

	private Company theModel;
	private CompanyViewable theView;
	
	public Controller(Company m, CompanyViewable v) {
		theModel = m;
		theView = v;
		
		theModel.registerListener(this);
		theView.registerListener(this);
	}
	
	@Override
	public String askCompanyName() {
		return theModel.getName();
	}

	@Override
	public double askCompanyProfit() {
		return theModel.getCompanyProfit();
	}

	@Override
	public ArrayList<String> askDeparmentsNames() {
		return theModel.getDepartmentsNames();
	}

	@Override
	public double askDepartmentProfit(String deparmentName) {
		return theModel.getDepartmentProfit(deparmentName);
	}

	@Override
	public boolean isDepartmentSync(String deparmentName) {
		return theModel.isDepartmentSync(deparmentName);
	}

	@Override
	public boolean isDepartmentChangeable(String departmentName) {
		return theModel.isDepartmentChangeable(departmentName);
	}

	@Override
	public ArrayList<Integer> askRoleIdInDepartment(String deparmentName) {
		return theModel.getRolesInDepartment(deparmentName);
	}

	@Override
	public String askRoleName(String deparmentName, int roleId) {
		return theModel.getRoleName(deparmentName, roleId);
	}

	@Override
	public String askRoleEmployeeName(String deparmentName, int roleId) {
		return theModel.getEmployeeNameInRole(deparmentName, roleId);
	}

	@Override
	public double askEmployeeProfit(String deparmentName, int roleId) {
		return theModel.getEmployeeProfit(deparmentName, roleId);
	}

	@Override
	public boolean isRoleSync(String deparmentName, int roleId) {
		return theModel.isRoleSync(deparmentName, roleId);
	}

	@Override
	public boolean isRoleChangeable(String departmentName, int roleId) {
		return theModel.isRoleChangeable(departmentName, roleId);
	}

	@Override
	public String askRoleData(String deparmentName, int roleId) {
		return theModel.getRoleData(deparmentName, roleId);
	}

	@Override
	public void addDepartment(String name, boolean isSync, boolean isChangeable) {
		theModel.addDepartment(name, isSync, isChangeable);
	}

	@Override
	public void addRole(String deparmentName, String roleName, boolean isRoleChangeable) {
		theModel.addRoleToDepartment(deparmentName, roleName, isRoleChangeable);
	}

	@Override
	public void addEpmloyee(String deparmentName, int roleId, String employeeName, EmployeeType type,
			int preferWorkingTime, boolean prefWorkingHome, int salary, double monthlyPersentage, int monthlySales) {
		theModel.addEmployeeToRole(deparmentName, roleId, employeeName, type, preferWorkingTime, prefWorkingHome, salary, monthlyPersentage, monthlySales);

	}

	@Override
	public void changeEmployeePersentageData(String deparmentName, int roleId, String employeeName, double percentage, int sales) {
		theModel.changePercentageEmployeeData(employeeName, roleId, percentage, sales);
	}

	@Override
	public void updateDepartment(String name) {
		theView.addedDeparment(name);
	}

	@Override
	public void updateRole(String departmentName, int roleId) {
		theView.addedRoleToDeparment(departmentName, roleId);
		
	}

	@Override
	public void updateEmployee(String departmentName, int roleId) {
		theView.addedEmployee(departmentName, roleId);
		
	}

	@Override
	public void modelExecpetion(String excpetionMsg) {
		theView.showError(excpetionMsg);
	}

	@Override
	public void modelMsg(String msg) {
		theView.showMsg(msg);
	}
	@Override
	public void updateChangeDepartmentHour(String departmentName) {
		updateDepartmentProfit(departmentName);
		updateCompanyProfit();
	}
	
	@Override
	public void updateChangeRoleHour(int roleId, String departmentName) {
		updateRoleProfit(departmentName, roleId);
		updateDepartmentProfit(departmentName);
		updateCompanyProfit();
	}
	
	@Override
	public void updateCompanyProfit() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateDepartmentProfit(String departmentName) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateRoleProfit(String departmentName, int roleId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EmployeeType askEmployeeType(String deparmentName, int roleId) {
		return theModel.getEmployeeType(deparmentName, roleId);
	}
	@Override
	public void changeDepartmentHours(String depName, boolean workHome, int startHour) {
		theModel.changeDepartmentWorkHours(depName, startHour, workHome);
	}
	@Override
	public void changeRoleHour(String depName, int roleId, boolean workFromHome, int startHour) {
		theModel.changeRoleWorkHours(depName, roleId, startHour, workFromHome);
		
	}
	
	@Override
	public int askEmployeeMonthlySales(String depName, int roleId) {
		int sales = theModel.getEmployeeMonthlySales(depName, roleId);
		if(sales != -1){
			return sales;
		}
		return 0;
	}
	
	@Override
	public double askEmployeePercentage(String depName, int roleId) {
		double percentage = theModel.getEmployeePercentage(depName, roleId);
		if(percentage != -1){
			return percentage;
		}
		return 0;
	}
	
	@Override
	public void addedDepartment(String depName) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addedRole(String depName, int roleId) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addedEmployee(String depName, int roleId) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void changedDepartmentHour(String depName) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void changedRoleHour(String depName, int roleId) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void changedPercentageEmployeeData(String depName, int roleId) {
		// TODO Auto-generated method stub
		
	}
	
}
