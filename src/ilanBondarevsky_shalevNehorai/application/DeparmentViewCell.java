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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DeparmentViewCell extends ListCell<DeparmentView> {
	private MainWindow mainWindow;
	
 	private BorderPane root;
	
	private HBox top;
	private Label name;
	private Label profitText;
	private Button changeHoursBtn;
	private Button addRoleButton;
	
	private ListView<RoleView> roleListView;
	
	public DeparmentViewCell(MainWindow mainWindow) {
		super();
		
		this.mainWindow = mainWindow;
		
		Font topfont = new Font(15);
		root = new BorderPane();
		root.setPadding(new Insets(20));

			//@TOP
		name = new Label();
		name.getStyleClass().add("default-label");
		profitText = new Label();
		profitText.setFont(topfont);
		
		changeHoursBtn = new EditButton("Change hours");
		changeHoursBtn.setTooltip(new Tooltip("change the work hours of all the roles in the deprament"));
		
		top = new HBox(name, profitText, changeHoursBtn);
		top.setSpacing(40);
		
		root.setTop(top);
		BorderPane.setMargin(top, new Insets(5));
			//END @TOP
		
		
			//@BUTTOM
		addRoleButton = new DefaultButton("Add Role");
		
		root.setBottom(addRoleButton);
		BorderPane.setMargin(addRoleButton, new Insets(5));
			//END @BUTTOM
		
	}
	
	@Override
	protected void updateItem(DeparmentView item, boolean empty) {
		super.updateItem(item, empty);
		
		if(item != null && !empty) {
			name.setText(item.getName());
			
			double profit = mainWindow.askDeparmentProfit(item.getName());
			profitText.setText(String.valueOf(profit) + "\u20AA");
			if(profit < 0) {
				profitText.setTextFill(Color.RED);
			}
			else {
				profitText.setTextFill(Color.GREEN);
			}
			
			ObservableList<RoleView> roleList = FXCollections.observableArrayList();
			for(int id : mainWindow.askRolesInDeparment(item.getName())) {
				roleList.add(new RoleView(id, item.getName()));
			}
						
			roleListView = new ListView<RoleView>(roleList);
			roleListView.setSelectionModel(new NoSelectionModel<RoleView>());
			roleListView.setCellFactory(new Callback<ListView<RoleView>, ListCell<RoleView>>() {
				@Override
				public ListCell<RoleView> call(ListView<RoleView> listView) {
					return new RoleViewCell(mainWindow);
				}
			});
			root.setCenter(roleListView);
			roleListView.setPrefSize(50, 400);
			
			/*if(!mainWindow.isDepatmentChangeable(item.getName())) {
				changeHoursBtn.setVisible(false);
			}*/
			changeHoursBtn.setVisible(mainWindow.isDepatmentChangeable(item.getName()));
			
			changeHoursBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					ViewChangeHours change = new ViewChangeHours(mainWindow, new Stage(), item.getName());
				}
			});
			
			addRoleButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					ViewAddRole addRole = new ViewAddRole(mainWindow, item.getName(), new Stage());
				}
			});
			
			setGraphic(root);
		}
	}
}
