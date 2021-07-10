package ilanBondarevsky_shalevNehorai.application;

import java.util.Random;

import javax.swing.JOptionPane;

import ilanBondarevsky_shalevNehorai.logic.EmployeeType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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

	public RoleViewCell(MainWindow mainWindow) {
		super();
		
		this.mainWindow = mainWindow;
		
		hbox = new HBox();
		hbox.setSpacing(20);
		
		roleNameLbl = new Label();
		employeeNameLbl = new Label();
		profitLbl = new Label();
		
		addEmployeeBtn = new Button("Add Employee");
		showBtn = new Button("show");
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
					addEmployeeToRole(item.getDeparmentName(), item.getRoleId(), names[rnd.nextInt(names.length)], EmployeeType.values()[rnd.nextInt(EmployeeType.values().length)],
							rnd.nextInt(24), rnd.nextBoolean(), rnd.nextInt(12000), rnd.nextDouble(), rnd.nextInt(500));
					
				}
			});
			
//			System.out.println(employeeName + " in cell " + item.getRoleId());
			if(employeeName != null) {
				addEmployeeBtn.setManaged(false);
				employeeNameLbl.setText(employeeName);
				employeeNameLbl.setVisible(true);
				profitLbl.setVisible(true);
				showBtn.setVisible(true);
				
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
				addEmployeeBtn.setManaged(true);
				employeeNameLbl.setVisible(false);
				profitLbl.setVisible(false);
				showBtn.setVisible(false);
				changeHoursBtn.setVisible(false);
				editEmployeeBtn.setVisible(false);
			}
			
			showBtn.setOnAction(new EventHandler<ActionEvent>() {	
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					JOptionPane.showMessageDialog(null, mainWindow.askRoleData(item.getDeparmentName(), item.getRoleId()));
				}
			});
			
			setGraphic(hbox);
		}
	}
	
	private void addEmployeeToRole(String deparmentName, int roleId, String emoloyeeName, EmployeeType type, int prefWorkingTime, boolean prefWorkingHome, int salary, double montlyPersantage, int monthlySales) {
		mainWindow.addEmployee(deparmentName, roleId, emoloyeeName, type, prefWorkingTime, prefWorkingHome, salary, montlyPersantage, monthlySales);
	}
}
