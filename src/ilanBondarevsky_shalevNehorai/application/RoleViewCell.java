package ilanBondarevsky_shalevNehorai.application;

import javax.swing.JOptionPane;

import ilanBondarevsky_shalevNehorai.logic.EmployeeType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RoleViewCell extends ListCell<RoleView> {
	private MainWindow mainWindow;
	
	private HBox hbox;
	private Label roleNameLbl;
	private Label employeeNameLbl;
	private Label profitLbl;
	
	private Button addEmployeeBtn;
	private Button showBtn;
	private Button changeHoursBtn;
	private Button editEmployeeBtn;
	
	private ImageView showImg;

	public RoleViewCell(MainWindow mainWindow) {
		super();
		
		this.mainWindow = mainWindow;
		
		hbox = new HBox();
		hbox.setSpacing(20);
		hbox.setAlignment(Pos.CENTER_LEFT);
		
		roleNameLbl = new Label();
		roleNameLbl.getStyleClass().add("default-label");
		employeeNameLbl = new Label();
		profitLbl = new Label();
		
		addEmployeeBtn = new Button("Add Employee");
		showBtn = new Button();
		showImg = new ImageView(new Image("ilanBondarevsky_shalevNehorai/Images/icons8-info-48.png"));
		showBtn.setGraphic(showImg);
		
		changeHoursBtn = new EditButton("change hours");
		editEmployeeBtn = new Button("Edit");
		
		hbox.getChildren().add(roleNameLbl);
	}
	 
	@Override
	protected void updateItem(RoleView item, boolean empty) {
		super.updateItem(item, empty);
		
		if(item != null && !empty) {			
			roleNameLbl.setText(mainWindow.askRoleName(item.getDeparmentName(), item.getRoleId()));
			
			String employeeName = mainWindow.askRoleEmployeeName(item.getDeparmentName(), item.getRoleId());					
			
			addEmployeeBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					ViewAddEmployee addEmployee = new ViewAddEmployee(mainWindow, item.getDeparmentName(), item.getRoleId(), new Stage());					
				}
			});
			
			showBtn.setOnAction(new EventHandler<ActionEvent>() {	
				@Override
				public void handle(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, mainWindow.askRoleData(item.getDeparmentName(), item.getRoleId()));
				}
			});
			
			changeHoursBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					ViewChangeHours change = new ViewChangeHours(mainWindow, new Stage(), item.getDeparmentName(), item.getRoleId());
					
				}
			});
			
			editEmployeeBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					ViewEditEmployee edit = new ViewEditEmployee(mainWindow, new Stage(), item.getDeparmentName(), item.getRoleId());
				}
			});
			
			if(employeeName != null) {
				hbox.getChildren().addAll(employeeNameLbl, profitLbl, showBtn);
				employeeNameLbl.setText(employeeName);
								
				if(!mainWindow.isRoleSync(item.getDeparmentName(), item.getRoleId()) && mainWindow.isRoleChangeable(item.getDeparmentName(), item.getRoleId())) {
					hbox.getChildren().add(changeHoursBtn);
				}
				
				if(mainWindow.askEmployeeType(item.getDeparmentName(), item.getRoleId()) == EmployeeType.PERCENTAGE_EMPLOYEE){
					hbox.getChildren().add(editEmployeeBtn);
				}
				
				double profit = mainWindow.askEmployeeProfit(item.getDeparmentName(), item.getRoleId());
				
				profitLbl.setText(String.valueOf(profit) + "\u20AA");
				if(profit < 0) {
					profitLbl.setTextFill(Color.RED);
				}
				else {
					profitLbl.setTextFill(Color.GREEN);
				}
			}
			else {
				hbox.getChildren().add(addEmployeeBtn);
			}
			
			setGraphic(hbox);
		}
	}
}
