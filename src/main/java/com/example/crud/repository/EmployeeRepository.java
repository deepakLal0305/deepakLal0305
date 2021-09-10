package com.example.crud.repository;

import com.example.crud.model.EmployeeModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<EmployeeModel, Integer> {

    @Query("Select em FROM EmployeeModel em order by em.id")
    public List<EmployeeModel> getAll();

    boolean existsByEmployeenameAndIdIsNot(String employeename, int id);

//    @Query("Select em FROM EmployeeModel em where em.employeename like ?1%")
//    public List<EmployeeModel> getEmployeeByName(String name);

// @Query("Select em FROM EmployeeModel em where em.employeename like %?1% ")
// public List<EmployeeModel> getEmployeeByNme(String name);
//
// @Query("Select em FROM EmployeeModel em where em.position like %?1%")
    // public List<EmployeeModel> getEmployeeByPosition(String position);

// @Query("Select em FROM EmployeeModel em where em.employeename like %?1% and em.position like %?2%")
// public List<EmployeeModel> getEmployeeByBoth(String name,String position);

//    @Query("Select em FROM EmployeeModel em where em.employeename=?1 AND em.id <> ?2")
//    public List<EmployeeModel> getFilterEmployee(String name, int id);

}