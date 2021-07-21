package ilanBondarevsky_shalevNehorai.logic;

import java.io.Serializable;

public class Preference implements Serializable {
	
	private final double HOME_EFFECTIVE_VALUE = 0.1;
	private final double OFFICE_EFFECTIVE_VALUE = 0.2;
	private final double UNEFFECTIVE_VALUE = 0.2;
	private final int DEFUALT_TIME = Company.DEFAULT_START_WORK_DAY;
	private final int WORK_HOURS = Company.WORK_HOURS_IN_DAY;
	
	private int prefStartTime;
	private boolean prefWorkFromHome;
	
	public Preference(boolean workFromHome, int startWorkHour) throws IllegalArgumentException{
		if(startWorkHour < 0 || startWorkHour >= 24) {
			throw new IllegalArgumentException("start working hour must be in range 0 - 23");
		}
		
		this.prefStartTime = startWorkHour;
		this.prefWorkFromHome = workFromHome;
	}
	
	public double getEfficiencyValue(){
		if(prefWorkFromHome)
			return HOME_EFFECTIVE_VALUE;
		return OFFICE_EFFECTIVE_VALUE;
	}
	
	public double getUnefficencyValue() {
		return UNEFFECTIVE_VALUE; 	
	}
	public int effectiveHours(int currentStartTime, boolean isHomeWorker) {
		if(isHomeWorker && this.prefWorkFromHome)
			return WORK_HOURS;
		if(isHomeWorker != this.prefWorkFromHome)
			return 0;	

		if(currentStartTime == DEFUALT_TIME) {
			return 0;
		}
		
		if(prefStartTime == currentStartTime)
			return Math.abs(DEFUALT_TIME - prefStartTime);
		else if(prefStartTime > currentStartTime) {
			return earlierThanPreferedEffectiveWorkHours(currentStartTime);
		}
		else {
			return laterThanPreferedEffectiveWorkHours(currentStartTime);
		}
	}
	
	private int laterThanPreferedEffectiveWorkHours(int currentStartTime) {
		if(prefStartTime < DEFUALT_TIME)
			return DEFUALT_TIME - prefStartTime;
		if(prefStartTime > DEFUALT_TIME) {
			if(prefStartTime > DEFUALT_TIME)
				return prefStartTime - DEFUALT_TIME;
			if(prefStartTime < DEFUALT_TIME)
				return 0;
		}
		return 0;
	}
	private int earlierThanPreferedEffectiveWorkHours(int currentStartTime) {
		if(currentStartTime < DEFUALT_TIME) {
			if(prefStartTime > DEFUALT_TIME) {
				return 0;
			}
			if(prefStartTime < DEFUALT_TIME) 
				return DEFUALT_TIME - prefStartTime;
		}
		if(currentStartTime > DEFUALT_TIME) {
			return currentStartTime - DEFUALT_TIME;
		}
		
		return 0;
	}
	
	public int unEffectiveHours(int currentStartTime, boolean isHomeWorker) {	
		if(isHomeWorker && this.prefWorkFromHome)
			return 0;
		if(isHomeWorker != this.prefWorkFromHome)
			return WORK_HOURS;
		
		if(currentStartTime == DEFUALT_TIME)
			return 0;
			
		if(prefStartTime == DEFUALT_TIME)
			return Math.abs(DEFUALT_TIME - currentStartTime);
		
		if(prefStartTime == currentStartTime)
			return 0;
		else if(prefStartTime > currentStartTime) {
			return earlierThanPreferedUneffectiveWorkHours(currentStartTime);
		}
		
		else {
			return laterThanPreferedUneffectiveWorkHours(currentStartTime);
		}
	}
	
	private int earlierThanPreferedUneffectiveWorkHours(int currentStartTime) {
		if(currentStartTime < DEFUALT_TIME) {
			if(prefStartTime < DEFUALT_TIME) {
				return prefStartTime - currentStartTime;
			}
			if(prefStartTime > DEFUALT_TIME) 
				return DEFUALT_TIME - currentStartTime;
		}
		if(currentStartTime > DEFUALT_TIME) {
			return 0;
		}
		return 0;
	}
	private int laterThanPreferedUneffectiveWorkHours(int currentStartTime) {
		if(currentStartTime < DEFUALT_TIME)
			return 0;
		if(currentStartTime > DEFUALT_TIME) {
			if(prefStartTime > DEFUALT_TIME)
				return currentStartTime - prefStartTime;
			if(prefStartTime < DEFUALT_TIME)
				return currentStartTime - DEFUALT_TIME;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("Preffer Working ");
		if(prefWorkFromHome)
			output.append("from home.");
		else
			output.append("from ").append(prefStartTime).append(" to ").append((prefStartTime + WORK_HOURS) % 24).append(".");
		return output.toString();
	}
}
