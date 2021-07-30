package ilanBondarevsky_shalevNehorai.application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class Util {
	public static void setTextFieldNumbersOnly(TextField tf) {
		tf.setPromptText("numbers only");
		tf.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	tf.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
	}
}
