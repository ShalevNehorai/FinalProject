package ilanBondarevsky_shalevNehorai;
	
import ilanBondarevsky_shalevNehorai.application.MainWindow;
import ilanBondarevsky_shalevNehorai.controller.Controller;
import ilanBondarevsky_shalevNehorai.logic.Company;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {
	@Override
	public void start(Stage primaryStage) {	
		Company com = new Company("The company");
		try {
			com.readBinaryFile();
		}
		catch(Exception e) {
			com.createStartingData();
		}
		MainWindow view = new MainWindow(primaryStage);
		Controller controller = new Controller(com, view);
		view.initViewData();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
