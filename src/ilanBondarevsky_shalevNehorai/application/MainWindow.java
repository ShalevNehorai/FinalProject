package ilanBondarevsky_shalevNehorai.application;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainWindow implements CompanyViewable {
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
		/*depListView.setCellFactory(new Callback<ListView<DeparmentView>, ListCell<DeparmentView>>() {
			@Override
			public ListCell<DeparmentView> call(ListView<DeparmentView> listView) {
				return new DeparmentViewCell(mainWindow);
			}
		});*/
		updateDepmtList();
		
		root.setCenter(depListView);
		BorderPane.setMargin(depListView, new Insets(10));

			//END @CENTER
		
			//@BUTTOM
		Button addDeptButton = new DefaultButton("Add Deparment");
		addDeptButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				//TODO open add department window and from there adding deparment
				
				Random rnd = new Random();
				String[] names = {"Cormac Millington", "Kean Guevara", "Giacomo Mcdaniel", "Pearce Terry", "Dorian Timms"};
//				addDepartment(names[rnd.nextInt(names.length)] + rnd.nextInt(), rnd.nextBoolean(), rnd.nextBoolean());	
				
				AddDepartment addDept = new AddDepartment(mainWindow, companyNameLbl.getText(), new Stage());
			}
		});
		
		root.setBottom(addDeptButton);
		BorderPane.setMargin(addDeptButton, new Insets(10));

			//END @BUTTOM
		
		
		Scene scene = new Scene(root, 1280, 720);
//		scene.getStylesheets().add("D:\\Shalev\\OneDrive - Afeka College Of Engineering\\codeProjects\\OOPLessones\\FinalProject\\src\\ilanBondarevsky_shalevNehorai\\application.css");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	@Override
	public void updateData() {
		askCompanyName();
		askCompanyProfit();
		askDeparmnetNames();
	}
	
	@Override
	public void updateProfit() {
		askCompanyProfit();
		updateDepmtList();
	}
	
	public void updateDepmtList() {
		depListView.setCellFactory(new Callback<ListView<DeparmentView>, ListCell<DeparmentView>>() {
			@Override
			public ListCell<DeparmentView> call(ListView<DeparmentView> listView) {
				return new DeparmentViewCell(mainWindow);
			}
		});
	}
	
	@Override
	public void registerListener(ViewListenable l) {
		allListeners.add(l);
	}

	@Override
	public void askCompanyName() {
		String name = allListeners.get(0).viewAskCompanyName();
		companyNameLbl.setText(name);
	}

	@Override
	public void askCompanyProfit() {
		double profit = allListeners.get(0).viewAskCompanyProfit();
		
		companyProfitLbl.setText("profit: " + String.valueOf(profit));
		
		if(profit < 0) {
			companyProfitLbl.setTextFill(Color.RED);
		}
		else {
			companyProfitLbl.setTextFill(Color.GREEN);
		}
	}

	@Override
	public void askDeparmnetNames() {
//		deptList.clear();
//		depListView.getItems().clear();
		
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
		return allListeners.get(0).viewAskRoleIdInDepartment(deparmentName);
	}

	@Override
	public String askRoleName(String departmentName, int id) {
		return allListeners.get(0).viewAskRoleName(departmentName, id);
	}

	@Override
	public String askRoleEmployeeName(String deparmentName, int id) {
		return allListeners.get(0).viewAskRoleEmployeeName(deparmentName, id);
	}

	@Override
	public double askEmployeeProfit(String deparmentName, int roleId) {
		return allListeners.get(0).viewAskEmployeeProfit(deparmentName, roleId);
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
	public void changeEmployeePercentageData(String deparmentName, int roleId, double percentage, int monthlySales) {
		for (ViewListenable viewListenable : allListeners) {
			viewListenable.viewChangeEmployeePersentageData(deparmentName, roleId, percentage, monthlySales);
		}
	}

	@Override
	public void addedDeparment(String deparmentName) {
		deptList.add(new DeparmentView(deparmentName));
		askCompanyProfit();
	}

	@Override
	public void addedRoleToDeparment(String deparmentName, int roleId) {
       /* for (DeparmentView deparmentView : depListView.getItems()) {
            if(deparmentView.getName().equals(deparmentName)) {
                deparmentView.addRoleId(roleId);
            }
        }*/
		updateDepmtList();
	}

	@Override
	public void addedEmployee(String deparmentName, int roleId) {
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
	public EmployeeType askEmployeeType(String deparmentName, int roleId) {
		return allListeners.get(0).viewAskEmployeeType(deparmentName, roleId);
	}
	
	@Override
	public void changeDepartmentHours(String depName, boolean workHome, int startHour) {
		for (ViewListenable viewListenable : allListeners) {
			viewListenable.viewChangeDepartmentHours(depName, workHome, startHour);
		}
	}
	@Override
	public void changeRoleHour(String depName, int roleId, boolean workFromHome, int startHour) {
		for (ViewListenable viewListenable : allListeners) {
			viewListenable.viewChangeRoleHour(depName, roleId, workFromHome, startHour);
		}
	}
	
	@Override
	public int askEmployeeMonthlySales(String depName, int roleId) {
		int temp = allListeners.get(0).viewAskEmployeeMonthlySales(depName, roleId);
		if (temp != -1)// TODO not necessary, we can just return 0 in the model
			return temp;
		return 0;
	}
	
	@Override
	public double askEmployeePercentage(String depName, int roleId) {
		double temp = allListeners.get(0).viewAskEmployeePercentage(depName, roleId);
		if (temp != -1)// TODO not necessary, we can just return 0 in the model
			return temp;
		return 0;
	}
}
