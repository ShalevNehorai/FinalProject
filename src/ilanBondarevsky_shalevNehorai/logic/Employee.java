package ilanBondarevsky_shalevNehorai.logic;

import java.io.Serializable;

public abstract class Employee implements CalculateAddedValueable, WorkChangeable, WorkingSync, Serializable {
	
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
	
	public String getName(){
		return name;
	}
	
	public abstract EmployeeType getType();
	public abstract double salaryForHour();
	
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
		double profitNotRounded = addedHours() * salaryForHour();
		return roundToTwoDigit(profitNotRounded);
	}
	
	private double roundToTwoDigit(double num){
		return Math.round(num * 100) / 100.0;
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
			output.append(" works from ").append(startTime).append(" to ").append((startTime + Company.WORK_HOURS_IN_DAY + 1) % 24).append(".\n");
		
		output.append("He/She prefer working: ").append(preference.toString());
		return output.toString();
	}
}
