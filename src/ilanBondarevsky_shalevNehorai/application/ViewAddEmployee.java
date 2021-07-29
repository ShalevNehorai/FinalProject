package ilanBondarevsky_shalevNehorai.application;

import javafx.scene.layout.GridPane;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import ilanBondarevsky_shalevNehorai.logic.Company;
import ilanBondarevsky_shalevNehorai.logic.EmployeeType;

public class ViewAddEmployee {
	private GridPane grid;
	
	private Label roleName;
	private Label name;
	private TextField nameInput;
	private Label preferStartHour;
	private TextField preferHourInput;
	private Label workFromHome;
	private CheckBox workFromHomeInput;
	private Label type;
	private ComboBox<EmployeeType> typeInput;
	private Label salary;
	private TextField salaryInput;
	private Label salesPercentage;
	private TextField salesPercentageInput;
	private Label salesMonthlyLbl;
	private TextField salesMonthlyInput;
	private Button addData;
	
	private final int H_GAP = 20;
	private final int V_GAP = 10;
	private final int SCENE_WIDTH = 500;
	private final int SCENE_HEIGHT = 400;
	private final int PADDING_INSETS = 20;
	
	public ViewAddEmployee(MainWindow view, String depName ,int roleId, Stage stage){
		grid = new GridPane();
		grid.setPadding(new Insets(PADDING_INSETS));
		grid.setHgap(H_GAP);
		grid.setVgap(V_GAP);
		
		roleName = new Label("Set employee in role " + view.askRoleName(depName, roleId) + " in deparment " + depName);
		grid.add(roleName, 0, 0);
		
		name = new Label();
		name.setText("Name: ");
		grid.add(name, 0, 1);
		
		nameInput = new TextField();
		grid.add(nameInput, 1, 1);
				
		workFromHome = new Label("Prefer Working From Home? ");
		grid.add(workFromHome, 0, 2);
		
		workFromHomeInput = new CheckBox();
		grid.add(workFromHomeInput, 1, 2);
		
		workFromHomeInput.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
				preferStartHour.setVisible(!newVal);
				preferHourInput.setVisible(!newVal);
				
				if(newVal) {
					if(preferHourInput.getText().isBlank()) {
						preferHourInput.setText("8");
					}
				}
			}
		});
		
		preferStartHour = new Label("Prefer Start Work Hour:\n(number between 0 to 23)");
		grid.add(preferStartHour, 0, 3);
		
		preferHourInput = new TextField();
		Util.setTextFieldNumbersOnly(preferHourInput);
		Util.setStartingText(preferHourInput, 8);
		grid.add(preferHourInput, 1, 3);
		
		type = new Label("Employee Type: ");
		grid.add(type, 0, 4);
		
		typeInput = new ComboBox<EmployeeType>();
		typeInput.getItems().addAll(EmployeeType.values());
		typeInput.getSelectionModel().selectFirst();
		typeInput.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				switch(typeInput.getValue()){
					case BASE_EMPLOYEE:
						salary.setText("Salary (Monthly): ");
						salesPercentage.setVisible(false);
						salesPercentageInput.setVisible(false);
						salesMonthlyLbl.setVisible(false);
						salesMonthlyInput.setVisible(false);
						break;
					case HOUR_EMPLOYEE:
						salary.setText("Salary (Per Hour): ");
						salesPercentage.setVisible(false);
						salesPercentageInput.setVisible(false);
						salesMonthlyLbl.setVisible(false);
						salesMonthlyInput.setVisible(false);
						break;
					case PERCENTAGE_EMPLOYEE:
						salary.setText("Salary (Monthly): ");
						salesPercentage.setVisible(true);
						salesPercentageInput.setVisible(true);
						salesMonthlyLbl.setVisible(true);
						salesMonthlyInput.setVisible(true);
						break;
					
					default:
						break;
				}
				
			}
		});
		grid.add(typeInput, 1, 4);
		
		salary = new Label("Salary (Monthly): ");
		grid.add(salary, 0, 5);
		
		salaryInput = new TextField();
		Util.setTextFieldNumbersOnly(salaryInput);
		Util.setStartingText(salaryInput, 0);
		grid.add(salaryInput, 1, 5);
		
		salesPercentage = new Label("Your sales Percentage:\n(A number between 0 to 100)");
		grid.add(salesPercentage, 0, 6);
		salesPercentage.setVisible(false);
		
		salesPercentageInput = new TextField();
		Util.setTextFieldNumbersOnly(salesPercentageInput);
		Util.setStartingText(salesPercentageInput, 0);
		grid.add(salesPercentageInput, 1, 6);
		salesPercentageInput.setVisible(false);
		
		salesMonthlyLbl = new Label("Your monthly sales: ");
		grid.add(salesMonthlyLbl, 0, 7);
		salesMonthlyLbl.setVisible(false);
		
		salesMonthlyInput = new TextField();
		Util.setTextFieldNumbersOnly(salesMonthlyInput);
		Util.setStartingText(salesMonthlyInput, 0);
		grid.add(salesMonthlyInput, 1, 7);
		salesMonthlyInput.setVisible(false);
		
		addData = new DefaultButton("Add Employee");
		addData.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String prefWorkHoursStr = preferHourInput.getText();
				if(prefWorkHoursStr.isBlank()) {
					prefWorkHoursStr = String.valueOf(Company.DEFAULT_START_WORK_DAY);
				}
				
				String salaryStr = salaryInput.getText();
				if(salaryStr.isBlank()) {
					salaryStr = "0";
				}
				
				String salePerStr = salesPercentageInput.getText();
				if(salePerStr.isBlank()) {
					salePerStr = "0";
				}
				
				String saleMonStr = salesMonthlyInput.getText();
				if(saleMonStr.isBlank()) {
					saleMonStr = "0";
				}
				
				view.addEmployee(depName, roleId, nameInput.getText(), typeInput.getValue(), 
								Integer.parseInt(prefWorkHoursStr), workFromHomeInput.isSelected(), 
								Integer.parseInt(salaryStr), Double.parseDouble(salePerStr) / 100.0,
								Integer.parseInt(saleMonStr));
				
				stage.close();
			}
		});
		grid.add(addData, 0, 8);
			
			
		Scene scene = new Scene(grid, SCENE_WIDTH, SCENE_HEIGHT);
		stage.setScene(scene);
		stage.show();	
	}
	
	private void setTextFieldNumbersOnly(TextField tf, int startingText) {
		tf.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	tf.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		tf.setText(String.valueOf(startingText));
	}
}
