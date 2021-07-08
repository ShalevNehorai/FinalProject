package ilanBondarevsky_shalevNehorai.application;

import java.util.ArrayList;
import java.util.Random;

import javax.management.relation.RoleList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.SetChangeListener;
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
import javafx.scene.text.Text;
import javafx.util.Callback;

public class DeparmentViewCell extends ListCell<DeparmentView> {
	private MainWindow mainWindow;
	
 	private BorderPane root;
	
	private HBox top;
	private Label name;
	private Label profitText;
	private Button changeHoursBtn;
	private Button addRoleButton;
	
//	private ObservableList<RoleView> roleList;
	private ListView<RoleView> roleListView;
	
	public DeparmentViewCell(MainWindow mainWindow) {
		super();
		
		this.mainWindow = mainWindow;
		
		Font topfont = new Font(15);
		root = new BorderPane();
		root.setPadding(new Insets(20));

			//@TOP
		name = new Label();
		name.setFont(topfont);
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
	
	private void setChagngBtnState(String name) {
		boolean isVisiable = mainWindow.isDepatmentChangeable(name);
		changeHoursBtn.setVisible(isVisiable);
	}
	
	private void addRole(String deptName, String roleName, boolean isRoleChangeable) {
		mainWindow.addRole(deptName, roleName, isRoleChangeable);
	}
	
	@Override
	protected void updateItem(DeparmentView item, boolean empty) {
		super.updateItem(item, empty);
		
		if(item != null && !empty) {
			name.setText(item.getName());
			
			double profit = mainWindow.askDeparmentProfit(item.getName());
			profitText.setText(String.valueOf(profit));
			if(profit < 0) {
				profitText.setTextFill(Color.RED);
			}
			else {
				profitText.setTextFill(Color.GREEN);
			}
			
			roleListView = new ListView<RoleView>(item.getRoleList());
			roleListView.setCellFactory(new Callback<ListView<RoleView>, ListCell<RoleView>>() {
				@Override
				public ListCell<RoleView> call(ListView<RoleView> listView) {
					return new RoleViewCell(mainWindow);
				}
			});
			root.setCenter(roleListView);
			roleListView.setPrefSize(50, 200);
			
			changeHoursBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					
				}
			});
			
			addRoleButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					//TODO open add department window and from there adding role
					
					Random rnd = new Random();
					String[] names = {"Stockbroker", "Computer analyst", "Paramedic", "Editor", "Probation officer", "Psychologist", "Lighthouse keeper"};
					addRole(item.getName(), names[rnd.nextInt(names.length)], rnd.nextBoolean());				
				}
			});
			
			setChagngBtnState(item.getName());
			setGraphic(root);
		}
	}
}
