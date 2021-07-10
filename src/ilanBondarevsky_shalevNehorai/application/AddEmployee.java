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

import ilanBondarevsky_shalevNehorai.logic.EmployeeType;

public class AddEmployee {
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
	private Button addData;
	
	private final int H_GAP = 20;
	private final int V_GAP = 30;
	private final int SCENE_WIDTH = 400;
	private final int SCENE_HEIGHT = 200;
	private final int PADDING_INSETS = 20;
	
	public AddEmployee(MainWindow view, String depName ,int roleId, Stage stage){
		grid = new GridPane();
		grid.setPadding(new Insets(PADDING_INSETS));
		grid.setHgap(H_GAP);
		grid.setVgap(V_GAP);
		
		roleName = new Label();
		roleName.setText("Set employee in role " + view.askRoleName(depName, roleId) + " in " + depName);
		grid.add(roleName, 0, 0);
		
		name = new Label();
		name.setText("Name: ");
		grid.add(name, 0, 1);
		
		nameInput = new TextField();
		grid.add(nameInput, 1, 1);
		
		preferStartHour = new Label();
		preferStartHour.setText("Prefer Start Work Hour: ");
		grid.add(preferStartHour, 0, 2);
		
		preferHourInput = new TextField();
		setTextFieldNumbersOnly(preferHourInput);
		grid.add(preferHourInput, 1, 2);
		
		workFromHome = new Label();
		workFromHome.setText("Prefer Working From Home? ");
		grid.add(workFromHome, 0, 3);
		
		workFromHomeInput = new CheckBox();
		grid.add(workFromHomeInput, 1, 3);
		
		type = new Label();
		type.setText("Employee Type: ");
		grid.add(type, 0, 4);
		
		typeInput = new ComboBox<EmployeeType>();
		typeInput.getItems().addAll(EmployeeType.values());
		typeInput.setValue(EmployeeType.BASE_EMPLOYEE);
		typeInput.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				switch(typeInput.getValue()){
					case BASE_EMPLOYEE:
						salary.setText("Salary (Monthly): ");
						salesPercentage.setVisible(false);
						salesPercentageInput.setVisible(false);
						salesPercentageInput.setText("0");
						break;
					case HOUR_EMPLOYEE:
						salary.setText("Salary (Per Hour): ");
						salesPercentage.setVisible(false);
						salesPercentageInput.setVisible(false);
						salesPercentageInput.setText("0");
						break;
					case PERCENTAGE_EMPLOYEE:
						salary.setText("Salary (Monthly): ");
						salesPercentage.setVisible(true);
						salesPercentageInput.setVisible(true);
						salesPercentageInput.setText("");
						break;
					
					default:
						break;
				}
				
			}
		});
		grid.add(typeInput, 1, 4);
		
		salary = new Label();
		salary.setText("Salary (Monthly): ");
		grid.add(salary, 0, 5);
		
		salaryInput = new TextField();
		setTextFieldNumbersOnly(salaryInput);
		grid.add(salaryInput, 1, 5);
		
		salesPercentage = new Label();
		salesPercentage.setText("Your sales Percentage: (A number between 0 to 100)");
		grid.add(salesPercentage, 0, 6);
		
		salesPercentageInput = new TextField();
		setTextFieldNumbersOnly(salesPercentageInput);
		grid.add(salesPercentageInput, 1, 6);
		
		addData = new Button("Add Employee");
		addData.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				view.addEmployee(depName, roleId, nameInput.getText(), typeInput.getValue(), 
								Integer.parseInt(preferHourInput.getText()), workFromHomeInput.isSelected(), 
								Integer.parseInt(salaryInput.getText()), Integer.parseInt(salesPercentage.getText()) / 100,
								0);
			}
		});
			
			
		Scene scene = new Scene(grid, SCENE_WIDTH, SCENE_HEIGHT);
		stage.setScene(scene);
		stage.show();	
	}
	
	private void setTextFieldNumbersOnly(TextField tf) {
		tf.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	tf.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
	}
}
