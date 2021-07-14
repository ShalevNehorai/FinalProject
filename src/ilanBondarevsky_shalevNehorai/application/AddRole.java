package ilanBondarevsky_shalevNehorai.application;

import javafx.scene.layout.GridPane;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class AddRole {
	private GridPane grid;
	
	private Label depName;
	private Label roleName;
	private TextField role;
	private Label isChangeable;
	private CheckBox changeable;
	private Button addData;
	
	private final int H_GAP = 20;
	private final int V_GAP = 10;
	private final int SCENE_WIDTH = 400;
	private final int SCENE_HEIGHT = 175;
	private final int PADDING_INSETS = 20;
	
	public AddRole(MainWindow view, String depName, Stage stage){
		grid = new GridPane();
		grid.setPadding(new Insets(PADDING_INSETS));
		grid.setHgap(H_GAP);
		grid.setVgap(V_GAP);
		
		this.depName = new Label();
		this.depName.setText("Add role to " + depName);
		grid.add(this.depName, 0, 0);
		
		roleName = new Label();
		roleName.setText("Role Name: ");
		grid.add(roleName, 0, 1);
		
		role = new TextField();
		grid.add(role, 1, 1);
		
		isChangeable = new Label();
		isChangeable.setText("Can Change Work Hours? ");
		
		changeable = new CheckBox();
		changeable.setSelected(false);
		
		if(view.isDeparmentSync(depName)){
			changeable.setSelected(true);
		}else if(view.isDepatmentChangeable(depName)){
			grid.add(isChangeable, 0, 2);
			grid.add(changeable, 1, 2);
		}
		
		
		addData = new DefaultButton("Add Role");
		addData.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				view.addRole(depName, role.getText(), changeable.isSelected());
				stage.close();
			}
		});
		
		grid.add(addData, 0, 3);
		
		Scene scene = new Scene(grid, SCENE_WIDTH, SCENE_HEIGHT);
		stage.setScene(scene);
		stage.show();
	}
}
