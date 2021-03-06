package ilanBondarevsky_shalevNehorai.logic;

public class HourEmployee extends Employee {

	private int moneyForHour;
	
	protected HourEmployee(String name, int startTime, boolean isHomeWorking, int prefStartTime, boolean prefWorkHome,
			boolean isWorkingSync, boolean isWorkChangeable, int moneyForHour) throws IllegalArgumentException, InvalidWorkingHoursException {
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
		output.append("\nThe salary is: ").append(moneyForHour).append("\u20AA for an Hour.");
		return output.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof HourEmployee)) {
			return false;
		}
		
		HourEmployee temp = (HourEmployee) obj;
		if(moneyForHour != temp.moneyForHour) {
			return false;
		}
		
		return super.equals(obj);
	}
}
