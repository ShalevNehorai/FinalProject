package ilanBondarevsky_shalevNehorai.logic;

public class InvalidWorkingHoursException extends Exception {

	public InvalidWorkingHoursException() {
		super("Start working hour must be in range 0 - 23");
	}

}
