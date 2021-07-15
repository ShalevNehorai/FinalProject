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
	private final int SCENE_HEIGHT = 200;
	private final int PADDING_INSETS = 20;
	
	public  EditEmployee(MainWindow view, Stage stage, String depName, int roleId){
		grid = new GridPane();
		grid.setPadding(new Insets(PADDING_INSETS));
		grid.setHgap(H_GAP);
		grid.setVgap(V_GAP);
		
		int currentPercentage = (int)(view.askEmployeePercentage(depName, roleId) * 100);
		int currentMonthlySales = view.askEmployeeMonthlySales(depName, roleId);
		String employeeName = view.askRoleEmployeeName(depName, roleId);
		
		employee = new Label("Employee " + employeeName);
		grid.add(employee, 0, 0);
		
		monthlySales = new Label("Monthly sales: ");
		grid.add(monthlySales, 0, 1);
		
		salesInput = new TextField("" + currentMonthlySales);
		setTextFieldNumbersOnly(salesInput);
		grid.add(salesInput, 1, 1);
		
		/*changePercentage = new Label();
		changePercentage.setText("Change sales percentage? \nCurrent at "+ (currentPercentage * 100) + "%");
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
		grid.add(change, 1, 2);*/
		
		percentage = new Label("Percentage (Can be 0 - 100)");
		grid.add(percentage, 0, 2);
		
		percentageInput = new TextField("" + (int)(currentPercentage));
		setTextFieldNumbersOnly(percentageInput);
		grid.add(percentageInput, 1, 2);
		
		editButton = new DefaultButton("Submit");
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String percentStr = percentageInput.getText();
				
				String saleStr = salesInput.getText();
				
				view.changeEmployeePercentageData(depName, roleId, 
												percentStr.isBlank()? (currentPercentage / 100.0) : (Double.parseDouble(percentStr) / 100.0),
												saleStr.isBlank()? (currentMonthlySales) : (Integer.parseInt(saleStr)));
												
				stage.close();
			}
		});
		grid.add(editButton, 0, 3);
		
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
