package com.example.crud.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class EmployeeDao {


    private String employeeName;
    private String position;

    public EmployeeDao(String employeeName,String position){
         this.employeeName=employeeName;
        this.position=position;
    }
}
