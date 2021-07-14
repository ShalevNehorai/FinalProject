package ilanBondarevsky_shalevNehorai.logic;

import javax.management.InstanceNotFoundException;

public class Role implements CalculateAddedValueable, WorkChangeable, WorkingSync {
	
	private static int globalId = 0;
	
	private String name;
	private int id;
	
	private Employee employee;
	
	private boolean isWorkingSync;
	private boolean isWorkChangeable;
	
	
	
	public Role(String name, boolean isWorkingSync, boolean isWorkChangeable, 
			EmployeeType type, String empployeeName, int startTime, boolean isHomeWorking, int prefStartTime, boolean prefWorkHome, int salary, double mothlyPercentage, int monthlySales) throws IllegalArgumentException {
		if(name.isBlank()) {
			throw new IllegalArgumentException("Role name cant be blank");
		}
		
		this.name = name;
		
		this.isWorkChangeable = isWorkChangeable;
		this.isWorkingSync = isWorkingSync;
		
		if(type != null) {
			setEmployee(type, empployeeName, startTime, isHomeWorking, prefStartTime, prefWorkHome, salary, mothlyPercentage, monthlySales);
		}
		
		this.id = globalId++;
	}
	
	public Role(String name, boolean isWorkingSync, boolean isWorkChangeable) throws IllegalArgumentException {
		this(name, isWorkingSync, isWorkChangeable, null, "", 0, false, 0, false, 0, 0, 0);
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
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

	@Override
	public boolean isWorkingSync() {
		return isWorkingSync;
	}

	@Override
	public boolean isWorkChangeable() {
		return isWorkChangeable;
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
		if(isWorkChangeable) {
			if(employee != null) {
				employee.changeWorkingHours(startTime, homeWork);
			}
		}
		else{
			throw new IllegalArgumentException("Hours can not be changed!");
		}
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append(name).append(" Role: ");
		if(employee != null)
			output.append(employee.toString() + "\n");
		else
			output.append(" There is no employee working for this role.\n");
			
		output.append("isChangeable? ").append(isWorkChangeable).append("\nisSync? ").append(isWorkingSync);
		return output.toString();
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
		} else{
			throw new InstanceNotFoundException("The role is vacant");
		}
	}
	
	public double getEmployeePercentage() throws InstanceNotFoundException, IllegalArgumentException {
		if(employee.getType() != EmployeeType.PERCENTAGE_EMPLOYEE){
			throw new IllegalArgumentException("Not a Bonus Sales Percentage Employee");
		}
		PercentageEmployee tempEmployee = (PercentageEmployee)(employee);
		return tempEmployee.getPercentage();
	}
	
	public int getEmployeeMonthlySales() throws IllegalArgumentException, InstanceNotFoundException{
		if(employee.getType() != EmployeeType.PERCENTAGE_EMPLOYEE){
			throw new IllegalArgumentException("Not a Bonus Sales Percentage Employee");
		}
		PercentageEmployee tempEmployee = (PercentageEmployee)(employee);
		return tempEmployee.getMonthlySales();
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
		throw new InstanceNotFoundException("The role is vacant");
	}
	
	/*public void changeEmployeeSalesPercentage(double percentage) throws InstanceNotFoundException, IllegalArgumentException{
		if(employee != null){
			if(employee.getType() == EmployeeType.PERCENTAGE_EMPLOYEE){
				PercentageEmployee emp = (PercentageEmployee)employee;
				emp.setMonthlyPercentage(percentage);
			} else{
				throw new IllegalArgumentException("only employee with bonuses get percentage of sales");
			}
		}else{
			throw new InstanceNotFoundException("The role is vacant");
		}
	}
	
	public void changeEmployeeMonthlySales(int sales) throws InstanceNotFoundException, IllegalArgumentException{
		if(employee != null){
			if(employee.getType() == EmployeeType.PERCENTAGE_EMPLOYEE){
				PercentageEmployee emp = (PercentageEmployee)employee;
				emp.setMonthlySales(sales);
			} else{
				throw new IllegalArgumentException("only employee with bonuses get percentage of sales");
			}
		}else{
			throw new InstanceNotFoundException("The role is vacant");
		}
	}*/
}
