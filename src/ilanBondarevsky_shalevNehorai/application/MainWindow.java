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
		depListView.setCellFactory(new Callback<ListView<DeparmentView>, ListCell<DeparmentView>>() {
			@Override
			public ListCell<DeparmentView> call(ListView<DeparmentView> listView) {
				return new DeparmentViewCell(mainWindow);
			}
		});
		
		root.setCenter(depListView);
		BorderPane.setMargin(depListView, new Insets(10));

			//END @CENTER
		
			//@BUTTOM
		Button addDeptButton = new DefaultButton("Add Deparmetn");
		addDeptButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				//TODO open add department window and from there adding deparment
				
				Random rnd = new Random();
				String[] names = {"Cormac Millington", "Kean Guevara", "Giacomo Mcdaniel", "Pearce Terry", "Dorian Timms"};
				addDepartment(names[rnd.nextInt(names.length)] + rnd.nextInt(), rnd.nextBoolean(), rnd.nextBoolean());				
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
	
	public void updateData() {
		askCompanyName();
		askCompanyProfit();
		
		askDeparmnetNames();
	}
	
	@Override
	public void registerListener(ViewListenable l) {
		allListeners.add(l);
	}

	@Override
	public void askCompanyName() {
		String name = allListeners.get(0).askCompanyName();
		companyNameLbl.setText(name);
	}

	@Override
	public void askCompanyProfit() {
		double profit = allListeners.get(0).askCompanyProfit();
		
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
		
		ArrayList<String> list = allListeners.get(0).askDeparmentsNames();
		
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
		return allListeners.get(0).isDepartmentSync(departmentName);
	}

	@Override
	public boolean isDepatmentChangeable(String departmentName) {
		return allListeners.get(0).isDepartmentChangeable(departmentName);
	}

	@Override
	public double askDeparmentProfit(String deparmentName) {
		return allListeners.get(0).askDepartmentProfit(deparmentName);
	}

	@Override
	public ArrayList<Integer> askRolesInDeparment(String deparmentName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String askRoleName(String departmentName, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String askRoleEmployeeName(String deparmentName, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double askEmployeeProfit(String deparmentName, int roleId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isRoleSync(String deparmentName, int roleId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRoleChangeable(String departmentName, int roleId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String askRoleData(String deparmentName, int roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addDepartment(String name, boolean isSync, boolean isChangeable) {
		for (ViewListenable viewListenable : allListeners) {
			viewListenable.addDepartment(name, isSync, isChangeable);
		}
	}

	@Override
	public void addRole(String deparmentName, String roleName, boolean isRoleChangeable) {
		for (ViewListenable viewListenable : allListeners) {
			viewListenable.addRole(deparmentName, roleName, isRoleChangeable);
		}

	}

	@Override
	public void addEpmloyee(String deparmentName, int roleId, String employeeName, EmployeeType type,
			int preferWorkingRime, boolean prefWorkingHome, int salary, double monthlyPersentage, int monthlySales) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeEmployeePersentage(String deparmentName, int roleId, String employeeName, double percentage,
			double monthlySales) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addedDeparment(String deparmentName) {
		if(deparmentName != null && !deparmentName.isBlank()) {
			deptList.add(new DeparmentView(deparmentName));
		}
	}

	@Override
	public void addedRoleToDeparment(String deparmentName, int roleId) {
		// TODO Auto-generated method stub
		for (DeparmentView deparmentView : depListView.getItems()) {
			if(deparmentView.getName().equals(deparmentName)) {
				deparmentView.addRoleId(roleId);
			}
		}
	}

	@Override
	public void addedEmployee(String deparmentName, int roleId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showMsg(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void showError(String errorMsg) {
		JOptionPane.showMessageDialog(null, errorMsg, "Failure", JOptionPane.ERROR_MESSAGE);
	}

}
