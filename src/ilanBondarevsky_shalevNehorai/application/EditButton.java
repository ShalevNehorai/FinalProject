package ilanBondarevsky_shalevNehorai.application;

import javafx.scene.Node;
import javafx.scene.control.Button;

public class EditButton extends Button {

	public EditButton() {
		super();
		setStyle();
	}

	public EditButton(String arg0, Node arg1) {
		super(arg0, arg1);
		setStyle();
	}

	public EditButton(String arg0) {
		super(arg0);
		setStyle();
	}
	
	private void setStyle() {
		super.getStyleClass().add("hourBtn");
	}
 
}
