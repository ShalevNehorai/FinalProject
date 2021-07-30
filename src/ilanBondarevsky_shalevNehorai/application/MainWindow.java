package ilanBondarevsky_shalevNehorai.application;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import ilanBondarevsky_shalevNehorai.listeners.ViewListenable;
import ilanBondarevsky_shalevNehorai.logic.EmployeeType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class MainWindow implements CompanyViewable {
	public static final String STYLE_SHEET_PATH = "/ilanBondarevsky_shalevNehorai/application.css";
	
	private final int WIN_WIDTH = 1000;//1280;
	private final int WIN_HEIGHT = 720;
	
	private MainWindow mainWindow;
	
	private ArrayList<ViewListenable> allListeners;
	
 	private Label companyNameLbl;
 	private Label companyProfitLbl;
 	
	private ObservableList<DeparmentView> deptList;
	private ListView<DeparmentView> depListView;

	public MainWindow(Stage primaryStage) {
		mainWindow = this;
		
		allListeners = new ArrayList<ViewListenable>();
		
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(20, 20, 20, 20));
		
			//@TOP
		HBox hbox = new HBox();
		hbox.setSpacing(40);
		
		Font topFont = new Font("Cambria", 32);
		
		companyNameLbl = new Label();
		companyNameLbl.setFont(topFont);
		companyProfitLbl = new Label();
		companyProfitLbl.setFont(topFont);
		

		hbox.getChildren().addAll(companyNameLbl, companyProfitLbl);
		
		root.setTop(hbox);
		BorderPane.setMargin(hbox, new Insets(10));
			//END @TOP
		
			//@CENTER
		deptList = FXCollections.observableArrayList();
		depListView = new ListView<DeparmentView>(deptList);
		depListView.setSelectionModel(new NoSelectionModel<DeparmentView>());
		updateDepartmentListView();
		
		root.setCenter(depListView);
		BorderPane.setMargin(depListView, new Insets(10));
			//END @CENTER
		
			//@BUTTOM
		Button addDeptButton = new DefaultButton("Add Deparment");
		addDeptButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {				
				new ViewAddDepartment(mainWindow, companyNameLbl.getText(), new Stage());
			}
		});
		
		Button exitBtn = new Button("save & quit");
		exitBtn.getStyleClass().add("exitBtn");
		exitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				saveCompanyData();
				primaryStage.close();
			}
		});
		
		HBox bottomBox = new HBox(addDeptButton, exitBtn);
		bottomBox.setSpacing(WIN_WIDTH - 340);
		
		root.setBottom(bottomBox);
		BorderPane.setMargin(addDeptButton, new Insets(10));
			//END @BUTTOM
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				saveCompanyData();
				primaryStage.close();
			}
		});
		
		Scene scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT);
		scene.getStylesheets().add(STYLE_SHEET_PATH);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@Override
	public void registerListener(ViewListenable l) {
		allListeners.add(l);
	}
	
	@Override
	public void initViewData() {
		askCompanyName();
		askCompanyProfit();
		askDeparmnetsNames();
	}
	
	@Override
	public void updateProfit() {
		askCompanyProfit();
		updateDepartmentListView();
	}
	
	private void updateDepartmentListView() {
		depListView.setCellFactory(new Callback<ListView<DeparmentView>, ListCell<DeparmentView>>() {
			@Override
			public ListCell<DeparmentView> call(ListView<DeparmentView> listView) {
				return new DeparmentViewCell(mainWindow);
			}
		});
	}

	@Override
	public void askCompanyName() {
		String name = allListeners.get(0).viewAskCompanyName();
		companyNameLbl.setText(name);
	}

	@Override
	public void askCompanyProfit() {
		double profit = allListeners.get(0).viewAskCompanyProfit();
		
		companyProfitLbl.setText("profit: " + String.valueOf(profit) + "\u20AA");
		
		if(profit < 0) {
			companyProfitLbl.setTextFill(Color.RED);
		}
		else {
			companyProfitLbl.setTextFill(Color.GREEN);
		}
	}

	@Override
	public void askDeparmnetsNames() {
		ArrayList<String> list = allListeners.get(0).viewAskDeparmentsNames();
		
		if(list != null) {		
			for (String string : list) {
				if(!isDeptListContains(string)) {
					deptList.add(new DeparmentView(string));
				}
			}
		}
	}
	
	private boolean isDeptListContains(String name) {
		for (DeparmentView deptView : deptList) {
			if(deptView.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isDeparmentSync(String departmentName) {
		return allListeners.get(0).viewAskIsDepartmentSync(departmentName);
	}

	@Override
	public boolean isDepatmentChangeable(String departmentName) {
		return allListeners.get(0).viewAskIsDepartmentChangeable(departmentName);
	}

	@Override
	public double askDeparmentProfit(String deparmentName) {
		return allListeners.get(0).viewAskDepartmentProfit(deparmentName);
	}

	@Override
	public ArrayList<Integer> askRolesInDeparment(String deparmentName) {
		return allListeners.get(0).viewAskRolesIdInDepartment(deparmentName);
	}

	@Override
	public String askRoleName(String departmentName, int id) {
		return allListeners.get(0).viewAskRoleName(departmentName, id);
	}
	
	@Override
	public double askRoleProfit(String departmentName, int id) {
		return allListeners.get(0).viewAskRoleProfit(departmentName, id);
	}
	
	@Override
	public boolean isRoleSync(String deparmentName, int roleId) {
		return allListeners.get(0).viewAskIsRoleSync(deparmentName, roleId);
	}

	@Override
	public boolean isRoleChangeable(String departmentName, int roleId) {
		return allListeners.get(0).viewAskIsRoleChangeable(departmentName, roleId);
	}
	
	@Override
	public String askRoleData(String deparmentName, int roleId) {
		return allListeners.get(0).viewAskRoleData(deparmentName, roleId);
	}

	@Override
	public ArrayList<Integer> askEmployeesInRole(String deparmentName, int roleId) {
		return allListeners.get(0).viewAskEmployeesInRole(deparmentName, roleId);
	}
	
	@Override
	public String askEmployeeName(String deparmentName, int roleId, int employeeId) {
		return allListeners.get(0).viewAskEmployeeName(deparmentName, roleId, employeeId);
	}

	@Override
	public double askEmployeeProfit(String deparmentName, int roleId, int employeeId) {
		return allListeners.get(0).viewAskEmployeeProfit(deparmentName, roleId, employeeId);
	}
	
	@Override
	public String askEmployeeData(String deparmentName, int roleId, int employeeId) {
		return allListeners.get(0).viewAskEmployeeData(deparmentName, roleId, employeeId);
	}
	
	@Override
	public EmployeeType askEmployeeType(String deparmentName, int roleId, int employeeId) {
		return allListeners.get(0).viewAskEmployeeType(deparmentName, roleId, employeeId);
	}

	@Override
	public double askEmployeePercentage(String depName, int roleId, int employeeId) {
		return allListeners.get(0).viewAskEmployeePercentage(depName, roleId, employeeId);
	}
	
	@Override
	public int askEmployeeMonthlySales(String depName, int roleId, int employeeId) {
		return allListeners.get(0).viewAskEmployeeMonthlySales(depName, roleId, employeeId);
	}

	@Override
	public void addDepartment(String name, boolean isSync, boolean isChangeable) {
		for (ViewListenable viewListenable : allListeners) {
			viewListenable.viewAddDepartment(name, isSync, isChangeable);
		}
	}

	@Override
	public void addRole(String deparmentName, String roleName, boolean isRoleChangeable) {
		for (ViewListenable viewListenable : allListeners) {
			viewListenable.viewAddRole(deparmentName, roleName, isRoleChangeable);
		}
	}

	@Override
	public void addEmployee(String deparmentName, int roleId, String employeeName, EmployeeType type,
			int preferWorkingTime, boolean prefWorkingHome, int salary, double monthlyPersentage, int monthlySales) {
		for (ViewListenable viewListenable : allListeners) {
			viewListenable.viewAddEpmloyee(deparmentName, roleId, employeeName, type, preferWorkingTime, prefWorkingHome, salary, monthlyPersentage, monthlySales);
		}
	}

	@Override
	public void changeEmployeePercentageData(String deparmentName, int roleId, int employeeId, double percentage, int monthlySales) {
		for (ViewListenable viewListenable : allListeners) {
			viewListenable.viewChangeEmployeePersentageData(deparmentName, roleId, employeeId, percentage, monthlySales);
		}
	}
	
	@Override
	public void changeRoleHours(String depName, int roleId, boolean workFromHome, int startHour) {
		for (ViewListenable viewListenable : allListeners) {
			viewListenable.viewChangeRoleHours(depName, roleId, workFromHome, startHour);
		}
	}
	
	@Override
	public void changeDepartmentHours(String depName, boolean workHome, int startHour) {
		for (ViewListenable viewListenable : allListeners) {
			viewListenable.viewChangeDepartmentHours(depName, workHome, startHour);
		}
	}

	@Override
	public void addedDeparment(String deparmentName) {
		deptList.add(new DeparmentView(deparmentName));
		askCompanyProfit();
	}

	@Override
	public void addedRoleToDeparment(String deparmentName, int roleId) {
		/*
		 * the method been provided with data of the new role even though we arent using it in this view class.
		 * we chose to keep it to make the method more general and to proide the option that some view class might use the data provided.
		 */
		updateProfit();
	}

	@Override
	public void addedEmployee(String deparmentName, int roleId, int employeeId) {
		/*
		 * the method been provided with data of the new role even though we arent using it in this view class.
		 * we chose to keep it to make the method more general and to proide the option that some view class might use the data provided.
		 */

		updateProfit();
	}
	
	@Override
	public void showMsg(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void showError(String errorMsg) {
		JOptionPane.showMessageDialog(null, errorMsg, "Failure", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void saveCompanyData() {
		for (ViewListenable viewListenable : allListeners) {
			viewListenable.viewAskSave();
		}
	}
}
