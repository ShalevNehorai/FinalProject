package ilanBondarevsky_shalevNehorai.application;

import java.util.Random;

import javax.swing.JOptionPane;

import ilanBondarevsky_shalevNehorai.logic.EmployeeType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
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
		employeeNameLbl = new Label();
		profitLbl = new Label();
		
		addEmployeeBtn = new Button("Add Employee");
		showBtn = new Button();
		showImg = new ImageView(new Image("ilanBondarevsky_shalevNehorai/Images/icons8-info-48.png"));		
		changeHoursBtn = new EditButton("change hours");
		editEmployeeBtn = new Button("Edit");
		
		hbox.getChildren().addAll(roleNameLbl, addEmployeeBtn, employeeNameLbl, profitLbl, showBtn, changeHoursBtn, editEmployeeBtn);
	}
	 
	
	@Override
	protected void updateItem(RoleView item, boolean empty) {
		super.updateItem(item, empty);
		
		if(item != null && !empty) {
//			int roleId = item.getRoleId();
			
			roleNameLbl.setText(mainWindow.askRoleName(item.getDeparmentName(), item.getRoleId()));
			
			String employeeName = mainWindow.askRoleEmployeeName(item.getDeparmentName(), item.getRoleId());
			
//			String employeeName = item.getEmployeeName();
					
			
			addEmployeeBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					// TODO open the add employee window
					
					Random rnd = new Random();
					String[] names = {"Cormac Millington", "Kean Guevara", "Giacomo Mcdaniel", "Pearce Terry", "Dorian Timms"};					
//					addEmployeeToRole(item.getDeparmentName(), item.getRoleId(), names[rnd.nextInt(names.length)], EmployeeType.values()[rnd.nextInt(EmployeeType.values().length)],
//							rnd.nextInt(24), rnd.nextBoolean(), rnd.nextInt(12000), rnd.nextDouble(), rnd.nextInt(500));
					AddEmployee addEmployee = new AddEmployee(mainWindow, item.getDeparmentName(), item.getRoleId(), new Stage());					
				}
			});
			
//			System.out.println(employeeName + " in cell " + item.getRoleId());
			if(employeeName != null) {
				addEmployeeBtn.setVisible(false);
				employeeNameLbl.setText(employeeName);
				employeeNameLbl.setVisible(true);
				profitLbl.setVisible(true);
				showBtn.setVisible(true);
				
				showBtn.setGraphic(showImg);
				
				changeHoursBtn.setVisible(true);
				
				if(mainWindow.isRoleSync(item.getDeparmentName(), item.getRoleId()) || !mainWindow.isRoleChangeable(item.getDeparmentName(), item.getRoleId())) {
					changeHoursBtn.setVisible(false);
				}
				
				editEmployeeBtn.setVisible(mainWindow.askEmployeeType(item.getDeparmentName(), item.getRoleId()) == EmployeeType.PERCENTAGE_EMPLOYEE);
				
				double profit = mainWindow.askEmployeeProfit(item.getDeparmentName(), item.getRoleId());
				
				profitLbl.setText(String.valueOf(profit));
				if(profit < 0) {
					profitLbl.setTextFill(Color.RED);
				}
				else {
					profitLbl.setTextFill(Color.GREEN);
				}
			}
			else {
				addEmployeeBtn.setVisible(true);
				employeeNameLbl.setVisible(false);
				profitLbl.setVisible(false);
				showBtn.setVisible(false);
				changeHoursBtn.setVisible(false);
				editEmployeeBtn.setVisible(false);
			}
			
			showBtn.setOnAction(new EventHandler<ActionEvent>() {	
				@Override
				public void handle(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, mainWindow.askRoleData(item.getDeparmentName(), item.getRoleId()));
				}
			});
			
			changeHoursBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					ChangeHours change = new ChangeHours(mainWindow, new Stage(), item.getDeparmentName(), item.getRoleId());
					
				}
			});
			
			editEmployeeBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					EditEmployee edit = new EditEmployee(mainWindow, new Stage(), item.getDeparmentName(), item.getRoleId());
				}
			});
			
			setGraphic(hbox);
		}
	}
}
