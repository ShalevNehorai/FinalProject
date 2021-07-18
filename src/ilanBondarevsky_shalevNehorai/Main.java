package ilanBondarevsky_shalevNehorai;
	
import ilanBondarevsky_shalevNehorai.application.MainWindow;
import ilanBondarevsky_shalevNehorai.controller.Controller;
import ilanBondarevsky_shalevNehorai.logic.Company;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {	
		Company com = new Company("The company");
		MainWindow view = new MainWindow(primaryStage);
		Controller controller = new Controller(com, view);
		com.readBinaryFile();
		view.updateData();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
