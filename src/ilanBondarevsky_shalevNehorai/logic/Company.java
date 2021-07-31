package ilanBondarevsky_shalevNehorai.logic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;

import ilanBondarevsky_shalevNehorai.listeners.CompanyListenable;

public class Company {
	public static final int WORK_HOURS_IN_MONTH = 160;
	public static final int WORK_HOURS_IN_DAY = 9;
	public static final int DEFAULT_START_WORK_DAY = 8;
	public static final int PROFIT_FOR_HOUR = 10;
	
	private final String FILE_PATH = "data.dat";
	
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
	
	public void createStartingData() {
		addDepartment("Multimedia", true, true);
		addRoleToDepartment("Multimedia", "Multimedia Designer", true);
		addEmployeeToRole("Multimedia", 0, "Aharon Ahava", EmployeeType.BASE_EMPLOYEE, 9, false, 100000, 0, 0);
		addEmployeeToRole("Multimedia", 0, "Tzofiya Mordecai", EmployeeType.HOUR_EMPLOYEE, 8, true, 100, 0, 0);
		addRoleToDepartment("Multimedia", "Software Engineering", true);
		addEmployeeToRole("Multimedia", 1, "Avishag Maor", EmployeeType.PERCENTAGE_EMPLOYEE, 6, false, 12000, 10, 300);
		
		addDepartment("Database", false, true);
		addRoleToDepartment("Database", "Database Administrator", false);
		addEmployeeToRole("Database", 2, "Moshe Gabay", EmployeeType.HOUR_EMPLOYEE, 8, false, 60, 0, 0);
		addRoleToDepartment("Database", "SQL Data Analyst", true);
		addEmployeeToRole("Database", 3, "Nitzan Hadasa", EmployeeType.HOUR_EMPLOYEE, 8, true, 70, 0, 0);
		
		addDepartment("PC Development", false, false);
		addRoleToDepartment("PC Development", "Software Architect", false);
		addEmployeeToRole("PC Development", 4, "Libi Oved", EmployeeType.PERCENTAGE_EMPLOYEE, 8, true, 4000, 20, 3000);
		addRoleToDepartment("PC Development", "Engineering Manager", true);
		addEmployeeToRole("PC Development", 5, "Reuben Aviel", EmployeeType.BASE_EMPLOYEE, 8, true, 40000, 0, 0);
		
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
	
	public void registerListener(CompanyListenable l) {
		allListeners.add(l);
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
	
	public void addRoleToDepartment(String departmentName, String roleName, boolean isRoleChangeable){
		Department department = getDepartmentByName(departmentName);
		if(department != null){
			try{
				int roleId = department.addRole(roleName, isRoleChangeable);
				fireAddedRole(departmentName, roleId);
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
				int employeeId = department.addEmployeeToRole(roleID, emplyeeType, employeeName, preferWorkingTime, preferWoringFromHome, salary, monthlyPercentage, monthlySales);
				fireAddedEmployee(departmentName, roleID, employeeId);
			}
			catch(IllegalArgumentException | InstanceNotFoundException | InvalidWorkingHoursException e){
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
			}
			catch(IllegalArgumentException | InvalidWorkingHoursException e){
				fireExcpetion(e);
			}
			finally {
				fireUpdateProfit();
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
			}
			catch(IllegalArgumentException | InstanceNotFoundException | InvalidWorkingHoursException e){
				fireExcpetion(e);
			}
			finally {
				fireUpdateProfit();
			}
		} else{
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
			return dep.getRolesIdList();
		} else{
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
			return null;
		}	
	}
	
	public boolean isDepartmentSync(String departmentName){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			return dep.isWorkingSync();		
		} else{
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
			return false;
		}
	}
	
	public boolean isDepartmentChangeable(String departmentName){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			return dep.isWorkChangeable();		
		} else{
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
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
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
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
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
			return false;
		}
	}
	
	public double getDepartmentProfit(String departmentName){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			return dep.profit();	
		} else{
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
			return 0;
		}
	}
	
	public double getRoleProfit(String departmentName, int roleId){
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
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
			return 0;
		}
	}
	
	public String getEmployeeDataInRole(String departmentName, int roleId, int employeeId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			try{
				return dep.getEmployeeDataInRole(roleId, employeeId);
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
				return "";
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
			return "";
		}
	}
	
	public double getEmployeeProfitInRole(String departmentName, int roleId, int employeeId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			try{
				return dep.getEmployeeProfitInRole(roleId, employeeId);	
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
				return 0;
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
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
				return "";
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
			return "";
		}
	}
	
	public String getEmployeeNameInRole(String departmentName, int roleId, int employeeId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			try{
				return dep.getEmployeeNameInRole(roleId, employeeId);
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
				return "";
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
			return "";
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
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
		}
		return "";
	}
	
	public ArrayList<Integer> getEmployeesInRole(String departmentName, int roleId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			try{
				return dep.getEmployeesIdListInRole(roleId);
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
				return null;
			}	
		} else{
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
			return null;
		}
	}
	
	public EmployeeType getEmployeeTypeInRole(String departmentName, int roleId, int employeeId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null){
			try{
				return dep.getEmployeeTypeInRole(roleId, employeeId);
			}
			catch(InstanceNotFoundException e){
				fireExcpetion(e);
				return null;
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
			return null;
		}
	}
	
	public double getEmployeePercentageInRole(String departmentName, int roleId, int employeeId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null) {
			try{
				return dep.getEmployeePercentageInRole(roleId, employeeId);
			}
			catch(IllegalArgumentException | InstanceNotFoundException e){
				fireExcpetion(e);
			}
		} else {
			fireExcpetion(new IllegalArgumentException("Deparment " + departmentName + " doesnt exists"));
		}
		
		return 0;
	}
	
	public int getEmployeeMonthlySalesInRole(String departmentName, int roleId, int employeeId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null) {
			try{
				return dep.getEmployeeMonthlySalesInRole(roleId, employeeId);
			}
			catch(IllegalArgumentException | InstanceNotFoundException e){
				fireExcpetion(e);
			}
		}
		else {
			fireExcpetion(new IllegalArgumentException("Deparment " + departmentName + " doesnt exists"));
		}
		
		return 0;
	}
	
	public void changePercentageEmployeeDataInRole(String departmentName, int roleId, int employeeId, double percentage, int monthlySales){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null) {
			try{
				dep.changePercentageEmployeeDataInRole(roleId, employeeId, percentage, monthlySales);
			}
			catch(IllegalArgumentException | InstanceNotFoundException e){
				fireExcpetion(e);
			}
			finally {
				fireUpdateProfit();
			}
		} 
		else {
			fireExcpetion(new IllegalArgumentException("deparment " + departmentName + " doesnt exists"));
		}
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
			listener.modelAddedDepartment(name);
		}
	}
	
	public void fireAddedRole(String departmentName, int roleId){
		for (CompanyListenable listener : allListeners) {
			listener.modelAddedRole(departmentName, roleId);
		}
	}
	
	public void fireAddedEmployee(String departmentName, int roleId, int employeeId){
		for (CompanyListenable listener : allListeners) {
			listener.modelAddedEmployee(departmentName, roleId, employeeId);
		}
	}
	
	// send command to update the profit in the company and all the departments and employees
	public void fireUpdateProfit() { 
		for (CompanyListenable companyListenable : allListeners) {
			companyListenable.modelUpdateProfit();
		}
	}
	
	public void saveAsBinaryFile() {
		try {
			ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
			outFile.writeObject(name);
			outFile.writeObject(departments);
			outFile.writeInt(Role.getGlobalId());
			outFile.writeInt(Employee.getGlobalId());
			outFile.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readBinaryFile() throws IOException, ClassNotFoundException {
		ObjectInputStream inputFile;
		try {
			inputFile = new ObjectInputStream(new FileInputStream(FILE_PATH));
			name = (String)inputFile.readObject();
			departments = (ArrayList<Department>)inputFile.readObject();
			Role.setGlobalId(inputFile.readInt());
			Employee.setGlobalId(inputFile.readInt());
			inputFile.close();
		}
		catch (IOException | ClassNotFoundException e) {
			throw e;
		}
	}
	
}
