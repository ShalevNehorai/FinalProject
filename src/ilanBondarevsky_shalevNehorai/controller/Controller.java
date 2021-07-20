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
	public String viewAskCompanyName() {
		return theModel.getName();
	}

	@Override
	public double viewAskCompanyProfit() {
		return theModel.getCompanyProfit();
	}

	@Override
	public ArrayList<String> viewAskDeparmentsNames() {
		return theModel.getDepartmentsNames();
	}

	@Override
	public double viewAskDepartmentProfit(String deparmentName) {
		return theModel.getDepartmentProfit(deparmentName);
	}

	@Override
	public boolean viewAskIsDepartmentSync(String deparmentName) {
		return theModel.isDepartmentSync(deparmentName);
	}

	@Override
	public boolean viewAskIsDepartmentChangeable(String departmentName) {
		return theModel.isDepartmentChangeable(departmentName);
	}

	@Override
	public ArrayList<Integer> viewAskRolesIdInDepartment(String deparmentName) {
		return theModel.getRolesInDepartment(deparmentName);
	}

	@Override
	public String viewAskRoleName(String deparmentName, int roleId) {
		return theModel.getRoleName(deparmentName, roleId);
	}

	@Override
	public String viewAskRoleEmployeeName(String deparmentName, int roleId) {
		return theModel.getEmployeeNameInRole(deparmentName, roleId);
	}

	@Override
	public double viewAskEmployeeProfit(String deparmentName, int roleId) {
		return theModel.getEmployeeProfit(deparmentName, roleId);
	}

	@Override
	public boolean viewAskIsRoleSync(String deparmentName, int roleId) {
		return theModel.isRoleSync(deparmentName, roleId);
	}

	@Override
	public boolean viewAskIsRoleChangeable(String departmentName, int roleId) {
		return theModel.isRoleChangeable(departmentName, roleId);
	}

	@Override
	public String viewAskRoleData(String deparmentName, int roleId) {
		return theModel.getRoleData(deparmentName, roleId);
	}
	
	@Override
	public EmployeeType viewAskEmployeeType(String deparmentName, int roleId) {
		return theModel.getEmployeeType(deparmentName, roleId);
	}
	
	@Override
	public int viewAskEmployeeMonthlySales(String depName, int roleId) {
		return theModel.getEmployeeMonthlySales(depName, roleId);
	}
	
	@Override
	public double viewAskEmployeePercentage(String depName, int roleId) {
		return theModel.getEmployeePercentage(depName, roleId);
	}
	
	@Override
	public void viewAddDepartment(String name, boolean isSync, boolean isChangeable) {
		theModel.addDepartment(name, isSync, isChangeable);
	}

	@Override
	public void viewAddRole(String deparmentName, String roleName, boolean isRoleChangeable) {
		theModel.addRoleToDepartment(deparmentName, roleName, isRoleChangeable);
	}

	@Override
	public void viewAddEpmloyee(String deparmentName, int roleId, String employeeName, EmployeeType type,
			int preferWorkingTime, boolean prefWorkingHome, int salary, double monthlyPersentage, int monthlySales) {
		theModel.addEmployeeToRole(deparmentName, roleId, employeeName, type, preferWorkingTime, prefWorkingHome, salary, monthlyPersentage, monthlySales);
	}
	
	@Override
	public void viewChangeDepartmentHours(String depName, boolean workHome, int startHour) {
		theModel.changeDepartmentWorkHours(depName, startHour, workHome);
	}
	
	@Override
	public void viewChangeRoleHour(String depName, int roleId, boolean workFromHome, int startHour) {
		theModel.changeRoleWorkHours(depName, roleId, startHour, workFromHome);	
	}

	@Override
	public void viewChangeEmployeePersentageData(String deparmentName, int roleId, double percentage, int sales) {
		theModel.changePercentageEmployeeData(deparmentName, roleId, percentage, sales);
	}
	
	@Override
	public void viewAskSave() {
		theModel.saveAsBinaryFile();
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
	public void modelAddedDepartment(String depName) {
		theView.addedDeparment(depName);
	}
	
	@Override
	public void modelAddedRole(String depName, int roleId) {
		theView.addedRoleToDeparment(depName, roleId);
	}
	
	@Override
	public void modelAddedEmployee(String depName, int roleId) {
		theView.addedEmployee(depName, roleId);
	}
	
	@Override
	public void modelUpdateProfit() {
		theView.updateProfit();
	}
}
