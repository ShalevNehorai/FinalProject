package ilanBondarevsky_shalevNehorai.logic;

public class PercentageEmployee extends BaseEmployee {
	
	private double monthlyPercentage;
	private int monthlySales;

	protected PercentageEmployee(String name, int startTime, boolean isHomeWorking, int prefStartTime,
			boolean prefWorkHome, boolean isWorkingSync, boolean isWorkChangeable, int salary, double mothlyPercentage, int monthlySales) throws IllegalArgumentException {
		super(name, startTime, isHomeWorking, prefStartTime, prefWorkHome, isWorkingSync, isWorkChangeable, salary);
		
		if(monthlySales < 0) {
			throw new IllegalArgumentException("monthly sales cant be nagative");
		}
		if(mothlyPercentage < 0) {
			throw new IllegalArgumentException("monthly persentage cant be nagative");
		}
		
		this.monthlyPercentage = mothlyPercentage;
		this.monthlySales = monthlySales;
	}

	@Override
	public double salaryForHour() {
		return super.salaryForHour() * monthlyPercentage * monthlySales;
	}
	
	public void setMonthlySales(int monthlySales) {
		this.monthlySales = monthlySales;
	}
	
	public void setMonthlyPercentage(double monthlyPercentage) {
		this.monthlyPercentage = monthlyPercentage;
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer(super.toString());
		output.append(" The salary is: ").append(this.getSalary()).append("\u20AA with ").append(monthlyPercentage).append(" percentage from sales.");
		return output.toString();
	}
}