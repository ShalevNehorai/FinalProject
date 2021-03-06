package ilanBondarevsky_shalevNehorai.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class RoleViewCell extends ListCell<RoleView> {
	private MainWindow mainWindow;
	
	private BorderPane layout;
		
	private HBox top;
	private Label roleNameLbl;
	private Label profitLbl;
	private Label roleData;
	
	private Button addEmployeeBtn;
	private Button changeHoursBtn;
	
	private ListView<EmployeeView> employeeListView;
	
	public RoleViewCell(MainWindow mainWindow) {
		super();
		
		this.mainWindow = mainWindow;
		
		layout = new BorderPane();
		layout.setPadding(new Insets(20));

		roleNameLbl = new Label();
		roleNameLbl.getStyleClass().add("default-label");
		profitLbl = new Label();
		roleData = new Label();
		
		changeHoursBtn = new EditButton("change hours");
		changeHoursBtn.setTooltip(new Tooltip("change the work hours of all the employees in the role"));
		
		top = new HBox(roleNameLbl, profitLbl, changeHoursBtn, roleData);
		top.setSpacing(20);

		layout.setTop(top);
		BorderPane.setMargin(top, new Insets(5));
		
		addEmployeeBtn = new DefaultButton("Add Employee");
		
		layout.setBottom(addEmployeeBtn);
		BorderPane.setMargin(addEmployeeBtn, new Insets(5));
	}
	 
	@Override
	protected void updateItem(RoleView item, boolean empty) {
		super.updateItem(item, empty);
		
		if(item != null && !empty) {			
			roleNameLbl.setText(mainWindow.askRoleName(item.getDeparmentName(), item.getRoleId()));
			
			double profit = mainWindow.askRoleProfit(item.getDeparmentName(), item.getRoleId());
			
			profitLbl.setText(String.valueOf(profit) + "\u20AA");
			if(profit < 0) {
				profitLbl.setTextFill(Color.RED);
			}
			else {
				profitLbl.setTextFill(Color.GREEN);
			}
			
			roleData.setText(mainWindow.askRoleData(item.getDeparmentName(), item.getRoleId()));
			
			ObservableList<EmployeeView> employeeList = FXCollections.observableArrayList();
			for(int id : mainWindow.askEmployeesInRole(item.getDeparmentName(), item.getRoleId())) {
				employeeList.add(new EmployeeView(item.getDeparmentName(), item.getRoleId(), id));
			}
			
			employeeListView = new ListView<EmployeeView>(employeeList);
			employeeListView.setSelectionModel(new NoSelectionModel<EmployeeView>());
			employeeListView.setCellFactory(new Callback<ListView<EmployeeView>, ListCell<EmployeeView>>() {
				@Override
				public ListCell<EmployeeView> call(ListView<EmployeeView> listView) {
					return new EmployeeViewCell(mainWindow);
				}
			});
			layout.setCenter(employeeListView);
			employeeListView.setPrefSize(50, 150);
			
			boolean canChangeHours = mainWindow.isRoleChangeable(item.getDeparmentName(), item.getRoleId()) && !mainWindow.isRoleSync(item.getDeparmentName(), item.getRoleId());
			
			changeHoursBtn.setVisible(canChangeHours);
						
			addEmployeeBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					new ViewAddEmployee(mainWindow, item.getDeparmentName(), item.getRoleId(), new Stage());					
				}
			});
			
			changeHoursBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					new ViewChangeHours(mainWindow, new Stage(), item.getDeparmentName(), item.getRoleId());
				}
			});
			
			setGraphic(layout);
		}
	}
}
