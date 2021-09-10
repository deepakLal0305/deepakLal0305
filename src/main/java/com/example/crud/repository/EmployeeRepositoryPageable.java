package com.example.crud.repository;

import com.example.crud.model.EmployeeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface EmployeeRepositoryPageable extends PagingAndSortingRepository<EmployeeModel,Integer> {

}