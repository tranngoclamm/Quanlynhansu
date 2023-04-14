package main;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private Button login;
	@FXML
	private Label loginErrorLabel;

	public void initialize() {
		login.setOnAction(event -> {
			String username = usernameTextField.getText();
			String password = passwordTextField.getText();
			if (username.equals("admin") && password.equals("12345")) {
				try {
					Stage stage = (Stage) login.getScene().getWindow();
					stage.close();
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/Main.fxml"));
					Parent root = loader.load();
					Stage primaryStage = new Stage();
					primaryStage.setTitle("EmployeeManagementSystem");
					primaryStage.setScene(new Scene(root));
					primaryStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				loginErrorLabel.setText("Invalid Login. Please try again.");
			}
		});
	}
	@FXML
	private void forgetPassword(ActionEvent event) {
	    String url = "https://forms.gle/s3QGV1CXBNgSDXaZ6";
	    try {
	        Desktop.getDesktop().browse(new URI(url));
	    } catch (IOException | URISyntaxException e) {
	        e.printStackTrace();
	    }
	}
}