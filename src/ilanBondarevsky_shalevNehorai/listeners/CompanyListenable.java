package ilanBondarevsky_shalevNehorai.listeners;

public interface CompanyListenable {	
	void modelExecpetion(String excpetionMsg);
	void modelMsg(String msg);

	void modelAddedDepartment(String depName);
	void modelAddedRole(String depName, int roleId);
	void modelAddedEmployee(String depName, int roleId, int employeeId);
	
	void modelUpdateProfit();
}
