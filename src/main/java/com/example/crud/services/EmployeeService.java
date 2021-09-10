package com.example.crud.services;

 import com.example.crud.model.EmployeeModel;
import com.example.crud.repository.EmployeeRepository;
import com.example.crud.repository.EmployeeRepositoryPageable;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
 import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

//    @Autowired
//    private EmployeeRepositoryPageable employeeRepositoryPageable;

    @Autowired
    private EntityManager entityManager;

    public ResponseEntity<?> saveEmployee(EmployeeModel employeeModel) {
        boolean isExists = employeeRepository.existsByEmployeenameAndIdIsNot(employeeModel.getEmployeename(), 0);
//        if (employeeRepository.getEmployeeByName(employeeModel.getEmployeename()).isEmpty()) {
        if (!isExists) {
            employeeRepository.save(employeeModel);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    public ArrayList<EmployeeModel> getEmployee() {
        Iterable<EmployeeModel> employeeModel = employeeRepository.getAll();
        ArrayList<EmployeeModel> employeeDaoArrayList = new ArrayList<>();
        employeeModel.forEach(employeeModel1 -> {
            EmployeeModel employeeDao = new EmployeeModel(employeeModel1.getId(), employeeModel1.getEmployeename(), employeeModel1.getPosition());
            employeeDaoArrayList.add(employeeDao);
        });

        return employeeDaoArrayList;

    }

    public ResponseEntity<?> updateEmployee(int id, EmployeeModel employeeModelData) {
        boolean isExists = employeeRepository.existsByEmployeenameAndIdIsNot(employeeModelData.getEmployeename(), id);
//        if (employeeRepository.getFilterEmployee(employeeModelData.getEmployeename(), id).isEmpty()) {
        if (!isExists) {
            Optional<EmployeeModel> employeeModel = employeeRepository.findById(id);
            employeeModel.get().setEmployeename(employeeModelData.getEmployeename());
            employeeModel.get().setPosition(employeeModelData.getPosition());
            employeeRepository.save(employeeModel.get());
            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> deleteEmployee(int id) {
        employeeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Page<EmployeeModel> getEmployeesBySearch(EmployeeModel employeeModel, int pageNo) {
        int pageSize = 5;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmployeeModel> criteriaQuery = criteriaBuilder.createQuery(EmployeeModel.class);
        Root<EmployeeModel> root = criteriaQuery.from(EmployeeModel.class);
        CriteriaQuery<Long> rootCount = criteriaBuilder.createQuery(Long.class);

        String empName = employeeModel.getEmployeename();
        String empPosition = employeeModel.getPosition();

        ArrayList<Predicate> searchCriterias = new ArrayList<>();

        if ((empName != null) && (!empName.isEmpty())) {
            searchCriterias.add(criteriaBuilder.like(root.get("employeename"), "%" + empName + "%"));
        }
        if ((empPosition != null) && (empPosition != "")) {
            searchCriterias.add(criteriaBuilder.like(root.get("position"), "%" + empPosition + "%"));
        }
        if (searchCriterias.size() > 0) {
            criteriaQuery.select(root).where(criteriaBuilder.and(searchCriterias.toArray(new Predicate[searchCriterias.size()])));
        } else {
            criteriaQuery.select(root);
        }

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));

        TypedQuery<EmployeeModel> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((pageNo * pageSize) + 1);
        typedQuery.setMaxResults(pageSize);
        List<EmployeeModel> lstEmployees = typedQuery.getResultList();

        rootCount.select(criteriaBuilder.count(rootCount.from(EmployeeModel.class)));
        if (searchCriterias.size() > 0) {
            rootCount.where(criteriaBuilder.and(searchCriterias.toArray(new Predicate[searchCriterias.size()])));
        }

        Long totalRecord = entityManager.createQuery(rootCount).getSingleResult();

        Pageable objPage = PageRequest.of(pageNo, pageSize);
        return new PageImpl<>(lstEmployees, objPage, totalRecord);
     }

//    public List<EmployeeModel> getAllEmployees(Integer pageNo, Integer pageSize, String sortBy) {
//        PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//
//        Page<EmployeeModel> pagedResult = employeeRepositoryPageable.findAll((org.springframework.data.domain.Pageable) paging);
//        if (pagedResult.hasContent()) {
//            return pagedResult.getContent();
//        } else {
//            return new ArrayList<EmployeeModel>();
//        }
//    }
}