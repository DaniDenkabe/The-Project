import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;


public class EffectsView extends Application {
	private GridPane main = new GridPane();	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene =  new Scene(main, 800, 800);
	}
	
}
