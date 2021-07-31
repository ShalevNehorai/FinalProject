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
	
	public Preference(boolean workFromHome, int startWorkHour) throws WorkingHoursException{
		if(startWorkHour < 0 || startWorkHour >= 24) {
			throw new WorkingHoursException();
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
	
	public int effectiveHours(int startTime, boolean homeWorker){	
		int minTime = Math.min(startTime, prefStartTime);
		int maxTime = Math.max(startTime, prefStartTime);
		
		if(homeWorker && prefWorkFromHome){
			return WORK_HOURS;
		}
		if(homeWorker != prefWorkFromHome){
			return 0;
		}
		
		if(startTime == DEFUALT_TIME || prefStartTime == DEFUALT_TIME)
			return 0;
		
		if(startTime < DEFUALT_TIME && prefStartTime < DEFUALT_TIME) {
			return Math.min(DEFUALT_TIME - maxTime, WORK_HOURS);
		}
		if(startTime > DEFUALT_TIME && prefStartTime > DEFUALT_TIME){
			int diffrence = Math.abs(startTime - prefStartTime);
			if(diffrence >= WORK_HOURS)
				return 0;
			return Math.min(minTime - DEFUALT_TIME, WORK_HOURS);
		}
		
		int endWorkTime = (maxTime + WORK_HOURS);
		if((endWorkTime > 24) && (endWorkTime % 24 > minTime)){
			return Math.min((endWorkTime % 24) - minTime, WORK_HOURS);
		}
		
		return 0;
	}
	
	 public int unEffectiveHours(int startTime, boolean homeWorker){
		if(homeWorker){
			if(prefWorkFromHome)
				return 0;
			return WORK_HOURS;
		}
		if(!homeWorker && prefWorkFromHome){
			return Math.min(Math.abs(DEFUALT_TIME - startTime), WORK_HOURS);
		}
		
		if(startTime == DEFUALT_TIME)
			return 0;
		if(startTime == prefStartTime)
			return 0;
		
		if(startTime > DEFUALT_TIME && prefStartTime > DEFUALT_TIME) {
			if((startTime >= DEFUALT_TIME + WORK_HOURS && prefStartTime >= DEFUALT_TIME + WORK_HOURS)) {
				return Math.min(Math.abs(startTime - prefStartTime), WORK_HOURS);
			}
			if(prefStartTime < startTime) {
				return Math.min(startTime - prefStartTime, WORK_HOURS) ;
			}
			if(prefStartTime >= startTime + WORK_HOURS) {
				return startTime - DEFUALT_TIME;
			}
			return 0;
		}
		
		if(startTime < DEFUALT_TIME && prefStartTime < DEFUALT_TIME) {
			if(startTime < prefStartTime) {
				return prefStartTime - startTime;
			} else {
				return 0;
			}
		}
		
		int minTime = Math.min(startTime, prefStartTime);
		int maxTime = Math.max(startTime, prefStartTime);
		int endWorkTime = maxTime + WORK_HOURS;
		if((endWorkTime > 24) && (endWorkTime % 24 > minTime)){
			if(startTime > DEFUALT_TIME) {
				return prefStartTime + 24 - startTime;
			}
			else {
				return Math.abs(DEFUALT_TIME  - (endWorkTime % 24));
			}
		} 
		
		return Math.min(Math.abs(DEFUALT_TIME - startTime), WORK_HOURS);
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("prefer working ");
		if(prefWorkFromHome)
			output.append("from home.");
		else
			output.append("from ").append(prefStartTime).append(" to ").append((prefStartTime + WORK_HOURS) % 24).append(".");
		return output.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Preference)){
			return false;
		}
		Preference temp = (Preference)(obj);
		if(temp.prefWorkFromHome != prefWorkFromHome)
			return false;
		if(temp.prefStartTime != prefStartTime)
			return false;
		return true;
	}
}
