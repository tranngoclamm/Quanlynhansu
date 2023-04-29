package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private ArrayList<Position> data = new ArrayList<>();
	String title = "";

	public void initialize() {
		chucVuColumn.setCellValueFactory(new PropertyValueFactory<Position, String>("positionName"));
		heSoLuongColumn.setCellValueFactory(new PropertyValueFactory<Position, Double>("salaryCoefficient"));
		positionShow();
	}

	/**
	 * hiển thị dữ liệu chức vụ
	 */
	public ArrayList<Position> getPosition() {

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/data", "root", "");

			String sql = "SELECT * FROM data.positiondata";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			data.clear();
			while (rs.next()) {
				String chucVu = rs.getString("chuc_vu");
				double heSoLuong = rs.getDouble("he_so_luong");
				Position position = new Position(chucVu, heSoLuong);
				data.add(position);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public void positionShow() {
		data = getPosition();
		ObservableList<Position> positionList = FXCollections.observableList(data);
		positionTableView.setItems(positionList);
		
	}
	/**
	 * hiển thị giao diện khi thêm / sửa chức vụ.
	 */

	@FXML
	public void handleAddButton() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/PositionForm.fxml"));
			Parent root = loader.load();
			PositionControllerFunctions positioncontrollerfunctions = loader.getController();
			Position a = new Position(title, 0);
			positioncontrollerfunctions.setData(a, "Add");
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../giaodien/PositionForm.fxml"));
			Parent root = loader.load();
			Stage positionFormStage = new Stage();
			positionFormStage.setTitle("Edit Position");
			positionFormStage.setResizable(false);
			positionFormStage.setScene(new Scene(root));
			PositionControllerFunctions positioncontrollerfunctions = loader.getController();
			positioncontrollerfunctions.setData(selectedPosition, "Edit");
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
