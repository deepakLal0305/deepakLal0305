package com.example.crud.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Getter
@Setter
@ToString

@Entity
@Table(name="employee")
public class EmployeeModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private String employeename;
        private String position;

    public EmployeeModel(){}

    public EmployeeModel(int id, String employeename, String position) {
        this.id = id;
        this.employeename = employeename;
        this.position = position;
    }
}
