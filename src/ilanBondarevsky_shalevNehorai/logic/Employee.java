package ilanBondarevsky_shalevNehorai.logic;

public abstract class Employee implements CalculateAddedValueable, WorkChangeable, WorkingSync {
	
	private String name;
	private int startTime;
	private boolean isHomeWorking;
	private Preference preference;
	
	private boolean isWorkingSync;
	private boolean isWorkChangeable;
	
	protected Employee(String name, int startTime, boolean isHomeWorking, int prefStartTime, 
			boolean prefWorkHome, boolean isWorkingSync, boolean isWorkChangeable) throws IllegalArgumentException {
		if(name.isBlank()) {
			throw new IllegalArgumentException("Role name cant be blank");
		}
		
		if(startTime < 0 || startTime >= 24) {
			throw new IllegalArgumentException("start working hour must be in range 0 - 23");
		}
		
		this.name = name;
		this.startTime = startTime;
		this.isHomeWorking = isHomeWorking;
		this.preference = new Preference(prefWorkHome, prefStartTime);
		
		this.isWorkingSync = isWorkingSync;
		this.isWorkChangeable = isWorkChangeable;
	}
	
	public abstract double salaryForHour();
	public abstract EmployeeType getType();
	
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
		return addedHours() * salaryForHour();
	}
	
	@Override
	public void changeWorkingHours(int startTime, boolean homeWork) {
		if(isWorkChangeable()) {
			this.startTime = startTime;
			this.isHomeWorking = homeWork;
		}
	}

	private double addedHours(){
		double effAddedHours = preference.effectiveHours(startTime, isHomeWorking);
		double unEffAddedHours = preference.unEffectiveHours(startTime, isHomeWorking);
		return effAddedHours * preference.efficiencyValue() - unEffAddedHours * preference.efficiencyValue();
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("Employee ").append(name);
		if(isHomeWorking)
			output.append(" works from home.");
		else
			output.append(" works from ").append(startTime).append(" to ").append((startTime + Company.WORK_HOURS_IN_DAY + 1) % 24).append(".");
		
		output.append(" He/She prefer working: ").append(preference.toString());
		return output.toString();
	}
	
	public String getName(){
		return name;
	}

}
