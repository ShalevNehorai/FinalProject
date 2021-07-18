package ilanBondarevsky_shalevNehorai.application;

import javafx.scene.layout.GridPane;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ilanBondarevsky_shalevNehorai.logic.Company;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ViewChangeHours {
	private GridPane grid;
	
	private Label whoToChange;
	private Label workFromHome;
	private CheckBox homeInput;	
	private Label whenStart;
	private TextField whenStartInput;
	private Button changeButton;
	
	private final int H_GAP = 20;
	private final int V_GAP = 10;
	private final int SCENE_WIDTH = 400;
	private final int SCENE_HEIGHT = 200;
	private final int PADDING_INSETS = 20;
	
	public ViewChangeHours(MainWindow view, Stage stage, String depName){
		grid = new GridPane();
		whoToChange = new Label("Change department " + depName + " hours:");
		
		changeButton = new DefaultButton("Submit");
		changeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String startStr = whenStartInput.getText();
				
				view.changeDepartmentHours(depName, homeInput.isSelected(), startStr.isBlank()? Company.DEFAULT_START_WORK_DAY : Integer.parseInt(startStr));
				stage.close();
			}
		});
		
		addObjectsToGrid(stage);
	}
	
	public ViewChangeHours(MainWindow view, Stage stage, String depName, int roleId){
		grid = new GridPane();
		whoToChange = new Label();
		whoToChange.setText("Change " + view.askRoleEmployeeName(depName, roleId) + " hours:");
		
		changeButton = new DefaultButton("Submit");
		changeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String startStr = whenStartInput.getText();
				
				view.changeRoleHour(depName, roleId, homeInput.isSelected(), startStr.isBlank()? Company.DEFAULT_START_WORK_DAY : Integer.parseInt(startStr));
				stage.close();
			}
		});
		
		addObjectsToGrid(stage);
	}
	
	private void addObjectsToGrid(Stage stage) {
		grid = new GridPane();
		grid.setPadding(new Insets(PADDING_INSETS));
		grid.setHgap(H_GAP);
		grid.setVgap(V_GAP);
		
		grid.add(whoToChange, 0, 0);
		
		workFromHome = new Label("Is working from home?");
		grid.add(workFromHome, 0, 1);
		
		homeInput = new CheckBox();
		homeInput.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
				whenStart.setVisible(!newVal);
				whenStartInput.setVisible(!newVal);
				
				if(newVal) {
					whenStartInput.setText("" + Company.DEFAULT_START_WORK_DAY);
				}
			}
		});
		grid.add(homeInput, 1, 1);
		
		whenStart = new Label("Start working hour:\n(number between 0 to 23)");
		grid.add(whenStart, 0, 2);
		
		whenStartInput = new TextField();
		setTextFieldNumbersOnly(whenStartInput);
		grid.add(whenStartInput, 1, 2);
		
		grid.add(changeButton, 1, 3);
		
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
