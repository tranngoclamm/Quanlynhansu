package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PositionControllerFunctions {
	@FXML
	private TextField salaryCoefficientTextField;
    @FXML
    private Button saveButton;
    @FXML
    private TextField positionNameTextField;

    /**
     * xác định chức năng thêm / sửa
     */
	private Position selectedPosition;
	String title="";


	  public PositionControllerFunctions() {
	    }
	    
	  
	  public void setData(Position selectedPosition, String title) {
	        this.selectedPosition = selectedPosition;
	        this.title=title;
	        initialize();
	    }
	
    public void initialize() {
    	setPositionInForm();
  }
    public void setPositionInForm() {
    	if (title.equals("Edit")) {
    	positionNameTextField.setText(selectedPosition.getPositionName());
    	salaryCoefficientTextField.setText(Double.toString(selectedPosition.getSalaryCoefficient()));
    	}
    }
    
    
    public void addPosition() {
    	Alert alert;
    	String positionName = positionNameTextField.getText();
    	try {
    	Double salaryCoefficient = Double.parseDouble(salaryCoefficientTextField.getText());
    	} catch (NumberFormatException e) {
    	alert = new Alert(AlertType.ERROR, "Hệ số lương không đúng.");
    	alert.showAndWait();
    	return;
    	}
    	if (positionName.trim().isEmpty()) {
    	alert = new Alert(AlertType.ERROR, "Bạn chưa nhập đủ thông tin");
    	alert.showAndWait();
    	return;
    	}
    	try {
    	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");
    	String sql = "SELECT * FROM data.positiondata WHERE chuc_vu = ?";
    	PreparedStatement stmt = conn.prepareStatement(sql);
    	stmt.setString(1, positionName);
    	ResultSet rs = stmt.executeQuery();
    	if (rs.next()) {
    	alert = new Alert(AlertType.ERROR, "Chức vụ đã tồn tại!");
    	alert.showAndWait();
    	return;
    	}
    	rs.close();
    	stmt.close();
    	sql = "INSERT INTO positiondata (chuc_vu, he_so_luong) VALUES (?, ?)";
    	PreparedStatement insertStmt = conn.prepareStatement(sql);
    	insertStmt.setString(1, positionName);
    	Double salaryCoefficient = Double.parseDouble(salaryCoefficientTextField.getText());
    	insertStmt.setDouble(2, salaryCoefficient);
    	insertStmt.executeUpdate();
    	insertStmt.close();
    	conn.close();
    	} catch (SQLException s) {
    	s.printStackTrace();
    	}
    	Stage stage = (Stage) saveButton.getScene().getWindow();
    	stage.close();
    	PositionController.updatePosition();
    	}
    
    public void editPosition() throws SQLException {
    	Alert alert;
    	String positionNameUpdate = positionNameTextField.getText();
    	Double salaryCoefficientUpdate=Double.parseDouble(salaryCoefficientTextField.getText());
    	try {
        	Double salaryCoefficient = Double.parseDouble(salaryCoefficientTextField.getText());
        } catch (NumberFormatException e) {
        	alert = new Alert(AlertType.ERROR, "Hệ số lương không đúng.");
        	alert.showAndWait();
        	return;
        }
    	if (positionNameUpdate.equals(selectedPosition.getPositionName()) && salaryCoefficientUpdate.equals(selectedPosition.getSalaryCoefficient())) {
    		    alert = new Alert(AlertType.INFORMATION, "Bạn chưa sửa thông tin!");
    		    alert.showAndWait();
    		    return;
    		}
    	if(positionNameUpdate == null || positionNameUpdate.trim().isEmpty() 
    			||salaryCoefficientUpdate == null) {
    		 alert = new Alert(AlertType.ERROR, "Bạn chưa nhập chức vụ!");
		     alert.showAndWait();
		     return;
    	}
    	
    	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");
    	String sql = "SELECT * FROM data.positiondata WHERE chuc_vu = ? AND chuc_vu != ?";
    	PreparedStatement stmt = conn.prepareStatement(sql);
    	stmt.setString(1, positionNameUpdate);
    	stmt.setString(2, selectedPosition.getPositionName());
    	ResultSet rs = stmt.executeQuery();
    	if (rs.next()) {
    		alert = new Alert(AlertType.ERROR, "Trùng tên chức vụ!");
    		alert.showAndWait();
    		return;	
    	}
    	try {
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");
		    sql = "UPDATE data.positiondata SET chuc_vu = ?, he_so_luong = ? WHERE chuc_vu = ?";
		    stmt = conn.prepareStatement(sql);
		    stmt.setString(1, positionNameUpdate);
		    stmt.setDouble(2, salaryCoefficientUpdate );
		    stmt.setString(3, selectedPosition.getPositionName());
		    stmt.executeUpdate();
		    stmt.close();
		    conn.close();
		} catch (SQLException s) {
		    s.printStackTrace();
		}
		 Stage stage = (Stage) saveButton.getScene().getWindow();
		 stage.close();
		 PositionController.updatePosition(); 	
    }
    
//	@FXML
	 public void handleSaveButton(ActionEvent e) throws SQLException {
		 if (title.equals("Add")) {
			 addPosition();
		 } else if (title.equals("Edit")) {
			 editPosition();
		 }
		 
		}
	}
