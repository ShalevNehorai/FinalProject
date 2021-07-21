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
				System.out.println("added role" + roleId + " " + getRoleName(departmentName, roleId) + " under " + departmentName);//TODO delete
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
				System.out.println("added employee " + emplyeeType + " " + employeeName);//TODO delete
			}
			catch(IllegalArgumentException | InstanceNotFoundException e){
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
			catch(IllegalArgumentException e){
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
			catch(IllegalArgumentException | InstanceNotFoundException e){
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
				return null;
			}
		} else{
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
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
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
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
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
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
			fireExcpetion(new InstanceNotFoundException("Deparment " + departmentName + " doesnt exists"));
			return null;
		}
	}
	
	public double getEmployeePercentage(String departmentName, int roleId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null) {
			try{
				return dep.getEmployeePercentage(roleId);
			}
			catch(IllegalArgumentException | InstanceNotFoundException e){
				fireExcpetion(e);
			}
		} else {
			fireExcpetion(new IllegalArgumentException("Deparment " + departmentName + " doesnt exists"));
		}
		
		return 0;
	}
	
	public int getEmployeeMonthlySales(String departmentName, int roleId){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null) {
			try{
				return dep.getEmployeeMonthlySales(roleId);
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
	
	public void changePercentageEmployeeData(String departmentName, int roleId, double percentage, int monthlySales){
		Department dep = getDepartmentByName(departmentName);
		if(dep != null) {
			try{
				dep.changePercentageEmployeeData(roleId, percentage, monthlySales);
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
	
	public void fireAddedRole(String departmentName, int roleId){//TODO needed to send the role id ?
		for (CompanyListenable listener : allListeners) {
			listener.modelAddedRole(departmentName, roleId);
		}
	}
	
	public void fireAddedEmployee(String departmentName, int roleId){
		for (CompanyListenable listener : allListeners) {
			listener.modelAddedEmployee(departmentName, roleId);
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
	
	public void readBinaryFile() {
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
			System.out.println("data file not found. fail loading data");
			e.printStackTrace();
		}	
	}
}
