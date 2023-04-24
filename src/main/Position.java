package main;

import java.io.Serializable;

public class Position implements Serializable  {
    private String positionName;
    private double salaryCoefficient;

    public Position(String positionName, double salaryCoefficient) {
        this.positionName = positionName;
        this.salaryCoefficient = salaryCoefficient;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public double getSalaryCoefficient() {
        return salaryCoefficient;
    }

    public void setSalaryCoefficient(double salaryCoefficient) {
        this.salaryCoefficient = salaryCoefficient;
    }
}