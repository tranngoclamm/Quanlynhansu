package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class DataExporter {

	/**
	 * đọc file nhị phân in ra console
	 */
	
	public void readBinaryFile(String filePath) {
		filePath="data/employeeData.bin";
	    try {
	        FileInputStream fis = new FileInputStream(filePath);
	        ObjectInputStream ois = new ObjectInputStream(fis);
	        List<?> list = (List<?>) ois.readObject(); 	 // Đọc dữ liệu object từ file
	        for (Object obj : list) { 	  // In thông tin các đối tượng theo dòng
	            System.out.println(obj.toString());
	        }
	        ois.close();
	        fis.close();
	    } catch (IOException | ClassNotFoundException ex) {
	        ex.printStackTrace();
	    }
	}
}
