package ilanBondarevsky_shalevNehorai.logic;

public class Role implements CalculateAddedValueable, WorkChangeable, WorkingSync {
	
	private static int globalId = 0;
	
	private String name;
	private int id;
	
	private Employee employee;
	
	private boolean isWorkingSync;
	private boolean isWorkChangeable;
	
	
	
	public Role(String name, boolean isWorkingSync, boolean isWorkChangeable, 
			EmployeeType type, String empployeeName, int startTime, boolean isHomeWorking, int prefStartTime, boolean prefWorkHome, int salary, double mothlyPercentage, int monthlySales) throws IllegalArgumentException {
		if(name.isBlank()) {
			throw new IllegalArgumentException("Role name cant be blank");
		}
		
		this.name = name;
		
		this.isWorkChangeable = isWorkChangeable;
		this.isWorkingSync = isWorkingSync;
		
		if(type != null) {
			setEmployee(type, empployeeName, startTime, isHomeWorking, prefStartTime, prefWorkHome, salary, mothlyPercentage, monthlySales);
		}
		
		this.id = globalId++;
	}
	
	public Role(String name, boolean isWorkingSync, boolean isWorkChangeable) throws IllegalArgumentException {
		this(name, isWorkingSync, isWorkChangeable, null, "", 0, false, 0, false, 0, 0, 0);
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setEmployee(EmployeeType type, String name, int startTime, boolean isHomeWorking, int prefStartTime, boolean prefWorkHome, int salary, double mothlyPercentage, int monthlySales) throws IllegalArgumentException {
		//argument salary refer to salary for base employee and hour salary for hour employee
		switch (type) {
		case BASE_EMPLOYEE: 
			employee = new BaseEmployee(name, startTime, isHomeWorking, prefStartTime, prefWorkHome, this.isWorkingSync, this.isWorkChangeable, salary);
			break;
		case HOUR_EMPLOYEE:
			employee = new HourEmployee(name, startTime, isHomeWorking, prefStartTime, prefWorkHome, this.isWorkingSync, this.isWorkChangeable, salary);
			break;
		case PARSENTAGE_EMPLOYEE:
			employee = new PercentageEmployee(name, startTime, isHomeWorking, prefStartTime, prefWorkHome, this.isWorkingSync, this.isWorkChangeable, salary, mothlyPercentage, monthlySales);
			break;
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}

	@Override
	public boolean isWorkingSync() {
		return isWorkingSync;
	}

	@Override
	public boolean isWorkChangeable() {
		return isWorkChangeable;
	}

	@Override
	public double addedMoney() {
		if(employee != null) {
			return employee.addedMoney();
		}
		
		return 0;
	}
	
	@Override
	public void changeWorkingHours(int startTime, boolean homeWork) {
		if(isWorkChangeable) {
			if(employee != null) {
				employee.changeWorkingHours(startTime, homeWork);
			}
		}
		else{
			//TODO fire not Changeable
			System.out.println("Cant change work hours for role " + this.id + " " + this.name);
		}
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append(name).append(" Role: ");
		if(employee != null)
			output.append(employee.toString());
		else
			output.append(" There is no employee working for this role.");
			
		output.append(" - isChangeable?:").append(isWorkChangeable).append("- isSync?:").append(isWorkingSync);
		return output.toString();
	}

}