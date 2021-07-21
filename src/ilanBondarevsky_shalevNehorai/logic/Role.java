package ilanBondarevsky_shalevNehorai.logic;

import java.io.Serializable;

import javax.management.InstanceNotFoundException;

public class Role implements CalculateAddedValueable, WorkChangeable, WorkingSync, Serializable {
	
	private static int globalId = 0;
	
	private String name;
	private int id;
	
	private Employee employee;
	
	private boolean isWorkingSync;
	private boolean isWorkChangeable;
	
	public Role(String name, boolean isWorkingSync, boolean isWorkChangeable) {
		if(name.isBlank()) {
			throw new IllegalArgumentException("Role name cant be blank");
		}
		
		this.name = name;
		
		this.isWorkChangeable = isWorkChangeable;
		this.isWorkingSync = isWorkingSync;
		
		this.id = globalId++;
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
	
	public void setEmployee(EmployeeType type, String name, int startTime, boolean isHomeWorking, int prefStartTime, boolean prefWorkHome, int salary, double mothlyPercentage, int monthlySales) throws IllegalArgumentException {
		//argument salary refer to salary for base employee and hour salary for hour employee
		switch (type) {
		case BASE_EMPLOYEE: 
			employee = new BaseEmployee(name, startTime, isHomeWorking, prefStartTime, prefWorkHome, this.isWorkingSync, this.isWorkChangeable, salary);
			break;
		case HOUR_EMPLOYEE:
			employee = new HourEmployee(name, startTime, isHomeWorking, prefStartTime, prefWorkHome, this.isWorkingSync, this.isWorkChangeable, salary);
			break;
		case PERCENTAGE_EMPLOYEE:
			employee = new PercentageEmployee(name, startTime, isHomeWorking, prefStartTime, prefWorkHome, this.isWorkingSync, this.isWorkChangeable, salary, mothlyPercentage, monthlySales);
			break;
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}
	
	public String getEmployeeName() {
		if(employee != null){
			return employee.getName();
		} 
		return null;
	}
	
	public EmployeeType getEmployeeType() throws InstanceNotFoundException{
		if(employee != null){
			return employee.getType();
		}
		else {
			throw new InstanceNotFoundException("The role is vacant");
		}
	}
	
	public double getEmployeePercentage() throws IllegalArgumentException, InstanceNotFoundException {
		if(employee != null) {
			if(employee.getType() != EmployeeType.PERCENTAGE_EMPLOYEE){
				throw new IllegalArgumentException("Not a Bonus Sales Percentage Employee");
			}
			PercentageEmployee tempEmployee = (PercentageEmployee)(employee);
			return tempEmployee.getPercentage();
		}
		else {
			throw new InstanceNotFoundException("role " + id + " doesnt have employee");
		}
	}
	
	public int getEmployeeMonthlySales() throws IllegalArgumentException, InstanceNotFoundException {
		if(employee != null) {
			if(employee.getType() != EmployeeType.PERCENTAGE_EMPLOYEE){
				throw new IllegalArgumentException("Not a Bonus Sales Percentage Employee");
			}
			PercentageEmployee tempEmployee = (PercentageEmployee)(employee);
			return tempEmployee.getMonthlySales();
		}
		else {
			throw new InstanceNotFoundException("role " + id + " doesnt have employee");
		}
	}

	@Override
	public double profit() {
		if(employee != null) {
			return employee.profit();
		}
		
		return 0;
	}
	
	@Override
	public void changeWorkingHours(int startTime, boolean homeWork) throws IllegalArgumentException{
		if(employee != null){
			if(isWorkChangeable){
				employee.changeWorkingHours(startTime, homeWork);				
			}
			else {
				throw new IllegalArgumentException("Employee " + getEmployeeName() + ", in role " + name + ", hours can't be changed!");				
			}
		} else {
			throw new IllegalArgumentException("Role " + name + " is vacant.");			
		}
	}
	
	public void changePercentageEmployeeData(double percentage, int sales) throws InstanceNotFoundException, IllegalArgumentException {
		if(employee != null){
			if(employee.getType() != EmployeeType.PERCENTAGE_EMPLOYEE){
				throw new IllegalArgumentException("Not a Bonus Sales Percentage Employee");
			}
			PercentageEmployee tempEmployee = (PercentageEmployee)employee;
			tempEmployee.setMonthlyPercentage(percentage);
			tempEmployee.setMonthlySales(sales);
		}
		else {
			throw new InstanceNotFoundException("role " + id + " doesnt have employee");
		}
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append(name).append(" Id " + id).append(" Role: ");
		if(employee != null)
			output.append(employee.toString() + "\n");
		else
			output.append(" There is no employee working for this role.\n");
			
		output.append("isChangeable? ").append(isWorkChangeable).append("\nisSync? ").append(isWorkingSync);
		return output.toString();
	}
}
