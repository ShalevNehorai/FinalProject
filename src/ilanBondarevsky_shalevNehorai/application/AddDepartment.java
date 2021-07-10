package ilanBondarevsky_shalevNehorai.application;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class AddDepartment {
	private final int H_GAP = 10;
	private final int V_GAP = 10;
	private final int SCENE_WIDTH = 500;
	private final int SCENE_HEIGHT = 250;
	private final int PADDING_INSETS = 20;
	
	private GridPane grid;
	
	private Label companyNameLbl;
	private TextField depNameInput;
	private Label depNameLbl;
	private Label isChangeableLbl;
	private CheckBox changeableCkb;
	private Label isSyncableLbl;
	private CheckBox syncableLbl;
	private Button addDataBtn;
	
	public AddDepartment(MainWindow view, String companyName, Stage stage){
		
	 	Insets insets = new Insets(PADDING_INSETS, 0, 0, PADDING_INSETS);
		
		grid = new GridPane();
//		grid.setPadding(insets);
		grid.setHgap(H_GAP);
		grid.setVgap(V_GAP);
		
		Font font = new Font(16);
		
		this.companyNameLbl = new Label();
		this.companyNameLbl.setText("Add demprment to the company: " + companyName);
		companyNameLbl.setFont(font);
				
		depNameLbl = new Label();
		depNameLbl.setText("Department Name: ");
		depNameLbl.setFont(font);
		grid.add(depNameLbl, 0, 0);
		
		depNameInput = new TextField();
		depNameInput.setFont(font);
		grid.add(depNameInput, 1, 0);
		
		isChangeableLbl = new Label();
		isChangeableLbl.setText("Hours changeable? : ");
		isChangeableLbl.setFont(font);
		grid.add(isChangeableLbl, 0, 1);
		
		changeableCkb = new CheckBox();
		grid.add(changeableCkb, 1, 1);
		
		isSyncableLbl = new Label();
		isSyncableLbl.setText("Department synchronized? : ");
		isSyncableLbl.setFont(font);
		grid.add(isSyncableLbl, 0, 2);
		
		syncableLbl = new CheckBox();
		syncableLbl.setOnAction(new EventHandler<ActionEvent>() {
			@Override
				public void handle(ActionEvent arg0) {
					if(syncableLbl.isSelected()){
						changeableCkb.setSelected(true);
						changeableCkb.setVisible(false);
						isChangeableLbl.setVisible(false);
					}
					else{
						changeableCkb.setSelected(false);
						changeableCkb.setVisible(true);
						isChangeableLbl.setVisible(true);
					}
				
				}
		});
		grid.add(syncableLbl, 1, 2);
		
		addDataBtn = new DefaultButton("Add Department");
		addDataBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.print(changeableCkb.isSelected());
				view.addDepartment(depNameInput.getText(), syncableLbl.isSelected(), changeableCkb.isSelected());	
				stage.close();
			}
		});
		grid.add(addDataBtn, 0, 3);
		
		VBox vbox = new VBox();
		vbox.setPadding(insets);
		vbox.setSpacing(V_GAP);
		
		vbox.getChildren().addAll(companyNameLbl, grid);
		
		Scene scene = new Scene(vbox, SCENE_WIDTH, SCENE_HEIGHT);
		stage.setScene(scene);
		stage.show();
		
	}
}