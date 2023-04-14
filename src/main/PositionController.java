package main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PositionController {
	public void showPositionForm() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/PositionForm.fxml"));
		Parent root = loader.load();
		Stage newStage = new Stage();
		newStage.setTitle("Position");
		newStage.setScene(new Scene(root));
		newStage.show();

	}
	 @FXML
	 private void addPosition(ActionEvent event) throws IOException {
		 	showPositionForm();
	    }
	 @FXML
	 private void editPosition(ActionEvent event) throws IOException {
		 	showPositionForm();
	    }
}
