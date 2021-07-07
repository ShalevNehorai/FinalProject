package ilanBondarevsky_shalevNehorai.logic;

public class BaseEmployee extends Employee {

	private int salary; // salary in a month
	
	protected BaseEmployee(String name, int startTime, boolean isHomeWorking, int prefStartTime, boolean prefWorkHome,
			boolean isWorkingSync, boolean isWorkChangeable, int salary) throws IllegalArgumentException{
		super(name, startTime, isHomeWorking, prefStartTime, prefWorkHome, isWorkingSync, isWorkChangeable);
		
		if(salary < 0) {
			throw new IllegalArgumentException("Salary cant be negative");
		}
		
		this.salary = salary;
	}

	@Override
	public double salaryForHour() {
		return ((double)salary) / ((double)Company.WORK_HOURS_IN_MONTH);
	}
	
	public int getSalary(){
		return salary;
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer(super.toString());
		output.append(" The salary is: ").append(salary).append("\u20AA for month.");
		return output.toString();
	}
}
