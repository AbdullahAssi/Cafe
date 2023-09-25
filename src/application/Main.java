package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("Application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cafe Shop System");
        primaryStage.setResizable(false);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(event -> {
        	event.consume();
        	logout(primaryStage); 
        });
    }
    
	public void logout(Stage primaryStage) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("Your are About to Exit");
		alert.setContentText("Do You want to Save Before Exiting");
		
		if(alert.showAndWait().get() == ButtonType.OK) {			
//			stage = (Stage) sectionPane.getScene().getWindow();
			System.out.println("Logged out");
			primaryStage.close();
		}
	}
}
