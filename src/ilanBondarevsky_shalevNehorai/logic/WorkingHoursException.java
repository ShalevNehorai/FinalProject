package ilanBondarevsky_shalevNehorai.logic;

public class WorkingHoursException extends Exception {

	public WorkingHoursException() {
		super("Start working hour must be in range 0 - 23");
	}

}
