package ilanBondarevsky_shalevNehorai.logic;

import java.io.Serializable;

public abstract class Employee implements CalculateAddedValueable, WorkChangeable, WorkingSync, Serializable {

	private static int globalId = 0;
	
	private int id;
	private String name;
	private int startTime;
	private boolean isHomeWorking;
	private Preference preference;
	
	private boolean isWorkingSync;
	private boolean isWorkChangeable;
	
	
	protected Employee(String name, int startTime, boolean isHomeWorking, int prefStartTime, 
			boolean prefWorkHome, boolean isWorkingSync, boolean isWorkChangeable) throws IllegalArgumentException, InvalidWorkingHoursException {
		if(name.isBlank()) {
			throw new IllegalArgumentException("Employee name cant be blank");
		}
		
		if(startTime < 0 || startTime >= 24) {
			throw new InvalidWorkingHoursException();
		}
		
		this.name = name;
		this.startTime = startTime;
		this.isHomeWorking = isHomeWorking;
		this.preference = new Preference(prefWorkHome, prefStartTime);
		
		this.isWorkingSync = isWorkingSync;
		this.isWorkChangeable = isWorkChangeable;
		
		this.id = globalId++;
	}
	
	public static int getGlobalId() {
		return globalId;
	}
	
	public static void setGlobalId(int globalId) {
		Employee.globalId = globalId;
	}
	
	public int getId() {
		return id;
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
		double profitNotRounded = addedHours() * Company.PROFIT_FOR_HOUR;
		return roundToTwoDigit(profitNotRounded);
	}
	
	private double roundToTwoDigit(double num){
		return Math.round(num * 100) / 100.0;
	}
	
	@Override
	public void changeWorkingHours(int startTime, boolean homeWork) throws InvalidWorkingHoursException {
		if(startTime < 0 || startTime >= 24) {
			throw new InvalidWorkingHoursException();
		}
		
		if(isWorkChangeable()) {
			this.startTime = startTime;
			this.isHomeWorking = homeWork;
		}
	}

	private double addedHours(){
		double effAddedHours = preference.effectiveHours(startTime, isHomeWorking);
		double unEffAddedHours = preference.unEffectiveHours(startTime, isHomeWorking);
		return effAddedHours * preference.getEfficiencyValue() - unEffAddedHours * preference.getUnefficencyValue();
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("Employee ").append(name).append(", Id " + id + ":\n");
		if(isHomeWorking)
			output.append("Works from home. ");
		else
			output.append("Works from ").append(startTime).append(" to ").append((startTime + Company.WORK_HOURS_IN_DAY) % 24).append(". ");
		
		output.append("He/She ").append(preference.toString());
		return output.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Employee)) {
			return false;
		}
		
		Employee temp = (Employee)obj;
		return (id == temp.id) && (name == temp.name) && (startTime == temp.startTime) && (isHomeWorking == temp.isHomeWorking) && 
				(isWorkingSync == temp.isWorkingSync) && (isWorkChangeable == temp.isWorkChangeable) && preference.equals(temp.preference);
	}
	
	
}
