package main;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EmployeeController {
		@FXML
		private AnchorPane baseView;
		@FXML
		private AnchorPane employeeView;

		public void showEmployeeForm() throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/EmployeeForm.fxml"));
			Parent root = loader.load();
			Stage newStage = new Stage();
			newStage.setTitle("Employee");
			newStage.setScene(new Scene(root));
			newStage.show();

		}
		 @FXML
		 private void addEmployee(ActionEvent event) throws IOException {
		        showEmployeeForm();
		    }
		 @FXML
		 private void editEmployee(ActionEvent event) throws IOException {
		        showEmployeeForm();
		    }
		 @FXML
		 private void switchTable(ActionEvent event) throws IOException {
			    Button clickedButton = (Button) event.getSource();
			    String clickedName = clickedButton.getText();
			    Button currentButton= (Button) event.getSource();
			    String currentName = currentButton.getText();
			    if(currentName.equals(clickedName) && clickedButton.getText().contains("QUẢN LÝ CHỨC VỤ")) {
			    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/PositionView.fxml"));
			    	 AnchorPane positionView = loader.load();
			    	 baseView.getChildren().setAll(positionView);
			    }
			    if(currentName.equals(clickedName) && clickedButton.getText().contains("QUẢN LÝ NHÂN VIÊN")) {			    	
			    	 baseView.getChildren().setAll(employeeView);


			    	}
		    }
		 @FXML
		 private void logout(ActionEvent event) {
		     Alert alert = new Alert(AlertType.CONFIRMATION);
		     alert.setTitle("Logout");
		     alert.setHeaderText("Bạn có muốn thoát không?");
		     Optional<ButtonType> result = alert.showAndWait();
		     if (result.get() == ButtonType.OK){
		         try {
		        	   Stage currentStage = (Stage) baseView.getScene().getWindow();
			             currentStage.close();
		             FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/Login.fxml"));
		             Parent root = (Parent) loader.load();
		             Stage stage = new Stage();
		             stage.setScene(new Scene(root));
		             stage.show();
		          
		         } catch (IOException e) {
		             e.printStackTrace();
		         }
		     }
		 }

		
		}
