package ilanBondarevsky_shalevNehorai.logic;

import java.util.ArrayList;

public class Company {
	public static final int WORK_HOURS_IN_MONTH = 160;
	public static final int WORK_HOURS_IN_DAY = 8;
	public static final int DEFAULT_START_WORK_DAY = 8;
	
	private String name;
	
	private ArrayList<Department> departments;
	
	public Company(String name) throws IllegalArgumentException{
		if(name.isBlank()) {
			throw new IllegalArgumentException("Company name cant be blank");
		}
		
		this.name = name;
		departments = new ArrayList<Department>();
	}
	
	private Department getDepartmentByName(String departmentName) {
		for (Department department : departments) {
			if(departmentName.equalsIgnoreCase(department.getName())){
				return department;
			}
		}
		return null;
	}
	
	public void addDepartment(String name, boolean isSync, boolean isChangeable){
		if(getDepartmentByName(name) == null) {
			try {
				departments.add(new Department(name, isSync, isChangeable));
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				//TODO fire error msg
			}
		}
		else {
			//TODO fire department allready exists
			System.out.println(name + "already exists");
		}
	}
	
	public String getName(){
		return name;
	}
	
	public double getCompanyProfit(){
		double profit = 0;
		for (Department department : departments) {
			profit += department.addedMoney();	
		}
		return profit;
	}
	
	public void addRoleToDepartment(String departmentName, String roleName, boolean isRoleChangeable){
		Department department = getDepartmentByName(departmentName);
		if(department != null){
			try{
				department.addRole(roleName, isRoleChangeable);
			}
			catch(IllegalArgumentException e){
				System.out.println(e.getMessage());
				//TODO fire error msg
			}
		}
		else{
			//TODO fire department not found
		}
	}
	
	public void addEmployeeToRole(String departmentName, int roleID, String employeeName, EmployeeType emplyeeType, int preferWorkingTime, boolean preferWoringFromHome, int salary, double monthlyPercentage, int monthlySales){
		Department department = getDepartmentByName(departmentName);
		
		if(department != null) {
			try{
				department.setRoleEmployee(roleID, emplyeeType, employeeName, DEFAULT_START_WORK_DAY, false, preferWorkingTime, preferWoringFromHome, salary, monthlyPercentage, monthlySales);			
			}
			catch(IllegalArgumentException e){
				System.out.println(e.getMessage());
				//TODO fire error msg
			}
		}

		//TODO fire role not found
	}
	
	public void changeDepartmentWorkHours(String departmentName, int startTime, boolean workingFromHome){
		Department department = getDepartmentByName(departmentName);
		if(department != null){
			department.changeWorkingHours(startTime, workingFromHome);
		}
		else{
			//TODO Fire Exceptions: department exist
		}	
	}	
	
	public void changeRoleWorkHours(String departmentName, int roleId, int startTime, boolean workingFromHome) {
		Department department = getDepartmentByName(departmentName);
		if(department != null){
			department.changeWorkingHoursForRole(roleId, startTime, workingFromHome);
		}
		else{
			//TODO Fire Exceptions: department dont exist
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
}
