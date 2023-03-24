import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BinaryFileIO {
    // Lưu trữ danh sách các nhân viên xuống file nhị phân
    public void saveEmployees(ArrayList<Employee> employees) throws IOException {
        FileOutputStream fos = new FileOutputStream("employees.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(employees);
        oos.close();
        fos.close();
    }
}