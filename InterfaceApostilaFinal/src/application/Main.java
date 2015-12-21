package application;
	
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {
	public static LoginController login = new LoginController();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("login.fxml"));
			initTransition(root);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login Apostilas IF");
			primaryStage.show();
			login.stage = primaryStage;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initTransition(AnchorPane stage) {
		FadeTransition transition = new FadeTransition(
		Duration.millis(4000), stage);
		transition.setFromValue(0.0);
		transition.setToValue(1.0);
		transition.play();
		}
	
	
	public static LoginController getLogin() {
		return login;
	}




	public void setLogin(LoginController login) {
		Main.login = login;
	}




	public static void main(String[] args) {
		launch(args);
	}
}
