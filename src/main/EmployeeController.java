package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

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
		ArrayList<Position> positionList = new ArrayList<>();
		ArrayList<String> positionNameList = new ArrayList<>();
		private static EmployeeController instance;
		
		public void initialize() {
			employeeShow(); // hiển thị dữ liệu nhân viên
			addPositionToFilterPositionComboBox();// lấy chức vụ để gán vào combobox lọc chức vụ
		}
		
		public void employeeShow() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");

				String sql = "SELECT * FROM data.employeedata";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				employeeList = FXCollections.observableArrayList();
				while (rs.next()) {
					String id = rs.getString("id");
					String hoTen = rs.getString("ho_ten");
					String ngaySinh = rs.getString("ngay_sinh");
					String gioiTinh = rs.getString("gioi_tinh");
					String email = rs.getString("email");
					String chucVu = rs.getString("chuc_vu");
					double heSoLuong = rs.getDouble("he_so_luong");
					int ngayLamViec = rs.getInt("ngay_lam_viec");
					double tongLuong = rs.getDouble("tong_luong");
					DecimalFormat decimalFormat = new DecimalFormat("#,##0");
					String formatted = decimalFormat.format(tongLuong); // chuyển tổng lương sang định dạng #,##0
					String ghiChu = rs.getString("ghi_chu");
					Employee employee = new Employee(id, hoTen, ngaySinh, gioiTinh, email, chucVu, heSoLuong, ngayLamViec,formatted,ghiChu);
					employeeList.add(employee);
				}
				idColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
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
		/**
		 * thêm danh sách chức vụ vào combobox để lọc chức vụ
		 */
		public void addPositionToFilterPositionComboBox() {
			PositionController positioncontroller = new PositionController();
			positionList=positioncontroller.getPosition();
			positionNameList.clear();
		    for (Position position : positionList) {
		        String name = position.getPositionName();
		        positionNameList.add(name);
		    }
			positionNameList.add("All");
			ObservableList<String> chucVuObservableList = FXCollections.observableArrayList(positionNameList);
			filteredPositionComboBox.setItems(chucVuObservableList);
	
		}
		
		/**
		 * lấy dữ liệu nhân viên hiện tại
		 */
		public ArrayList<Employee> getEmployeeCurrentData(){
			ObservableList<Employee> filteredData = FXCollections.observableArrayList();
			ObservableList<Employee> allData = employeeTableView.getItems();
			for (Employee employee : allData) {
				if (employeeTableView.getItems().indexOf(employee) > -1) {
					filteredData.add(employee);
					System.out.println(employee.getName());
				}
			}
			ArrayList<Employee> arrayList = new ArrayList<>(filteredData);
			return arrayList;
		}

			/**
			 * hiện giao diện thêm nhân viên
			 */
			@FXML
			private void handleAddEmployee(ActionEvent event) throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/EmployeeForm.fxml"));
			Parent root = loader.load();
			EmployeeControllerFunctions employeecontrollerfunctions = loader.getController();
			Employee employee = new Employee("", "", "", "", "", "", 0, 0,"", "");
			employeecontrollerfunctions.setData(employee, "Add");
			Stage employeeFormStage = new Stage();
			employeeFormStage.setTitle("Add Employee");
			employeeFormStage.setScene(new Scene(root));
			employeeFormStage.show();
				}
			
			/**
			* hiện giao diện sửa nhân viên
			*/
			@FXML
			private void handleEditEmployee(ActionEvent event) throws IOException {
				try {
					Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/EmployeeForm.fxml"));
					Parent root = loader.load();
					Stage employeeFormStage = new Stage();
					employeeFormStage.setTitle("Edit Position");
					employeeFormStage.setResizable(false);
					employeeFormStage.setScene(new Scene(root));
					EmployeeControllerFunctions employeecontrollerfunctions = loader.getController();
					employeecontrollerfunctions.setData(selectedEmployee, "Edit");
					employeeFormStage.show();
				} catch (Exception e) {
					Alert alert;
					alert = new Alert(AlertType.ERROR, "Bạn chưa chọn nhân viên để sửa");
					alert.showAndWait();
					return;
				}
			}
		 	/**
			 * lưu dữ liệu nhân viên xuống file nhị phân 
			 */
			@FXML
			private void saveBinaryEmployeeData(ActionEvent e) {
				ArrayList<Employee> employeeCurrentData=getEmployeeCurrentData();
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Binary File", "*.bin"),
						new ExtensionFilter("All File", "*.*"));
				File file = fileChooser.showSaveDialog(null);
				if (file != null) {
					try {
						FileOutputStream fos = new FileOutputStream(file);
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeObject(employeeCurrentData);
						oos.close();
						fos.close();
						Alert alert = new Alert(AlertType.INFORMATION, "Lưu thành công!");
						alert.showAndWait();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
			
			/**
			 * lưu xuống file txt, csv
			 */
		 @FXML
			public void handleSaveAs() throws IOException {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text File", "*.txt"),
														 new ExtensionFilter("CSV File", "*.csv"),
														 new ExtensionFilter("All File", "*.*"));
				File file = fileChooser.showSaveDialog(null);		
				if (file != null) {
					try (PrintWriter writer = new PrintWriter(file)) {
						if(currentTableName.equals("EmployeeView")) {
							ArrayList<Employee> arrayList=getEmployeeCurrentData();
							writer.println("| ID | Name  | Birthday	| Gender | Email	| Position	| Coeff | Workday | Salary | Note | " + "\n");
						    for (Employee employee : arrayList) {
						        writer.println(employee.getId() + "; "
						                	 + employee.getName() + "; "
						                	 + employee.getBirthday() + "; "
						                	 + employee.getGender() + "; "
						                	 + employee.getEmail() + "; "
						                	 + employee.getPosition() + "; "
						                	 + employee.getSalaryCoefficient() + "; "
						                	 + employee.getWorkday() + "; "
						                	 + employee.getSalary() + "; "
						                	 + employee.getNote() + "\n");
						    }
						    Alert alert = new Alert(AlertType.INFORMATION, "Lưu thành công!");
					        alert.showAndWait();
						}
						if(currentTableName.equals("PositionView")) {
							PositionController.updatePosition();
							PositionController positioncontroller = new PositionController();
							ArrayList<Position> arrayList=positioncontroller.getPosition();
							writer.println("| Chuc_Vu | He_so_luong |" + "\n");
						    for (Position position : arrayList) {
						        writer.println(position.getPositionName() + "; "
						                	 + position.getSalaryCoefficient() + "\n");
						    }
						    Alert alert = new Alert(AlertType.INFORMATION, "Lưu thành công!");
					        alert.showAndWait();
						}
						
					
					} catch (IOException e) {
					    e.printStackTrace();
					}
				}
			}
		 
			/**
			 * chuyển đổi chức năng giữa quản lý nhân viên với quản lý chức vụ.
			 */
			@FXML
			public void switchTable(ActionEvent event) throws IOException {
				Button clickedButton = (Button) event.getSource();
				String clickedName = clickedButton.getText(); // lấy tên Button đã được nhấn
				if (clickedName.contains("QUẢN LÝ CHỨC VỤ") && !currentTableName.equals("PositionView")) { // tránh hiển thị dữ liệu lại khi nhấn cùng loại Button 
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/PositionView.fxml"));
					AnchorPane positionView = loader.load();
					baseView.getChildren().setAll(positionView); 
					currentTableName = "PositionView";
					return;
				} else if (clickedName.contains("QUẢN LÝ NHÂN VIÊN") && !currentTableName.equals("EmployeeView")) {
					baseView.getChildren().setAll(employeeView);
//					getPosition(); // cập nhật lại thông tin chức vụ 
					addPositionToFilterPositionComboBox();
					currentTableName = "EmployeeView";
					return;
				}
			}
			
			/**
			 * update lại dữ liệu
			 */

			public EmployeeController() {
				instance = this; // lưu trữ đối tượng EmployeeController hiện tại vào biến instance
			}

			public static void updateEmployee() { // Nếu đã có đối tượng EmployeeController được tạo ra trước đó (instance !=null) thì sẽ hiển thị lại thông tin
				if (instance != null) {
					instance.employeeShow();
				}
			}
			
			/**
			 * đăng xuất
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
