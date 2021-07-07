package ilanBondarevsky_shalevNehorai;
	
import ilanBondarevsky_shalevNehorai.application.MainWindow;
import ilanBondarevsky_shalevNehorai.controller.Controller;
import ilanBondarevsky_shalevNehorai.logic.Company;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		/*try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}*/
		
		Company com = new Company("The company");
		MainWindow view = new MainWindow(primaryStage);
		Controller controller = new Controller(com, view);
		view.updateData();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
