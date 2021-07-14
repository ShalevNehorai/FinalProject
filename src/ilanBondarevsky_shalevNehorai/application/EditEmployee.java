package ilanBondarevsky_shalevNehorai.application;

import javafx.scene.layout.GridPane;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ilanBondarevsky_shalevNehorai.logic.Company;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class EditEmployee {
	private GridPane grid;
	
	private Label employee;
	private Label monthlySales;
	private TextField salesInput;
	private Label changePercentage;
	private CheckBox change;
	private Label percentage;
	private TextField percentageInput;
	private Button editButton;
	
	private final int H_GAP = 20;
	private final int V_GAP = 10;
	private final int SCENE_WIDTH = 400;
	private final int SCENE_HEIGHT = 175;
	private final int PADDING_INSETS = 20;
	
	public  EditEmployee(MainWindow view, Stage stage, String depName, int roleId){
		grid = new GridPane();
		grid.setPadding(new Insets(PADDING_INSETS));
		grid.setHgap(H_GAP);
		grid.setVgap(V_GAP);
		
		double currentPercentage = view.askEmployeePercentage(depName, roleId);
		String employeeName = view.askRoleEmployeeName(depName, roleId);
		
		employee = new Label();
		employee.setText("Employee " + employeeName);
		grid.add(employee, 0, 0);
		
		monthlySales = new Label();
		monthlySales.setText("Monthly sales: ");
		grid.add(monthlySales, 0, 1);
		
		salesInput = new TextField();
		setTextFieldNumbersOnly(salesInput);
		salesInput.setText("" + view.askEmployeeMonthlySales(depName, roleId));
		grid.add(salesInput, 1, 1);
		
		changePercentage = new Label();
		changePercentage.setText("Change sales percentage? Current at "+ (currentPercentage * 100) + "%");
		grid.add(changePercentage, 0, 2);
		
		change = new CheckBox();
		change.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(change.isSelected()){
					percentage.setVisible(true);
					percentageInput.setVisible(true);
					percentageInput.setText("");
				} else{
					percentage.setVisible(false);
					percentageInput.setVisible(false);
					percentageInput.setText("" + currentPercentage);
				}
			}
		});
		grid.add(change, 1, 2);
		
		percentage = new Label();
		percentage.setText("New percentage: Can be 0 - 100");
		grid.add(percentage, 0, 3);
		
		percentageInput = new TextField();
		setTextFieldNumbersOnly(percentageInput);
		
		editButton = new Button();
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				view.changeEmployeePercentageData(depName, roleId, employeeName, 
												Integer.parseInt(percentageInput.getText()) / 100,
												Integer.parseInt(salesInput.getText()));
												
				stage.close();
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
