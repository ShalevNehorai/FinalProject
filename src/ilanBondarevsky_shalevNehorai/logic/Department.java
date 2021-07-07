package ilanBondarevsky_shalevNehorai.logic;

import java.util.ArrayList;

public class Department implements CalculateAddedValueable, WorkChangeable, WorkingSync {
	
	private String name;
	private ArrayList<Role> roles;

	private boolean isSync;
	private boolean isChangeable;
	
	public Department(String name, boolean isSync, boolean isChangable) throws IllegalArgumentException{
		if(name.isBlank()) {
			throw new IllegalArgumentException("Department name cant be blank");
		}
		
		this.name = name;
		this.isSync = isSync;
		this.isChangeable = isChangable;
		
		roles = new ArrayList<Role>();
	}
	
	public String getName(){
		return name;
	}
	
	private Role getRoleById(int roleId) {
		for (Role role : roles) {
			if(role.getId() == roleId) {
				return role;
			}
		}
		return null;
	}
	
	public void addRole(String roleName, boolean isRoleChangeable)  throws IllegalArgumentException {
		addRole(roleName, isRoleChangeable, null, "", 0, false, 0, false, 0, 0, 0);
	}
	
	public void addRole(String roleName, boolean isRoleChangeable, EmployeeType type, String employeeName, int startTime, boolean isHomeWorking, int prefStartTime, boolean prefWorkHome, int salary, double monthlyPercentage, int monthlySales) throws IllegalArgumentException {
		if(!isChangeable)
			isRoleChangeable = false;
		
		roles.add(new Role(roleName, isSync, isRoleChangeable, type, employeeName, startTime, isHomeWorking, prefStartTime, prefWorkHome, salary, monthlyPercentage, monthlySales));
		//TODO fire add role
	}
	
	public void setRoleEmployee(int roleId, EmployeeType type, String name, int startTime, boolean isHomeWorking, int prefStartTime, boolean prefWorkHome, int salary, double mothlyPercentage, int monthlySales) throws IllegalArgumentException {
		Role role = getRoleById(roleId);
		
		if(role != null) {
			role.setEmployee(type, name, startTime, isHomeWorking, prefStartTime, prefWorkHome, salary, mothlyPercentage, monthlySales);
			//TODO fire add employee
		}
		else{
			//TODO fire role not found
		}
	}
	
	@Override
	public void changeWorkingHours(int startTime, boolean homeWork) {
		if(isChangeable){
			for (Role role : roles) {
				role.changeWorkingHours(startTime, homeWork);
			}
		}
	}
	
	public void changeWorkingHoursForRole(int roleId, int startTime, boolean homeWork) {
		if(isSync) {
			System.out.println("cant change hours for this role " + roleId + " seperately");
			return;
		}
		
		Role role = getRoleById(roleId);
		if(role != null) {
			role.changeWorkingHours(startTime, homeWork);
		}
	}
	
	@Override
	public boolean isWorkingSync() {
		return isSync;
	}

	@Override
	public boolean isWorkChangeable() {
		return isChangeable;
	}

	@Override
	public double addedMoney() {
		double addedMoney = 0;
		for (Role role : roles) {
			addedMoney += role.addedMoney();
		}
		return addedMoney;
	}
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("Department ").append(name).append("\n");
		if(roles.isEmpty()){
			output.append("\t").append("There are no roles in this department.");
		}
		else{
			for (Role role : roles) {
				output.append("\t").append(role.toString()).append("\n");
			}
		}
		
		
		return output.toString();
	}
}
