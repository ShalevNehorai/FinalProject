package ilanBondarevsky_shalevNehorai.logic;

public class Main {

	public static void main(String[] args) {
		Company com = new Company("Test");
		
		com.addDepartment("shlomo", true, true);
		
		com.addRoleToDepartment("shlomo", "", true);
		com.addRoleToDepartment("shlomo", "ui/ux", true);
		
		com.addEmployeeToRole("shlomo", 0, "ilan", EmployeeType.HOUR_EMPLOYEE, 12, false, 75, 0, 0);
		com.addEmployeeToRole("shlomo", 1, "shalev", EmployeeType.BASE_EMPLOYEE, 12, true, 12000, 0, 0);
		
		com.changeRoleWorkHours("shlomo", 0, 22, false);
		com.changeRoleWorkHours("shlomo", 1, 0, true);
		
//		com.changeDepartmentWorkHours("shlomo", 12, false);
		
		
		System.out.println(com.toString());
		System.out.println(com.getCompanyProfit());
		System.out.println();
		
	}

}
