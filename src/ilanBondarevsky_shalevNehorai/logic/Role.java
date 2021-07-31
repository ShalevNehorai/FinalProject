package ilanBondarevsky_shalevNehorai.logic;

import java.io.Serializable;
import java.util.ArrayList;
import javax.management.InstanceNotFoundException;

public class Role implements CalculateAddedValueable, WorkChangeable, WorkingSync, Serializable {
	
	private static int globalId = 0;
	
	private String name;
	private int id;
	
	private ArrayList<Employee> employeeList;
	
	private boolean isWorkingSync;
	private boolean isWorkChangeable;
	
	private int startTime;
	private boolean isWorkFromHome;
	
	public Role(String name, boolean isWorkingSync, boolean isWorkChangeable, int startTime, boolean workFromHome) {
		if(name.isBlank()) {
			throw new IllegalArgumentException("Role name can't be blank");
		}
		
		this.name = name;
		
		this.isWorkChangeable = isWorkChangeable;
		this.isWorkingSync = isWorkingSync;
		
		this.startTime = startTime;
		this.isWorkFromHome = workFromHome;
		
		this.employeeList = new ArrayList<Employee>();
		
		this.id = globalId++;
	}
	
	public Role(String name, boolean isWorkingSync, boolean isWorkChangeable) {
		this(name, isWorkingSync, isWorkChangeable, Company.DEFAULT_START_WORK_DAY, false);
	}
	
	public static int getGlobalId() {
		return globalId;
	}
	
	public static void setGlobalId(int globalId) {
		Role.globalId = globalId;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public boolean isWorkingSync() {
		return isWorkingSync;
	}

	@Override
	public boolean isWorkChangeable() {
		return isWorkChangeable;
	}
	
	public int addEmployee(EmployeeType type, String name, int prefStartTime, boolean prefWorkHome, int salary, double mothlyPercentage, int monthlySales) throws IllegalArgumentException, WorkingHoursException {
		//argument salary refer to salary for base employee and hour salary for hour employee
		switch (type) {
		case BASE_EMPLOYEE: 
			employeeList.add(new BaseEmployee(name, startTime, isWorkFromHome, prefStartTime, prefWorkHome, this.isWorkingSync, this.isWorkChangeable, salary));
			break;
		case HOUR_EMPLOYEE:
			employeeList.add(new HourEmployee(name, startTime, isWorkFromHome, prefStartTime, prefWorkHome, this.isWorkingSync, this.isWorkChangeable, salary));
			break;
		case PERCENTAGE_EMPLOYEE:
			employeeList.add(new PercentageEmployee(name, startTime, isWorkFromHome, prefStartTime, prefWorkHome, this.isWorkingSync, this.isWorkChangeable, salary, mothlyPercentage, monthlySales));
			break;
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
		return employeeList.get(employeeList.size() - 1).getId();
	}
	
	public String getEmployeeName(int employeeId) throws InstanceNotFoundException{
		Employee employee = getEmployeeById(employeeId);
		if(employee != null){
			return employee.getName();
		} else{
			throw new InstanceNotFoundException("Employee doesn't exist in this role");
		}
	}
	
	private Employee getEmployeeById(int employeeId) throws InstanceNotFoundException{
		for (Employee employee : employeeList) {
			if(employeeId == employee.getId())
				return employee;
		} 
		throw new InstanceNotFoundException("Employee doesn't exist in this role");
	}
	
	public EmployeeType getEmployeeType(int employeeId) throws InstanceNotFoundException{
		Employee employee = getEmployeeById(employeeId);
		if(employee != null){
			return employee.getType();
		} else{
			throw new InstanceNotFoundException("Employee doesn't exist in this role");
		}
	}
	
	public double getEmployeeProfit(int employeeId) throws InstanceNotFoundException{
		Employee employee = getEmployeeById(employeeId);
		if(employee != null){
			return employee.profit();
		} else {
			throw new InstanceNotFoundException("Employee doesn't exist in this role");
		}
	}
	
	public double getEmployeePercentage(int employeeId) throws IllegalArgumentException, InstanceNotFoundException {
		Employee employee = getEmployeeById(employeeId);
		if(employee != null){
			if(employee.getType() != EmployeeType.PERCENTAGE_EMPLOYEE)
				throw new IllegalArgumentException("Not a Bonus Sales Percentage Employee");
			PercentageEmployee tempEmployee = (PercentageEmployee)(employee);
			return tempEmployee.getPercentage();
		} else{
			throw new InstanceNotFoundException("Employee doesn't exist in this role");
		}
	}
	
	public int getEmployeeMonthlySales(int employeeId) throws IllegalArgumentException, InstanceNotFoundException {
		Employee employee = getEmployeeById(employeeId);
		if(employee != null){
			if(employee.getType() != EmployeeType.PERCENTAGE_EMPLOYEE)
				throw new IllegalArgumentException("Not a Bonus Sales Percentage Employee");
			PercentageEmployee tempEmployee = (PercentageEmployee)(employee);
			return tempEmployee.getMonthlySales();
		} else{
			throw new InstanceNotFoundException("Employee doesn't exist in this role");
		}
	}

	@Override
	public double profit() {
		double profit = 0;
		for (Employee employee : employeeList) {
			profit += employee.profit();
		}
		
		return profit;
	}
	
	@Override
	public void changeWorkingHours(int startTime, boolean homeWork) throws IllegalArgumentException, WorkingHoursException{
		if(!isWorkChangeable())
			throw new IllegalArgumentException("Role's hours cannot be changed");
		
		for (Employee employee : employeeList) {
			employee.changeWorkingHours(startTime, homeWork);
		}
		
		this.startTime = startTime;
		this.isWorkFromHome = homeWork;
	}
	
	public void changePercentageEmployeeData(int employeeId, double percentage, int sales) throws InstanceNotFoundException, IllegalArgumentException {
		Employee employee = getEmployeeById(employeeId);
		if(employee != null){
			if(employee.getType() != EmployeeType.PERCENTAGE_EMPLOYEE) {
				throw new IllegalArgumentException("Not a Bonus Sales Percentage Employee");
			}
			PercentageEmployee tempEmployee = (PercentageEmployee)(employee);
			tempEmployee.setMonthlyPercentage(percentage);
			tempEmployee.setMonthlySales(sales);
		} else{
			throw new InstanceNotFoundException("Employee doesn't exist in this role");
		}
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("The role is in ").append(isWorkingSync? "" : "un").append("synchronous department.");
		output.append(" Working hours for this role can").append(isWorkChangeable? "":"'t").append(" be change");
		return output.toString();
	}
	
	public ArrayList<Integer> getEmployeesIdList(){
		ArrayList<Integer> idList = new ArrayList<Integer>();
		for (Employee employee : employeeList) {
			idList.add(employee.getId());
		}
		return idList;
	}
	
	public String getEmployeeData(int employeeId) throws InstanceNotFoundException{
		Employee employee = getEmployeeById(employeeId);
		if(employee != null){
			return employee.toString();
		} else{
			throw new InstanceNotFoundException("Employee doesn't exist in this role");
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Role))
			return false;
		Role temp = (Role)(obj);
		if(temp.id != id)
			return false;
		if(temp.getName() != name)
			return false;
		if(temp.isWorkChangeable() != isWorkChangeable)
			return false;
		if(temp.isWorkingSync() != isWorkingSync)
			return false;	
		if(temp.isWorkFromHome != isWorkFromHome)
			return false;
		if(temp.startTime != startTime)
			return false;
		if(temp.employeeList != employeeList)
			return false;
		return true;
	}
}
