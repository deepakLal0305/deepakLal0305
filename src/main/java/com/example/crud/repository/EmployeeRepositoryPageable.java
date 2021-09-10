package com.example.crud.repository;

import com.example.crud.model.EmployeeModel;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface EmployeeRepositoryPageable extends PagingAndSortingRepository<EmployeeModel,Integer> {

}