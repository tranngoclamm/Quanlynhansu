package main;

import java.io.Serializable;

public class Employee implements Serializable {
	private static final long serialVersionUID = 1485032912857265702L; // fix lỗi class được đọc từ file có serialVersionUID khác với class được định nghĩa 

	
	private String id;
	private String name;
	private String birthday;
	private String gender;
	private String email;
	private String position;
	private double salaryCoefficient;
	private int workday;
	private String salary;
	private String note;

    public Employee(String id, String name, String birthday, String gender, String email, String position, double salaryCoefficient, int workday, String salary, String note) {
        this.id= id;
        this.name=name;
        this.birthday=birthday;
        this.gender=gender;
        this.email=email;
        this.position=position;
        this.salaryCoefficient=salaryCoefficient;
        this.workday=workday;
        this.salary=salary;
        this.note=note;
    }
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public double getSalaryCoefficient() {
		return salaryCoefficient;
	}

	public void setSalaryCoefficient(double salaryCoefficient) {
		this.salaryCoefficient = salaryCoefficient;
	}

	public int getWorkday() {
		return workday;
	}

	public void setWorkday(int workday) {
		this.workday = workday;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note=note;
	}
	
	
    @Override
    public String toString() {
        return id + "; " + name + "; "  + birthday + "; " 
				+ gender + "; " + email + "; " + position + "; " +
				+ salaryCoefficient + "; " + workday + "; " + salary + "; " + salary;
    }
}

    

