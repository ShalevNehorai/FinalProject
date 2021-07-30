package ilanBondarevsky_shalevNehorai.application;

import javafx.scene.control.Button;

public class DefaultButton extends Button {

	public DefaultButton() {
		super();
		setStyle();
	}

	public DefaultButton(String text) {
		super(text);
		setStyle();
	}
	
	private void setStyle() {
		super.getStyleClass().add("defaultBtn");
	}
}
