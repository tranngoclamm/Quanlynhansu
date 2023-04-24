package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.ObservableList;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import java.sql.PreparedStatement;
import javafx.scene.input.KeyCode;
import javafx.collections.transformation.FilteredList;
public class EmployeeController {
		@FXML
		private AnchorPane baseView;
		@FXML
		private AnchorPane employeeView;
		private String currentTableName = "EmployeeView";
		
		@FXML
	    private TableView<Employee> employeeTableView;
		@FXML
		private TableColumn<Employee, String> idColumn;
		@FXML
		private TableColumn<Employee, String> nameColumn;
		@FXML
		private TableColumn<Employee, String> birthdayColumn;
		@FXML
		private TableColumn<Employee, String> genderColumn;
		@FXML
		private TableColumn<Employee, String> emailColumn;
		@FXML
		private TableColumn<Employee, String> positionColumn;
		@FXML
		private TableColumn<Employee, Double> coeffColumn;
		@FXML
		private TableColumn<Employee, Integer> workdayColumn;
		@FXML
		private TableColumn<Employee, String> salaryColumn;
		@FXML
		private TextField idTextField;
		@FXML
		private TextField fisrtNameTextField;
		@FXML
		private TextField lastNameTextField;
		@FXML
		private TextField birthdayTextField;
		@FXML
		private TextField emailTextField;
		@FXML
		private TextField workdayTextField;
		@FXML
		private ComboBox<String> positionComboBox;
		@FXML
		private ComboBox<String> filteredPositionComboBox;
		@FXML
		private RadioButton maleRadioButton;
		@FXML
		private RadioButton femaleRadioButton;
		@FXML
		private ToggleGroup gender;
		@FXML
	    public TextField searchTextField;
		@FXML
	    public TextField filteredSalaryTextField;
		@FXML
		private Button saveButton;
		@FXML
		private Button addEmployee;
		private ObservableList<Employee> employeeList;
		List<Position> positionList = new ArrayList<>();
		List<String> positionNameList = new ArrayList<>();
		private static EmployeeController instance;
		Double salaryCoefficient=0.0;



	    public void initialize() {
	    	employeeShow(); // hiển thị dữ liệu nhân viên

	    	getPosition(); // lấy chức vụ để gán vào chức năng lọc chức vụ
	        searchEmployee();
	        filteredSalaryTextField.setOnKeyPressed(event -> { // Lọc chức vụ = phím Enter
	            if (event.getCode() == KeyCode.ENTER) {
	                filteredSalary(); 
	            }
	        });
	        filteredPosition();
//	        filterData();
	    }
	    
		public void getPosition() {
			
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");

		        String sql = "SELECT * FROM data.positiondata";
		        Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(sql);
		        positionNameList.clear();
		        while (rs.next()) {
		            String chucVu = rs.getString("chuc_vu");
		            double heSoLuong = rs.getDouble("he_so_luong");
		            Position position = new Position(chucVu, heSoLuong);
		            positionNameList.add(chucVu);
		            positionList.add(position);
		        	}
	            positionNameList.add("All");
		    	ObservableList<String> chucVuObservableList = FXCollections.observableArrayList(positionNameList);
			    filteredPositionComboBox.setItems(chucVuObservableList); 
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
			}
		
	 
	    public void employeeShow() {
	    	try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");

		        String sql = "SELECT * FROM data.employeedata";
		        Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(sql);
		        
		        employeeList = FXCollections.observableArrayList();
		        while (rs.next()) {
		            String id  = rs.getString("id");
		            String hoTen = rs.getString("ho_ten");
		            String ngaySinh = rs.getString("ngay_sinh");
		            String gioiTinh  = rs.getString("gioi_tinh");
		            String email = rs.getString("email");
		            String chucVu = rs.getString("chuc_vu");
		            double heSoLuong = rs.getDouble("he_so_luong");
		            int ngayLamViec = rs.getInt("ngay_lam_viec");
		            double tongLuong = rs.getDouble("tong_luong");
		            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
		            String formatted = decimalFormat.format(tongLuong); // chuyển tổng lương sang định dạng #,##0
		            

		            Employee employee = new Employee(id, hoTen, ngaySinh, gioiTinh, email, chucVu, heSoLuong, ngayLamViec, formatted);
		            employeeList.add(employee);
		        }
		        if (idColumn != null) {
			        idColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
		        } else idColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("id1"));

		        nameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
		        birthdayColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("birthday"));
		        genderColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("gender"));
		        emailColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
		        positionColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
		        coeffColumn.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salaryCoefficient"));
		        workdayColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("workday"));
		        salaryColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("salary"));
		   
		        employeeTableView.setItems(employeeList);
		        
		        conn.close();
		        employeeTableView.setStyle("-fx-font-size: 13px;");
		        workdayColumn.setStyle("-fx-alignment: center;");
		        idColumn.setStyle("-fx-alignment: center;");
		        coeffColumn.setStyle("-fx-alignment: center;");
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	    }
	    
	    
	   public EmployeeController() {
	    	instance=this;
	    }
	   public static void updateEmployee(){
	    	if(instance != null) {
	    		instance.employeeShow();
	    	}
	    }
	   
		/**
		* hiển thị bảng khi thêm, sửa nhân viên.
		*/
		public void showEmployeeForm(String title) throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/EmployeeForm.fxml"));
			Parent root = loader.load();
			Stage employeeFormStage = new Stage();
			employeeFormStage.setTitle(title);
			employeeFormStage.setScene(new Scene(root));
			employeeFormStage.show();

		}

		
		 /**
		  * thêm nhân viên
		  */
		@FXML
		private void handleAddEmployee(ActionEvent event) {
		    try {
				showEmployeeForm("Add Employee");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	
		}

		 @FXML
		 private void editEmployee(ActionEvent event) throws IOException {
		        showEmployeeForm("Edit Employee");
		    } 
		 
		 /**
		  * chuyển đổi chức năng giữa quản lý nhân với quản lý chức vụ.
		  */
		 @FXML
		 private void switchTable(ActionEvent event) throws IOException {
		     Button clickedButton = (Button) event.getSource();
		     String clickedName = clickedButton.getText();
		     if (clickedName.contains("QUẢN LÝ CHỨC VỤ") && !currentTableName.equals("PositionView")) {
		         FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/PositionView.fxml"));
		         AnchorPane positionView = loader.load();
		         baseView.getChildren().setAll(positionView);
		         currentTableName = "PositionView";
		     } else if (clickedName.contains("QUẢN LÝ NHÂN VIÊN") && !currentTableName.equals("EmployeeView")) {
		         baseView.getChildren().setAll(employeeView);
		         getPosition();
		         currentTableName = "EmployeeView";
		     }
		 }
		 
		 /**
		  * tìm kiếm.
		  */
		 public void searchEmployee() {
		     searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		         if (newValue == null || newValue.isEmpty()) { // nếu ô tìm kiếm trống
		             employeeTableView.setItems(employeeList); // hiển thị toàn bộ danh sách
		             return;
		         }
		         ObservableList<Employee> filteredList = FXCollections.observableArrayList();
		         String filter = newValue.toUpperCase(); // chuyển đổi giá trị tìm kiếm thành chữ in hoa
		         for (Employee employee : employeeList) {
		             String id = String.valueOf(employee.getId()).toUpperCase();
		             String email = employee.getEmail().toUpperCase();
		             String name = employee.getName().toUpperCase();
		             if (id.contains(filter) || email.contains(filter) || name.contains(filter)) {
		                 filteredList.add(employee); // thêm bản ghi vào danh sách đã lọc
		             }
		         }
		         employeeTableView.setItems(filteredList);
		     });
		 }
		 
		 /**
		  * lọc hệ số lương > n
		  */
		 @FXML
		 public void filteredSalary() {
		        ObservableList<Employee> filteredList = FXCollections.observableArrayList();
		         if (filteredSalaryTextField == null || filteredSalaryTextField.getText().isEmpty()) { 
		             employeeTableView.setItems(employeeList); // hiển thị toàn bộ danh sách
		             return;
		         }
		         double filter = Double.parseDouble(filteredSalaryTextField.getText()); // lấy giá trị double từ TextField
		         for (Employee employee : employeeList) {
		             if (employee.getSalaryCoefficient() >= filter ) {
		                 filteredList.add(employee); 
		             }
		         }
		         employeeTableView.setItems(filteredList); 		         
		 }
		 
		 /**
		  * lọc chức vụ
		  */
		 public void filteredPosition() {
		        ObservableList<Employee> filteredList = FXCollections.observableArrayList();
			 	String selectedValue = filteredPositionComboBox.getValue();
		         if (selectedValue == null || selectedValue.isEmpty() || selectedValue.equals("All") ) { 
		             employeeTableView.setItems(employeeList); // hiển thị toàn bộ danh sách
		             return;
		         }
		         for (Employee employee : employeeList) {
		             if (employee.getPosition().contains(selectedValue)) {
		                 filteredList.add(employee); 
		             }
		         }
		         employeeTableView.setItems(filteredList); 
		 } 
		 
		 
		 
		 
//		 public void filterData() {
////		    String selectedPosition = filteredPositionComboBox.getValue();
////		    String salaryText = filteredSalaryTextField.getText();
//			ObservableList<Employee> filteredList = FXCollections.observableArrayList();
//		    ObservableList<Employee> filteredList2 = FXCollections.observableArrayList();
//		    ObservableList<Employee> filteredList3 = FXCollections.observableArrayList();
//		 	String selectedValue = filteredPositionComboBox.getValue();
//	         if (selectedValue == null || selectedValue.isEmpty() || selectedValue.equals("All") ) { 
//	             employeeTableView.setItems(employeeList); // hiển thị toàn bộ danh sách
//	             return;
//	         } else {
//	         for (Employee employee : employeeList) {
//	             if (employee.getPosition().contains(selectedValue)) {
//	                 filteredList.add(employee); 
//	             }
//	         }
//	         employeeTableView.setItems(filteredList); 
//	         }
//		    filteredSalaryTextField.setOnKeyPressed(event -> { // Lọc chức vụ = phím Enter
//
//	            if (event.getCode() == KeyCode.ENTER) {
//	            	 if (filteredSalaryTextField == null || filteredSalaryTextField.getText().isEmpty()) { 
//	  		             employeeTableView.setItems(filteredList); // hiển thị toàn bộ danh sách
//	  		             return;
//	  		         }
//	  		double filter = Double.parseDouble(filteredSalaryTextField.getText()); // lấy giá trị double từ TextField
//	  		for (Employee employee : filteredList) {
//	  			if (employee.getSalaryCoefficient() >= filter ) {
//	  		      
//	  		            filteredList2.add(employee); 
//	  		          }
//	  		       }
//	  		         employeeTableView.setItems(filteredList2); 
//	  		       
//	  			    
//	            }
//	        });
//		 }
////		
		 /**
		  * xóa nhân viên.
		  */
		 public void deleteEmployee(ActionEvent e) {
			  Employee selected = employeeTableView.getSelectionModel().getSelectedItem();
			    Alert alert = new Alert(AlertType.CONFIRMATION);
			    alert.setTitle("Xác nhận");
			    alert.setHeaderText(null);
			    alert.setContentText("Bạn có chắc chắn muốn xóa nhân viên này?");
			    Optional<ButtonType> result = alert.showAndWait();
			    if (result.isPresent() && result.get() == ButtonType.OK) {
			    try {
			        // Kết nối đến cơ sở dữ liệu
			    	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");
			        // Tạo câu lệnh SQL DELETE và thực thi
			        String sql = "DELETE FROM data.employeedata WHERE id = ?";
			        PreparedStatement stmt = conn.prepareStatement(sql);
			        stmt.setString(1, selected.getId());
			        stmt.executeUpdate();
			        conn.close();
			        employeeShow(); 
			    } catch (SQLException ex) {
			        ex.printStackTrace();
			    }
			  }
			}
		 
		 /**
		  * gán chức vụ cho nhân viên
		  */
		 public void handleAssignRole() {
//			 	String myString = "Hello world!";
			 				 	
			 	try {
			 	FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/Assign Role.fxml"));
			 	Parent root;
				root = loader.load();
				Stage employeeFormStage = new Stage();
				employeeFormStage.setTitle("AssignRole");
				AssignRole myDestinationObject = loader.getController();
		    	Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
			 	myDestinationObject.setMyVariable(selectedEmployee);
				employeeFormStage.setScene(new Scene(root));
				employeeFormStage.show();
			 	} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
	
		 /**
		  * đăng xuất.
		  */
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
