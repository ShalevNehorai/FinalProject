package ilanBondarevsky_shalevNehorai.logic;

public class Preference {
	
	private final double HOME_EFFECTIVE_VALUE = 0.1;
	private final double OFFICE_EFFECTIVE_VALUE = 0.2;
	private final int DEFUALT_TIME = Company.DEFAULT_START_WORK_DAY;
	private final int WORK_HOURS = Company.WORK_HOURS_IN_DAY;
	
	private int startTime;
	private boolean workFromHome;
	
	public Preference(boolean workFromHome, int startWorkHour) throws IllegalArgumentException{
		if(startWorkHour < 0 || startWorkHour >= 24) {
			throw new IllegalArgumentException("start working hour must be in range 0 - 23");
		}
		
		this.startTime = startWorkHour;
		this.workFromHome = workFromHome;
	}
	
	public double efficiencyValue(){
		if(workFromHome)
			return HOME_EFFECTIVE_VALUE;
		return OFFICE_EFFECTIVE_VALUE;
	}
	
	public int effectiveHours(int currentStartTime, boolean isHomeWorker) {
		if(isHomeWorker && this.workFromHome)
			return WORK_HOURS;
		if(isHomeWorker != this.workFromHome)
			return 0;	
		
		if(currentStartTime == DEFUALT_TIME || startTime == DEFUALT_TIME)
			return 0;
		if(startTime == currentStartTime)
			return Math.abs(DEFUALT_TIME - startTime);
		else if(startTime > currentStartTime) {
			return earlierThanPreferedEffectiveWorkHours(currentStartTime);
		}
		else {
			return laterThanPreferedEffectiveWorkHours(currentStartTime);
		}
	}
	
	private int laterThanPreferedEffectiveWorkHours(int currentStartTime) {
		if(startTime < DEFUALT_TIME)
			return DEFUALT_TIME - startTime;
		if(startTime > DEFUALT_TIME) {
			if(startTime > DEFUALT_TIME)
				return startTime - DEFUALT_TIME;
			if(startTime < DEFUALT_TIME)
				return 0;
		}
		return 0;
	}
	private int earlierThanPreferedEffectiveWorkHours(int currentStartTime) {
		if(currentStartTime < DEFUALT_TIME) {
			if(startTime > DEFUALT_TIME) {
				return 0;
			}
			if(startTime < DEFUALT_TIME) 
				return DEFUALT_TIME - startTime;
		}
		if(currentStartTime > DEFUALT_TIME) {
			return currentStartTime - DEFUALT_TIME;
		}
		
		return 0;
	}
	
	public int unEffectiveHours(int currentStartTime, boolean isHomeWorker) {	
		if(isHomeWorker && this.workFromHome)
			return 0;
		if(isHomeWorker != this.workFromHome)
			return WORK_HOURS;
		
		if(currentStartTime == DEFUALT_TIME)
			return 0;
		if(startTime == DEFUALT_TIME)
			return Math.abs(DEFUALT_TIME - currentStartTime);
		
		if(startTime == currentStartTime)
			return 0;
		else if(startTime > currentStartTime) {
			return earlierThanPreferedUneffectiveWorkHours(currentStartTime);
		}
		
		else {
			return laterThanPreferedUneffectiveWorkHours(currentStartTime);
		}
	}
	
	private int earlierThanPreferedUneffectiveWorkHours(int currentStartTime) {
		if(currentStartTime < DEFUALT_TIME) {
			if(startTime < DEFUALT_TIME) {
				return startTime - currentStartTime;
			}
			if(startTime > DEFUALT_TIME) 
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
			if(startTime > DEFUALT_TIME)
				return currentStartTime - startTime;
			if(startTime < DEFUALT_TIME)
				return currentStartTime - DEFUALT_TIME;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("Preffer Working ");
		if(workFromHome)
			output.append("from home.");
		else
			output.append("from ").append(startTime).append(" to ").append((startTime + WORK_HOURS + 1) % 24).append(".");
		return output.toString();
	}
}
