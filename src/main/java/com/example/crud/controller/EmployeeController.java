package com.example.crud.controller;

import com.example.crud.dao.EmployeeDao;
import com.example.crud.model.EmployeeModel;
import com.example.crud.repository.EmployeeRepository;
import com.example.crud.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/CRUD")
public class EmployeeController{

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(path = "/")
    public String indexFile(){
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET,value = "/get_employee",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEmployee(){
        try {
             return new ResponseEntity<>(employeeService.getEmployee(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Employees not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "/get_employee_id",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEmployeeById(@RequestParam("id") int id){
        try {
        ArrayList<EmployeeModel> employeeDaoArrayList=new ArrayList<>();
        Optional<EmployeeModel> employeeModel= employeeRepository.findById(id);
        EmployeeModel employeeDao=new EmployeeModel(employeeModel.get().getId(),employeeModel.get().getEmployeename(),employeeModel.get().getPosition());
        employeeDaoArrayList.add(employeeDao);
        return new ResponseEntity<>(employeeDaoArrayList, HttpStatus.OK);
        }
        catch (Exception e){
             return new ResponseEntity<>("Employee Id not found: "+id, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save_employee",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeModel employeeModel){
        try {
            return employeeService.saveEmployee(employeeModel);
        }catch (Exception e){
            return new ResponseEntity<>("Employee not saved with "+employeeModel.getEmployeename(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/update_employee",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEmployee(@RequestParam("id") int id,@RequestBody EmployeeModel employeeModel){
        try {
            return employeeService.updateEmployee(id, employeeModel);
        }
        catch (Exception e){
            return new ResponseEntity<>("Employee not updated: "+employeeModel.getEmployeename(), HttpStatus.NOT_FOUND);

        }
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/delete_employee",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteEmployee(@RequestParam("id") int id){
        try {
            return new ResponseEntity<>(employeeService.deleteEmployee(id), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Employee not deleted: "+id, HttpStatus.NOT_FOUND);

        }
    }

    @RequestMapping(method = RequestMethod.POST,value = "/get_employees_search",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  getEmployeeByBoth(@RequestBody EmployeeModel employeeModel,@RequestParam("pageNo") int pageNo ){
        try {
            return new ResponseEntity<>(employeeService.getEmployeesBySearch(employeeModel, pageNo), HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>("Employee not found: ", HttpStatus.NOT_FOUND);
        }
    }

//    @RequestMapping(method = RequestMethod.GET,value = "/get_employee_pagination",produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> getAllEmployees(
//            @RequestParam("pageNo") Integer pageNo,
//            @RequestParam(defaultValue="2") Integer pageSize,
//            @RequestParam(defaultValue = "id") String sortBy)
//    {
//        try {
//
//            List<EmployeeModel> list = employeeService.getAllEmployees(pageNo, pageSize, sortBy);
//            return new ResponseEntity<List<EmployeeModel>>(list, new HttpHeaders(), HttpStatus.OK);
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(" No pagination: ", HttpStatus.NOT_FOUND);
//        }
//    }
}
