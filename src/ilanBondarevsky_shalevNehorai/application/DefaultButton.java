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
		super.setStyle("-fx-background-color: #1C7EE7; -fx-text-fill: #ffffff; -fx-font-size: 20px; padding: 10px 20px 10px 20px");
	}

	
}
