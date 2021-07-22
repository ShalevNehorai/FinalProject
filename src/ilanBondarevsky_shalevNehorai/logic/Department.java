package ilanBondarevsky_shalevNehorai.logic;

import java.io.Serializable;
import java.util.ArrayList;

import javax.management.InstanceNotFoundException;

public class Department implements CalculateAddedValueable, WorkChangeable, WorkingSync, Serializable {
	
	private String name;
	private ArrayList<Role> roles;

	private boolean isSync;
	private boolean isChangeable;
	
	private int syncStartTime;
	private boolean syncWorkFromHome;
	
	public Department(String name, boolean isSync, boolean isChangeable) throws IllegalArgumentException{
		if(name.isBlank()) {
			throw new IllegalArgumentException("Department name cant be blank");
		}
		
		this.name = name;
		this.isSync = isSync;
		this.isChangeable = isChangeable;
		
		syncStartTime = Company.DEFAULT_START_WORK_DAY;
		syncWorkFromHome = false;
		
		roles = new ArrayList<Role>();
	}
	
	public String getName(){
		return name;
	}
	
	public int getSyncedStartHour(){
		return syncStartTime;
	}
	
	public boolean getSyncWorkFromHome(){
		return syncWorkFromHome;
	}
	
	private Role getRoleById(int roleId) {
		for (Role role : roles) {
			if(role.getId() == roleId) {
				return role;
			}
		}
		return null;
	}
	public int addRole(String roleName, boolean isRoleChangeable, int startTime, boolean workFromHome){
		if(isSync){
			isRoleChangeable = true;
		}
		else if(!isChangeable) {
			isRoleChangeable = false;
		}
		
		roles.add(new Role(roleName, isSync, isRoleChangeable, startTime, workFromHome));
		
		return roles.get(roles.size() - 1).getId();
	}
	
	public int addRole(String roleName, boolean isRoleChangeable) throws IllegalArgumentException {
		return addRole(roleName, isRoleChangeable, syncStartTime, syncWorkFromHome);
	}
	
	public int addEmployeeToRole(int roleId, EmployeeType type, String name, int startTime, boolean isHomeWorking, int prefStartTime, boolean prefWorkHome, int salary, double mothlyPercentage, int monthlySales) throws IllegalArgumentException, InstanceNotFoundException {
		Role role = getRoleById(roleId);
		
		if(role != null) {
			if(isSync) {
				startTime = this.syncStartTime;
				isHomeWorking = this.syncWorkFromHome;
			}
			return role.addEmployee(type, name, startTime, isHomeWorking, prefStartTime, prefWorkHome, salary, mothlyPercentage, monthlySales);
		}
		else{
			throw new InstanceNotFoundException("Role not found");
		}
	}
	
	@Override
	public void changeWorkingHours(int startTime, boolean homeWork) throws IllegalArgumentException{
		StringBuffer errorMsg = new StringBuffer();
		
		if(isChangeable){
			syncStartTime = startTime;
			syncWorkFromHome = homeWork;
			for (Role role : roles) {
				try{
					role.changeWorkingHours(startTime, homeWork);
				}
				catch(IllegalArgumentException e){
					errorMsg.append(e.getMessage() + "\n");
				}
			}
			
			if(!errorMsg.isEmpty()){
				throw new IllegalArgumentException(errorMsg.toString());
			}
		} else {
			throw new IllegalArgumentException("Can't change the department's roles hours");
		}
	}
	
	public void changeWorkingHoursForRole(int roleId, int startTime, boolean homeWork) throws IllegalArgumentException, InstanceNotFoundException{
		if(isSync) {
			System.out.println("cant change hours for this role " + roleId + " seperately");
			throw new IllegalArgumentException("Can't change the role's hours");
		}
		
		Role role = getRoleById(roleId);
		if(role != null) {
			role.changeWorkingHours(startTime, homeWork);
		} else{
			throw new InstanceNotFoundException("Role does not exist");
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
	public double profit() {
		double addedMoney = 0;
		for (Role role : roles) {
			addedMoney += role.profit();
		}
		return addedMoney;
	}
		
	public ArrayList<Integer> getRolesIdList(){
		ArrayList<Integer> rolesId = new ArrayList<Integer>();
		for (Role role : roles) {
			rolesId.add(role.getId());
		}
		return rolesId;
	}
	
	public boolean isRoleSync(int roleId) throws InstanceNotFoundException{
		Role role = getRoleById(roleId);
		if(role != null){
			return role.isWorkingSync();
		} else{
			throw new InstanceNotFoundException("Role doesn't exist");
		}
	}
	
	public boolean isRoleChangeable(int roleId) throws InstanceNotFoundException{
		Role role = getRoleById(roleId);
		if(role != null){
			return role.isWorkChangeable();
		} else{
			throw new InstanceNotFoundException("Role doesn't exist");
		}
	}
	
	public double getRoleProfit(int roleId) throws InstanceNotFoundException{
		Role role = getRoleById(roleId);
		if(role != null){
			return role.profit();
		} else{
			throw new InstanceNotFoundException("Role doesn't exist");
		}
	}
	
	public String getRoleName(int roleId) throws InstanceNotFoundException{
		Role role = getRoleById(roleId);
		if(role != null){
			return role.getName();
		} else{
			throw new InstanceNotFoundException("Role doesn't exist");
		}
	}
	
	public String getRoleData(int roleId) throws InstanceNotFoundException{
		Role role = getRoleById(roleId);
		if(role != null){
			return role.toString();
		} else{
			throw new InstanceNotFoundException("Role doesn't exist");
		}
	}
	
	public String getEmployeeNameInRole(int roleId, int employeeId) throws InstanceNotFoundException {
		Role role = getRoleById(roleId);
		if(role != null){
			return role.getEmployeeName(employeeId);
		}
		else {
			throw new InstanceNotFoundException("Role not found under deparment " + name);
		}
	}
	
	public EmployeeType getEmployeeTypeInRole(int roleId, int employeeId) throws InstanceNotFoundException{
		Role role = getRoleById(roleId);
		if(role != null){
			return role.getEmployeeType(employeeId);
		} else{
			throw new InstanceNotFoundException("Role doesn't exist");
		}
	}
	
	public double getEmployeeProfitInRole(int roleId, int employeeId) throws InstanceNotFoundException{
		Role role = getRoleById(roleId);
		if(role != null){
			return role.getEmployeeProfit(employeeId);
		}
		else {
			throw new InstanceNotFoundException("Role doesn't exist");
		}
	}
	
	public double getEmployeePercentageInRole(int roleId, int employeeId) throws IllegalArgumentException, InstanceNotFoundException{
		Role role = getRoleById(roleId);
		return role.getEmployeePercentage(employeeId);
	}
	
	public int getEmployeeMonthlySalesInRole(int roleId, int employeeId) throws IllegalArgumentException, InstanceNotFoundException {
		Role role = getRoleById(roleId);
		return role.getEmployeeMonthlySales(employeeId);
	}	
	
	public void changePercentageEmployeeDataInRole(int roleId, int employeeId, double percentage, int monthlySales) throws IllegalArgumentException, InstanceNotFoundException {
		Role role = getRoleById(roleId);
		role.changePercentageEmployeeData(employeeId, percentage, monthlySales);
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("Department ").append(name).append(":\n");
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
	
	public ArrayList<Integer> getEmployeesIdListInRole(int roleId) throws InstanceNotFoundException{
		Role role = getRoleById(roleId);
		if(role != null){
			return role.getEmployeesIdList();
		}
		throw new InstanceNotFoundException("Role doesn't exist");
	}
	
	public String getEmployeeDataInRole(int roleId, int employeeId) throws InstanceNotFoundException{
		Role role = getRoleById(roleId);
		if(role != null){
			return role.getEmployeeData(employeeId);
		}
		throw new InstanceNotFoundException("Role doesn't exist");
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Department)) {
			return false;
		}
		
		Department temp = (Department)obj;
		if(name != temp.name)
			return false;
		
		if(isSync != temp.isSync)
			return false;
		
		if(isChangeable != temp.isChangeable)
			return false;
		
		if(syncStartTime != temp.syncStartTime)
			return false;
		
		if(syncWorkFromHome != temp.syncWorkFromHome)
			return false;
		
		if(roles != temp.roles)
			return false;
		
		return true;
			
	}
}
