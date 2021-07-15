package ilanBondarevsky_shalevNehorai.listeners;

public interface CompanyListenable {	
	void modelAddedDepartment(String depName);
	void modelAddedRole(String depName, int roleId);
	void modelAddedEmployee(String depName, int roleId);
	
	void modelUpdateProfit();
	
	void modelExecpetion(String excpetionMsg);
	void modelMsg(String msg);
}
