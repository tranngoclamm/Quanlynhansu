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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class EmployeeCon {
	@FXML
	private TextField idTextField;
	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;
	@FXML
	private TextField birthdayTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField workdayTextField;
	@FXML
	private TextField noteTextField;
	@FXML
	private ComboBox<String> positionComboBox;
	@FXML
	private ComboBox<String> assignRoleComboBox;
	@FXML
	private RadioButton maleRadioButton;
	@FXML
	private RadioButton femaleRadioButton;
	@FXML
	private ToggleGroup genderToggleGroup;
	@FXML
	public TextField searchTextField;
	@FXML
	private Button saveButton;
	@FXML
	private Button assignRoleButton;

//	private ObservableList<Employee> employeeList;
	List<Position> positionList = new ArrayList<>();
	List<String> positionNameList = new ArrayList<>();
	Double salaryCoefficient = 0.0;
	private Employee selectedEmployee;
	String title = "";
	
	public EmployeeCon() {
	}

	public void setData(Employee selectedEmployee, String title) {
		this.selectedEmployee = selectedEmployee;
		this.title = title;
		setPosition();
		setEmployeeInForm();
		initialize();
	}

	public void initialize() {
	}

	public void setPosition() {
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
			positionComboBox.setItems(chucVuObservableList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setEmployeeInForm() {
		if (title.equals("Edit")) {
			String fullName = selectedEmployee.getName().trim();
			int spaceIndex = fullName.lastIndexOf(" "); // tìm vị trí của khoảng trắng cuối cùng trong chuỗi
			String firstName = fullName.substring(0, spaceIndex).trim(); // lấy phần đầu tiên của chuỗi là first name và loại bỏ khoảng trắng
			String lastName = fullName.substring(spaceIndex + 1).trim(); // lấy phần còn lại của chuỗi là last name và loại bỏ khoảng trắng
			idTextField.setText(selectedEmployee.getId());
			firstNameTextField.setText(firstName);
			lastNameTextField.setText(lastName);
			birthdayTextField.setText(selectedEmployee.getBirthday());
			emailTextField.setText(selectedEmployee.getEmail());
			positionComboBox.setValue(selectedEmployee.getPosition());
			workdayTextField.setText(String.valueOf(selectedEmployee.getWorkday()));
//			Toggle selectedToggle = genderToggleGroup.getSelectedToggle();
			if(selectedEmployee.getGender().equals("Male")) {
				genderToggleGroup.selectToggle(maleRadioButton);
			} else if (selectedEmployee.getGender().equals("Female")) {
				genderToggleGroup.selectToggle(femaleRadioButton);
			}
	    	noteTextField.setText(selectedEmployee.getNote());
		}
	}

	public void checkInput() throws IOException  {
		Alert alert;
		String id = idTextField.getText();
		String fullname = firstNameTextField.getText() + " " + lastNameTextField.getText();
		String email = emailTextField.getText();
		String birthday = birthdayTextField.getText();
		String workdayStr = workdayTextField.getText();
		int workday = 0;
		
		if (id.trim().isEmpty() || fullname.trim().isEmpty() || email.trim().isEmpty() || birthday.trim().isEmpty()) {
			alert = new Alert(AlertType.ERROR, "Bạn chưa nhập đủ thông tin");
			alert.showAndWait();
			return;
		}
		try {
		    workday = Integer.parseInt(workdayStr);
		} catch (NumberFormatException e) {
			alert = new Alert(AlertType.ERROR, "Sai định dạng ngày. Vui lòng nhập lại!");
			alert.showAndWait();
			return;
		}
		if (workdayStr.matches("[0-9]{1,2}")) {
			int workdayTemp = Integer.parseInt(workdayStr);
			if (workdayTemp >= 0 && workdayTemp <= 31) {
				workday = workdayTemp;
			} else {
			    alert = new Alert(AlertType.ERROR, "Bạn nhập sai ngày. Vui lòng nhập lại!");
				alert.showAndWait();
		        return;
			}
		} else {
			alert = new Alert(AlertType.ERROR, "Bạn nhập sai ngày. Vui lòng nhập lại!");
			alert.show();
			return;
		}
		String regex = "^[a-zA-Z ]+$"; // Chỉ chấp nhận ký tự chữ cái và dấu cách
		if (!fullname.matches(regex)) {
			alert = new Alert(AlertType.ERROR, "Tên chỉ bao gồm chữ cái!");
			alert.showAndWait();
			return;
		}
		regex = ".*@gmail\\.com$"; // Chỉ chấp nhận địa chỉ email kết thúc bằng "@gmail.com"
		if (!email.matches(regex)) {
			alert = new Alert(AlertType.ERROR, "Email sai định dạng");
			alert.showAndWait();
			return;
		}
	}

	
	
//	private void addEmployee() throws IOException {
//		  
//		Alert alert;
//		String id = idTextField.getText();
//		String fullname = firstNameTextField.getText() + " " + lastNameTextField.getText();
//		String email = emailTextField.getText();
//		String birthday = birthdayTextField.getText();
//		String workdayStr = workdayTextField.getText();
//		String note = noteTextField.getText();
//		int workday = -1;
//		if (workdayStr.matches("[0-9]{1,2}")) {
//		     int workdayTemp = Integer.parseInt(workdayStr);
//		     if (workdayTemp >= 0 && workdayTemp <= 31) {
//		    	 workday = workdayTemp;
//		     } else {
//		         alert = new Alert(AlertType.ERROR, "Bạn nhập sai ngày. Vui lòng nhập lại!");
//		         alert.showAndWait();
//		         return;
//		        }
//		 } else {
//		        alert = new Alert(AlertType.ERROR, "Bạn nhập sai ngày. Vui lòng nhập lại!");
//		        alert.showAndWait();
//		        return;
//		    }
//		workday = Integer.parseInt(workdayStr);
//		Toggle selectedToggle = genderToggleGroup.getSelectedToggle();
//		String gender = ((RadioButton) selectedToggle).getText();
//		System.out.println(gender);
//		String position = "";
//		if (positionComboBox != null && positionComboBox.getValue() != null) {
//		    position = positionComboBox.getValue().toString();
//		}
//	
//
//		if (id.trim().isEmpty() || fullname.trim().isEmpty() || email.trim().isEmpty() || birthday.trim().isEmpty() || gender.isEmpty()) {
//		    alert = new Alert(AlertType.ERROR, "Bạn chưa nhập đủ thông tin");
//		    alert.showAndWait();
//		    return;
//		}
//
//		try {
//		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");
//		    String sql = "SELECT * FROM data.employeedata WHERE id = ?"; 	// Kiểm tra trùng mã nhân viên
//		    PreparedStatement stmt = conn.prepareStatement(sql);
//		    stmt.setString(1, id);
//		    ResultSet rs = stmt.executeQuery();
//		    if (rs.next()) {
//		        alert = new Alert(AlertType.ERROR, "Nhân viên đã tồn tại!");
//		        alert.showAndWait();
//		        return; 
//		    }
//		    rs.close();
//		    stmt.close();
//
//		    // Thêm mới nhân viên
//		    sql = "INSERT INTO employeedata (id, ho_ten, ngay_sinh, gioi_tinh, email, chuc_vu, he_so_luong, ngay_lam_viec, tong_luong, ghi_chu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//		    PreparedStatement insertStmt = conn.prepareStatement(sql);
//		    insertStmt.setString(1, id);
//		    insertStmt.setString(2, fullname);
//		    insertStmt.setString(3, birthday);
//		    insertStmt.setString(4, gender);
//		    insertStmt.setString(5, email);
//		    
//		    insertStmt.setString(6, position);
//			for (Position p : positionList) {
//		        if (p.getPositionName().equals(position)) {
//		            salaryCoefficient = p.getSalaryCoefficient();
//		        }
//			}
//		    insertStmt.setDouble(7, salaryCoefficient);
//		    insertStmt.setInt(8, workday);
//		    double tongLuong = (salaryCoefficient * 1490000 * workday) / 26; 	// Lấy lương cơ bản = 1,490,000
//		    insertStmt.setDouble(9, tongLuong);
//		    insertStmt.setString(10, note);
//		    insertStmt.executeUpdate();
//		    insertStmt.close();
//		    conn.close();
//		} catch (SQLException s) {
//		    s.printStackTrace();
//		}
//		 Stage stage = (Stage) saveButton.getScene().getWindow();
//		 stage.close();
//		 EmployeeController.updateEmployee();
//	}
	
	

	@FXML
	private void handleSaveButton(ActionEvent event) throws IOException {
		if (title.equals("Add")) {
//			checkInput();
			addEmployee();
		} else if (title.equals("Edit")) {
//			checkInput();
			editEmployee();
		}

	}

	public void addEmployee() throws IOException {
//		checkInput();
		String id = idTextField.getText();
		String fullname = firstNameTextField.getText() + " " + lastNameTextField.getText();
		String email = emailTextField.getText();
		String birthday = birthdayTextField.getText();
		String workdayStr = workdayTextField.getText();
		int workday = 0;
		Toggle selectedToggle = genderToggleGroup.getSelectedToggle();
		String gender = ((RadioButton) selectedToggle).getText();
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");
			// Kiểm tra trùng mã nhân viên
			String sql = "SELECT * FROM data.employeedata WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Alert alert;
				alert = new Alert(AlertType.ERROR, "Nhân viên đã tồn tại!");
				alert.showAndWait();
				return; 
			}
			rs.close();
			stmt.close();
			// Thêm mới nhân viên

			/*  Trong đoạn code này, biến insertStmt được khởi tạo bằng cách gọi phương thức prepareStatement 
		     * của đối tượng conn (Connection) với tham số sql là một chuỗi chứa câu lệnh SQL có thể chứa các
		     *  tham số “?” để truyền giá trị vào câu lệnh SQL sau này. Sau khi biến insertStmt được khởi tạo,
		     *   bạn có thể sử dụng các phương thức setXXX để thiết lập giá trị cho các tham số “?” trong câu
		     *    lệnh SQL và cuối cùng gọi phương thức executeUpdate để thực thi câu lệnh SQL và trả về số bả ghi bị ảnh hưởng*/
			sql = "INSERT INTO employeedata (id, ho_ten, ngay_sinh, gioi_tinh, email, chuc_vu, he_so_luong, ngay_lam_viec, tong_luong) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement insertStmt = conn.prepareStatement(sql);
			insertStmt.setString(1, id);
			insertStmt.setString(2, fullname);
			insertStmt.setString(3, birthday);
			insertStmt.setString(4, gender);
			insertStmt.setString(5, email);
			String position = "";
			if (positionComboBox != null && positionComboBox.getValue() != null) {
			    position = positionComboBox.getValue().toString();
			}
			insertStmt.setString(6, position);
			for (Position p : positionList) {
				if (p.getPositionName().equals(position)) {
					salaryCoefficient = p.getSalaryCoefficient();
				}
			}
			try {
				workday = Integer.parseInt(workdayStr);

			} catch (NumberFormatException e) {
				Alert alert;
				alert = new Alert(AlertType.ERROR, "Sai định dạng ngày. Vui lòng nhập lại!");
				alert.showAndWait();
				return;
			}
			insertStmt.setDouble(7, salaryCoefficient);
			insertStmt.setInt(8, workday);
			double tongLuong = (salaryCoefficient * 1490000 * workday) / 26; 	// Lấy lương cơ bản = 1,490,000
			insertStmt.setDouble(9, tongLuong);
			insertStmt.executeUpdate();
			insertStmt.close();
			conn.close();
		} catch (SQLException s) {
			s.printStackTrace();
		}
		
		Stage stage = (Stage) saveButton.getScene().getWindow();
		stage.close();
		EmployeeController.updateEmployee(); //update 
	}
	
	public void editEmployee() {
		String id = idTextField.getText();
		String fullname = firstNameTextField.getText() + " " + lastNameTextField.getText();
		String email = emailTextField.getText();
		String birthday = birthdayTextField.getText();
		String workdayStr = workdayTextField.getText();
		String note = noteTextField.getText();
		int workday = 0;
		Toggle selectedToggle = genderToggleGroup.getSelectedToggle();
		String gender = ((RadioButton) selectedToggle).getText();
//		checkInput();
		Alert alert;
		if (id.equals(selectedEmployee.getId()) && fullname.equals(selectedEmployee.getName()) &&
			email.equals(selectedEmployee.getEmail()) && id.equals(selectedEmployee.getId()) &&
			workday == selectedEmployee.getWorkday() && gender.equals(selectedEmployee.getGender()) &&
			birthday.equals(selectedEmployee.getBirthday()) && (note != null && note.equals("someValue"))){
			
				alert = new Alert(AlertType.INFORMATION, "Bạn chưa sửa thông tin!");
				alert.showAndWait();
				return;
		}
		try {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");
    	String sql = "SELECT * FROM data.employeedata WHERE id = ? AND id != ?";
    	PreparedStatement stmt = conn.prepareStatement(sql);
    	stmt.setString(1, id);
    	stmt.setString(2, selectedEmployee.getId());
    	
    	ResultSet rs = stmt.executeQuery();
    	if (rs.next()) {
    		alert = new Alert(AlertType.ERROR, "Trùng id nhân viên khác!");
    		alert.showAndWait();
    		return;	
    	}

		// Update
        conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");
	    sql = "UPDATE data.employeedata SET id = ?, ho_ten = ?, ngay_sinh = ?, gioi_tinh = ?, email = ?, chuc_vu = ?, he_so_luong = ?, ngay_lam_viec = ?, tong_luong = ?, ghi_chu = ? WHERE id = ? ";
	    stmt = conn.prepareStatement(sql);
	    stmt.setString(1, id);
    	stmt.setString(2, fullname);
    	stmt.setString(3, birthday);
    	stmt.setString(4, gender);
    	stmt.setString(5, email);
    	String position = "";
		if (positionComboBox != null && positionComboBox.getValue() != null) {
		    position = positionComboBox.getValue().toString();
		}

		/*vòng lặp for được sử dụng để duyệt qua danh sách positionList 
		     * và nếu tên vị trí của đối tượng Position trong danh sách positionList 
		     * bằng với biến position đã thiết lập giá trị cho tham số “?” thứ 6 trong
		     *  câu lệnh SQL, biến salaryCoefficient sẽ được gán giá trị bằng phương thức
		     *   getSalaryCoefficient của đối tượng Position.
		     */
    	for (Position p : positionList) {
			if (p.getPositionName().equals(position)) {
				salaryCoefficient = p.getSalaryCoefficient();
			}
		}
    	try {
		    workday = Integer.parseInt(workdayStr);
		} catch (NumberFormatException e) {
			alert = new Alert(AlertType.ERROR, "Sai định dạng ngày. Vui lòng nhập lại!");
			alert.showAndWait();
			return;
		}

		/* Kiểm tra xem chuỗi workdayStr có phải là một số nguyên không bằng cách so sánh với biểu thức chính quy [0-9]{1,2}. 
		Biểu thức này có nghĩa là chuỗi chỉ chứa các ký tự số từ 0 đến 9 và có độ dài từ 1 đến 2 ký tự.*/
		if (workdayStr.matches("[0-9]{1,2}")) {
			int workdayTemp = Integer.parseInt(workdayStr);
			if (workdayTemp >= 0 && workdayTemp <= 31) {
				workday = workdayTemp;
			} else {
			    alert = new Alert(AlertType.ERROR, "Bạn nhập sai ngày. Vui lòng nhập lại!");
				alert.showAndWait();
		        return;
			}
		}
    	stmt.setString(6, position);
    	stmt.setDouble(7, salaryCoefficient );
    	stmt.setInt(8, Integer.parseInt(workdayStr));
		double tongLuong = (salaryCoefficient * 1490000 * workday) / 26; 	// Lấy lương cơ bản = 1,490,000
		stmt.setDouble(9, tongLuong);
		stmt.setString(10, note);
		stmt.setString(11, selectedEmployee.getId());
	    stmt.executeUpdate();
	    stmt.close();
	    conn.close();
    	
		} catch (SQLException s) {
			s.printStackTrace();
		}
		Stage stage = (Stage) saveButton.getScene().getWindow();
		stage.close();
		EmployeeController.updateEmployee(); 

	}
	



}