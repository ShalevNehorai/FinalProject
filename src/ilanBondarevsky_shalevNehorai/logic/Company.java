package ilanBondarevsky_shalevNehorai.logic;

import java.util.ArrayList;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;

import ilanBondarevsky_shalevNehorai.listeners.CompanyListenable;

public class Company {
	public static final int WORK_HOURS_IN_MONTH = 160;
	public static final int WORK_HOURS_IN_DAY = 8;
	public static final int DEFAULT_START_WORK_DAY = 8;
	
	private String name;
	
	private ArrayList<Department> departments;
	
	private ArrayList<CompanyListenable> allListeners;
	
	public Company(String name) throws IllegalArgumentException{
		if(name.isBlank()) {
			throw new IllegalArgumentException("Company name cant be blank");
		}
		
		this.name = name;
		departments = new ArrayList<Department>();
		allListeners = new ArrayList<CompanyListenable>();
	}
	
	private Department getDepartmentByName(String departmentName) {
		if(departmentName != null){
			for (Department department : departments) {
				if(departmentName.equalsIgnoreCase(department.getName())){
					return department;
				}
			}
		}
		return null;
	}
	
	public void addDepartment(String name, boolean isSync, boolean isChangeable){
		if(getDepartmentByName(name) == null) {
			try {
				departments.add(new Department(name, isSync, isChangeable));
				fireAddedDepartment(name);
			} catch (IllegalArgumentException e) {
				fireExcpetion(e);
			}
		}
		else {
			fireExcpetion(new InstanceAlreadyExistsException("Department already exist"));
		}
	}
		
	public String getName(){
		return name;
	}
	
	public double getCompanyProfit(){
		double profit = 0;
		for (Department department : departments) {
			profit += department.profit();	
		}
		return profit;
	}
	
	public void addRoleToDepartment(String departmentName, String roleName, boolean isRoleChangeable){
		Department department = getDepartmentByName(departmentName);
		if(department != null){
			try{
				int roleId = department.addRole(roleName, isRoleChangeable);
				fireAddedRole(departmentName, roleId);
				System.out.println("added role" + roleId + " " + getRoleName(departmentName, roleId) + " under " + departmentName);
			}
			catch(IllegalArgumentException e){
				fireExcpetion(e);
			}
		}
		else{
			fireExcpetion(new InstanceNotFoundException("Department not found"));
		}
	}
	
	public void addEmployeeToRole(String departmentName, int roleID, String employeeName, EmployeeType emplyeeType, int preferWorkingTime, boolean preferWoringFromHome, int salary, double monthlyPercentage, int monthlySales){
		Department department = getDepartmentByName(departmentName);
		
		if(department != null) {
			try{
				department.setRoleEmployee(roleID, emplyeeType, employeeName, DEFAULT_START_WORK_DAY, false, preferWorkingTime, preferWoringFromHome, salary, monthlyPercentage, monthlySales);			
				fireAddedEmployee(departmentName, roleID);
			}
			catch(IllegalArgumentException e){
				fireExcpetion(e);
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Department not found"));
		}
	}
	
	public void changeDepartmentWorkHours(String departmentName, int startTime, boolean workingFromHome){
		Department department = getDepartmentByName(departmentName);
		if(department != null){
			try{
				department.changeWorkingHours(startTime, workingFromHome);
				fireChangedDepartmentHour(departmentName);
			}
			catch(IllegalArgumentException e){
				fireExcpetion(e);
			}
		}
		else{
			fireExcpetion(new InstanceNotFoundException("Department doesnt exist"));
		}
	}	
	
	public void changeRoleWorkHours(String departmentName, int roleId, int startTime, boolean workingFromHome) {
		Department department = getDepartmentByName(departmentName);
		if(department != null){
			try{
				department.changeWorkingHoursForRole(roleId, startTime, workingFromHome);
				fireChangedRoleHour(roleId, department.getName());
			}
			catch(IllegalArgumentException e){
				fireExcpetion(e);
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
			}	
		}	else{
			fireExcpetion(new InstanceNotFoundException("Department doesnt exist"));
		}
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("The ").append(name).append(" company: ").append("\n");
		for (Department department : departments) {
			output.append(department.toString()).append("\n");
		}
		return output.toString();
	}
	
	public void registerListener(CompanyListenable l) {
		allListeners.add(l);
	}
	
	public ArrayList<String> getDepartmentsNames(){
		ArrayList<String> output = new ArrayList<String>();
		for (Department dep : departments) {
			output.add(dep.getName());
		}
		return output;
	}
	
	public ArrayList<Integer> getRolesInDepartment(String departmentName){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			return dep.getRolesIDList();
		} else{
			fireExcpetion(new InstanceNotFoundException("Department doesnt exist"));
			return null;
		}	
	}
	
	public boolean isDepartmentSync(String departmentName){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			return dep.isWorkingSync();		
		} else{
			fireExcpetion(new InstanceNotFoundException("Department doesnt exist"));
			return false;
		}
	}
	
	public boolean isDepartmentChangeable(String departmentName){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			return dep.isWorkChangeable();		
		} else{
			fireExcpetion(new InstanceNotFoundException("Department doesnt exist"));
			return false;
		}
	}
	
	public boolean isRoleSync(String departmentName, int roleId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			try{
				return dep.isRoleSync(roleId);	
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
				return false;
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Department doesnt exist"));
			return false;
		}
	}
	
	public boolean isRoleChangeable(String departmentName, int roleId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			try{
				return dep.isRoleChangeable(roleId);	
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
				return false;
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Department doesnt exist"));
			return false;
		}
	}
	
	public double getDepartmentProfit(String departmentName){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			return dep.profit();	
		} else{
			fireExcpetion(new InstanceNotFoundException("Department doesnt exist"));
			return 0;
		}
	}
	
	public double getEmployeeProfit(String departmentName, int roleId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			try{
				return dep.getRoleProfit(roleId);	
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
				return 0;
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Department doesnt exist"));
			return 0;
		}
	}
	
	public String getRoleName(String departmentName, int roleId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			try{
				return dep.getRoleName(roleId);	
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
				return null;
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Department doesnt exist"));
			return null;
		}
	}
	
	public String getEmployeeNameInRole(String departmentName, int roleId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			try{
				return dep.getEmployeeNameInRole(roleId);
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
				return null;
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Department doesnt exist"));
			return null;
		}
	}
	
	public String getRoleData(String departmentName, int roleId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			try{
				return dep.getRoleData(roleId);
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
				return null;
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Department doesnt exist"));
			return null;
		}
	}
	
	public EmployeeType getEmployeeType(String departmentName, int roleId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			try{
				return dep.getEmployeeType(roleId);
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
				return null;
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Department doesnt exist"));
			return null;
		}
	}
	
	public void changeEmployeePercentage(String departmentName, int roleId, double percentage){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			try{
				if(dep.getEmployeeType(roleId) == EmployeeType.PERCENTAGE_EMPLOYEE){
					dep.changeEmployeeSalesPercentage(roleId, percentage);
				} else{
					fireExcpetion(new IllegalArgumentException("only employee with bonuses get percentage of sales"));
				}
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
			}
			catch(IllegalArgumentException e){
				fireExcpetion(e);
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Department doesnt exist"));
		}
	}
	
	public void changeMonthlySales(int sales, int roleId){
		Department dep = getDepartmentByName(searchRoleInAllDepartments(roleId));
		if(dep != null){
			try{
				dep.changeEmployeeMonthlySales(sales, roleId);
				fireChangedMonthlySales(sales, roleId, dep.getName());
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
			}
			catch(IllegalArgumentException e){
				fireExcpetion(e);
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("The role doesn't exist in the company"));
		}
		
	}
	
	private String searchRoleInAllDepartments(int roleId){
		for (Department department : departments) {
			try{
				department.getRoleName(roleId);
				return department.getName();
			}
			catch(InstanceNotFoundException e){
				
			}
			catch(IllegalArgumentException e){
				
			}
		}
		return null;
	}
	
	public void fireExcpetion(Exception e){
		for (CompanyListenable listener : allListeners) {
			listener.modelExecpetion(e.getMessage());
		}
	}
	
	public void fireMessage(String msg){
		for (CompanyListenable listener : allListeners) {
			listener.modelMsg(msg);
		}
	}
	
	public void fireAddedDepartment(String name){
		for (CompanyListenable listener : allListeners) {
			listener.updateDepartment(name);
		}
	}
	
	public void fireAddedRole(String departmentName, int roleId){
		for (CompanyListenable listener : allListeners) {
			listener.updateRole(departmentName, roleId);
		}
	}
	
	public void fireAddedEmployee(String departmentName, int roleId){
		for (CompanyListenable listener : allListeners) {
			listener.updateEmployee(departmentName, roleId);
		}
	}
	
	public void fireChangedDepartmentHour(String departmentName){
		for (CompanyListenable listener : allListeners) {
			listener.updateChangeDepartmentHour(departmentName);
		}
	}
	
	public void fireChangedRoleHour(int roleId, String departmentName){
		for (CompanyListenable listener : allListeners) {
			listener.updateChangeRoleHour(roleId, departmentName);
		}
	}
	
	public void fireChangedMonthlySales(int sales, int roleId, String departmentName){
		for (CompanyListenable listener : allListeners) {
			listener.updateChangeMonthlySales(sales, roleId, departmentName);
		}
	}
	
}
