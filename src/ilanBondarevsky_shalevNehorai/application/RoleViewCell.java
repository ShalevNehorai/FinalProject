package ilanBondarevsky_shalevNehorai.application;

import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class RoleViewCell extends ListCell<RoleView> {
	private MainWindow mainWindow;
	
	private Text roleText;
	private BorderPane root;

	public RoleViewCell(MainWindow mainWindow) {
		super();
		
		this.mainWindow = mainWindow;
		
		roleText = new Text();
		root = new BorderPane();
		
		root.setTop(roleText);
		
	}
	 
	
	@Override
	protected void updateItem(RoleView item, boolean empty) {
		// TODO Auto-generated method stub
		super.updateItem(item, empty);
		
		if(item != null && !empty) {
			roleText.setText(String.valueOf(item.getRoleId()));
			
			setGraphic(root);
		}
	}
}
