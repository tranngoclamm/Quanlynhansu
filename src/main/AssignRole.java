package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AssignRole {
	@FXML
	private ComboBox<String> assignRoleComboBox;
	@FXML
	private Button saveButton;
	@FXML
	private Button assignRoleButton;
	List<Position> positionList = new ArrayList<>();
	List<String> positionNameList = new ArrayList<>();
	private Employee selectedEmployee;

	public void initialize() {
		getPosition();
	}
	  public AssignRole() {
	    }
	    
	  
	  public void setMyVariable(Employee selectedEmployee) {
	        this.selectedEmployee = selectedEmployee;
	    }
	

	public void getPosition() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");

			String sql = "SELECT * FROM data.positiondata";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String chucVu = rs.getString("chuc_vu");
				double heSoLuong = rs.getDouble("he_so_luong");
				Position position = new Position(chucVu, heSoLuong);
				positionNameList.add(chucVu);
				positionList.add(position);
			}
			ObservableList<String> chucVuObservableList = FXCollections.observableArrayList(positionNameList);
			assignRoleComboBox.setItems(chucVuObservableList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

		@FXML
		public void handleSaveAssignRole () throws IOException {
				 Alert alert;
					if (selectedEmployee == null) {
				 		alert = new Alert(AlertType.ERROR, "Bạn chưa chọn nhân viên để gán chức vụ.");
					    alert.showAndWait();
					    return;
				 	}
				 String selectedValue = assignRoleComboBox.getValue();
				 String position = selectedEmployee.getPosition();
				 System.out.println(selectedEmployee.getPosition());

				 Double coeff = selectedEmployee.getSalaryCoefficient();
				 int workday = selectedEmployee.getWorkday();
				 String id = selectedEmployee.getId();
				 Double salary = 0.0;
				 
				    if (selectedValue == null || selectedValue.isEmpty()) {
				        alert = new Alert(AlertType.ERROR, "Bạn chưa chọn chức vụ.");
				        alert.showAndWait();
				        return;
				    }
				    if (position.contains(selectedValue)) {
				    	alert = new Alert(AlertType.ERROR, "Chức vụ đã tồn tại!");
				        alert.showAndWait();
				        return;
				    }
					try {
					    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");
					    String sql = "UPDATE employeedata SET chuc_vu = ?, he_so_luong = ?, tong_luong = ? WHERE id = ?";
				        PreparedStatement stmt = conn.prepareStatement(sql);
				        if (position == null || position.isEmpty()) {
				        	stmt.setString(1, selectedValue);
				        	 for (Position p : positionList) { 
					                if (p.getPositionName().equals(selectedValue)) {
					                    coeff = p.getSalaryCoefficient();
					                }
					            }
				        } else {
				            stmt.setString(1, position + ", " + selectedValue);
				            for (Position p : positionList) {
				                if (p.getPositionName().equals(selectedValue)) {
				                    coeff += p.getSalaryCoefficient();
				                }
				            }
				        }
				        salary = (coeff * 1490000 * workday) / 26;
				        stmt.setDouble(2,  coeff);
				        stmt.setDouble(3, salary);
				        stmt.setString(4, id);
				        stmt.executeUpdate();
					    stmt.close();
					    conn.close();
				
					} catch (SQLException s) {
					    s.printStackTrace();
					}
					 Stage stage = (Stage) assignRoleButton.getScene().getWindow();
					 stage.close();
					 EmployeeController.updateEmployee();
		}

}
