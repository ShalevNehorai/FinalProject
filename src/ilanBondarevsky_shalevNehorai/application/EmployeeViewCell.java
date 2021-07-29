package ilanBondarevsky_shalevNehorai.application;

import javax.swing.JOptionPane;

import ilanBondarevsky_shalevNehorai.logic.EmployeeType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EmployeeViewCell extends ListCell<EmployeeView> {
	private MainWindow mainWindow;
	
	private HBox hbox;
	private Label nameLbl;
	private Label profitLbl;
	
	private Button infoBtn;
	private Button editEmployeeBtn;
	
	public EmployeeViewCell(MainWindow mainWindow) {
		super();
		
		this.mainWindow = mainWindow;
		
		hbox = new HBox();
		hbox.setSpacing(40);
		hbox.setAlignment(Pos.CENTER_LEFT);
		
		nameLbl = new Label();
		profitLbl = new Label();
		
		infoBtn = new Button("show info");
		
		editEmployeeBtn = new Button("Edit");
		editEmployeeBtn.setTooltip(new Tooltip("change the monthly persentage and monthly sales"));//TODO chack spelling
		
		hbox.getChildren().addAll(nameLbl, profitLbl, infoBtn, editEmployeeBtn);
	}
	
	@Override
	protected void updateItem(EmployeeView item, boolean empty) {
		super.updateItem(item, empty);
		
		if(item != null && !empty) {
			nameLbl.setText(mainWindow.askEmployeeName(item.getDepartmentName(), item.getRoleId(), item.getEmployeeId()));
			
			double profit = mainWindow.askEmployeeProfit(item.getDepartmentName(), item.getRoleId(), item.getEmployeeId());
			
			profitLbl.setText(String.valueOf(profit) + "\u20AA");
			if(profit < 0) {
				profitLbl.setTextFill(Color.RED);
			}
			else {
				profitLbl.setTextFill(Color.GREEN);
			}
			
			infoBtn.setOnAction(new EventHandler<ActionEvent>() {	
				@Override
				public void handle(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, mainWindow.askEmployeeData(item.getDepartmentName(), item.getRoleId(), item.getEmployeeId()));
				}
			});
			
			editEmployeeBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					ViewEditEmployee edit = new ViewEditEmployee(mainWindow, new Stage(), item.getDepartmentName(), item.getRoleId(), item.getEmployeeId());
				}
			});

			editEmployeeBtn.setVisible(mainWindow.askEmployeeType(item.getDepartmentName(), item.getRoleId(), item.getEmployeeId()) == EmployeeType.PERCENTAGE_EMPLOYEE);
			
			setGraphic(hbox);
		}
	}
}
