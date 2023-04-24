package main;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class PositionController {

	@FXML
	private TableView<Position> positionTableView;

	@FXML
	private TableColumn<Position, String> chucVuColumn;

	@FXML
	private TableColumn<Position, Double> heSoLuongColumn;

	@FXML
	private Button saveButton;
	@FXML
	private Button editPositon;
	@FXML
	private Button addPosition;
	@FXML
	private TextField positionNameTextField;
	@FXML
	private TextField salaryCoefficientTextField;
	private static PositionController instance;
	private ObservableList<Position> positionList;
	String title = "";

	public void initialize() {
		positionShow();
	}

	/**
	 * hiển thị dữ liệu chức vụ
	 */

	public void positionShow() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");
			String sql = "SELECT chuc_vu, he_so_luong FROM data.positiondata";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			positionList = FXCollections.observableArrayList();
			while (rs.next()) {
				String chucVu = rs.getString("chuc_vu");
				double heSoLuong = rs.getDouble("he_so_luong");
				Position position = new Position(chucVu, heSoLuong);
				positionList.add(position);
			}
			chucVuColumn.setCellValueFactory(new PropertyValueFactory<Position, String>("positionName"));
			heSoLuongColumn.setCellValueFactory(new PropertyValueFactory<Position, Double>("salaryCoefficient"));
			positionTableView.setItems(positionList);
			positionTableView.setStyle("-fx-font-size: 15px;");
			chucVuColumn.setStyle("-fx-alignment: center;");
			heSoLuongColumn.setStyle("-fx-alignment: center;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * xóa chức vụ
	 */
	public void deletePosition(ActionEvent e) {
		Position selected = positionTableView.getSelectionModel().getSelectedItem();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Xác nhận");
		alert.setHeaderText(null);
		alert.setContentText("Bạn có chắc chắn muốn xóa chức vụ này?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");
				String sql = "DELETE FROM data.positiondata WHERE chuc_vu = ?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, selected.getPositionName());
				stmt.executeUpdate();
				positionTableView.getItems().remove(selected);
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * hiển thị giao diện khi thêm / sửa chức vụ.
	 * 
	 * @param event
	 * @throws IOException
	 */
//	@FXML
//	private void handleButtonAction(ActionEvent event) throws IOException {
//		Button button = (Button) event.getSource();
//		String action = button.getId();
//		if (action.equals("addPosition")) {
//			showPositionForm("Add");
//		} else if (action.equals("editPosition")) {
//			showPositionForm("Edit");
//		}
//	}

//	public void showPositionForm(String title) throws IOException {
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/PositionForm.fxml"));
//		Parent root = loader.load();
//		if (positionTableView.getSelectionModel().getSelectedItem() != null) {
//			PositionCon myDestinationObject = loader.getController();
//			Position selectedPosition = positionTableView.getSelectionModel().getSelectedItem();
//			myDestinationObject.setMyVariable(selectedPosition, title);
//		}
//		Stage positionFormStage = new Stage();
//		positionFormStage.setTitle(title + " Position");
//		positionFormStage.setResizable(false);
//		positionFormStage.setScene(new Scene(root));
//		positionFormStage.show();
//	}

	@FXML
	public void handleAddButton() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/PositionForm.fxml"));
			Parent root = loader.load();
			PositionCon myDestinationObject = loader.getController();
//			EmployeeController a = new (EmployeeController);
			Position a = new Position(title, 0);
			myDestinationObject.setData(a, "Add");
			Stage positionFormStage = new Stage();
			positionFormStage.setTitle("Add Position");
			positionFormStage.setResizable(false);
			positionFormStage.setScene(new Scene(root));
			positionFormStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleEditButton() throws IOException {
		try {
			Position selectedPosition = positionTableView.getSelectionModel().getSelectedItem();
//			if(selectedPosition == null) {
//	            throw new Exception("Bạn chưa chọn chức vụ để sửa.");
//	        }
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/PositionForm.fxml"));
			Parent root = loader.load();
			Stage positionFormStage = new Stage();
			positionFormStage.setTitle("Edit Position");
			positionFormStage.setResizable(false);
			positionFormStage.setScene(new Scene(root));
			PositionCon positioncon = loader.getController();
			positioncon.setData(selectedPosition, "Edit");
			positionFormStage.show();
		} catch (Exception e) {
			Alert alert;
			alert = new Alert(AlertType.ERROR, "Bạn chưa chọn chức vụ để sửa");
			alert.showAndWait();
			return;
		}
	}

	/**
	 * lưu file nhị phân
	 */
	public ArrayList<Position> getEmployeeData() {
		ObservableList<Position> allData = positionTableView.getItems();
		ArrayList<Position> arrayList = new ArrayList<>(allData);
		return arrayList;
	}

	// lưu trực tiếp
//	 @FXML 
//	 private void saveBinaryPositionData(ActionEvent e) {
//		 ObservableList<Position> filteredData = FXCollections.observableArrayList();
//		 ObservableList<Position> allData = positionTableView.getItems();
//		 File file = new File("data/positionData.bin");
//		 for (Position position : allData) {
//		     if (positionTableView.getItems().indexOf(position) > -1) { 
//		         filteredData.add(position);
//		     }
//		 	}
//		    ArrayList<Position> arrayList = new ArrayList<>(filteredData);
//		 try {
//		     FileOutputStream fos = new FileOutputStream(file);
//		     ObjectOutputStream oos = new ObjectOutputStream(fos);
//		     oos.writeObject(arrayList);
//		     oos.close();
//		     fos.close();
//		     Alert alert = new Alert(AlertType.INFORMATION, "Lưu thành công!");
//		     alert.showAndWait();
//		 } catch (IOException ex) {
//		     ex.printStackTrace();
//		 }
//	 }

	// dùng Filechooser
	@FXML
	private void saveBinaryPositionData(ActionEvent e) {
		ObservableList<Position> allData = positionTableView.getItems();
		ArrayList<Position> arrayList = new ArrayList<>(allData);
		FileChooser fileChooser = new FileChooser();
//	    fileChooser.setTitle("Lưu file dữ liệu nhân viên");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Binary File", "*.bin"),
				new ExtensionFilter("All File", "*.*"));
		File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			try {
				FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(arrayList);
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
	 * update dữ liệu khi thêm, sửa chức vụ
	 */
	public PositionController() {
		instance = this;
	}

	public static void updatePosition() {
		if (instance != null) {
			instance.positionShow();
		}
	}

}
