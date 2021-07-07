package ilanBondarevsky_shalevNehorai.logic;

public interface WorkChangeable {
	boolean isWorkChangeable();
	void changeWorkingHours(int startTime, boolean homeWork);
}
