package com.example.testrest.controller;

import com.example.testrest.exception.EmployeeNotFoundException;
import com.example.testrest.model.Employee;
import com.example.testrest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
        System.out.println("Added: " + employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @PostMapping("/employees") //Can be ResponseEntity<VOID>
    public ResponseEntity<Employee> addEmployeeBetter(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.save(employee);
        System.out.println("Added: " + createdEmployee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAll();
        if(employees.isEmpty()) {
            System.out.println("There are no employees");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        System.out.println("Found " + employees.size() + " employees.");
        System.out.println(Arrays.toString(employees.toArray()));
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            System.out.println("Employee with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Found Employee:: " + employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateEmployee(@RequestBody Employee employee) {
        Employee existingEmp = employeeService.getById(employee.getId());
        if (existingEmp == null) {
            System.out.println("Employee with id " + employee.getId() + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            employeeService.save(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) throws EmployeeNotFoundException {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            System.out.println("Employee with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            employeeService.delete(id);
            System.out.println("Employee with id " + id + " deleted");
            return new ResponseEntity<>(HttpStatus.GONE);
        }
    }


}
