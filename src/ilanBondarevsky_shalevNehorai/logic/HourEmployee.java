package ilanBondarevsky_shalevNehorai.logic;

public class HourEmployee extends Employee {

	private int moneyForHour;
	
	protected HourEmployee(String name, int startTime, boolean isHomeWorking, int prefStartTime, boolean prefWorkHome,
			boolean isWorkingSync, boolean isWorkChangeable, int moneyForHour) throws IllegalArgumentException {
		super(name, startTime, isHomeWorking, prefStartTime, prefWorkHome, isWorkingSync, isWorkChangeable);
		
		if(moneyForHour < 0) {
			throw new IllegalArgumentException("money for hour cant be negative");
		}
		
		this.moneyForHour = moneyForHour;
	}
	
	@Override
	public EmployeeType getType() {
		return EmployeeType.HOUR_EMPLOYEE;
	}

	@Override
	public double salaryForHour() {
		return moneyForHour;
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer(super.toString());
		output.append(" The salary is: ").append(moneyForHour).append("\u20AA for Hour.");
		return output.toString();
	}
}
