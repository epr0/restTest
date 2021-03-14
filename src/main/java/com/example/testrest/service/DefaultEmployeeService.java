package com.example.testrest.service;

import com.example.testrest.exception.EmployeeNotFoundException;
import com.example.testrest.model.Employee;
import com.example.testrest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class DefaultEmployeeService implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee save(Employee entity) {
		return employeeRepository.save(entity);
	}

	@Override
	public Employee getById(Serializable id) {
		return employeeRepository.findById((Long) id).get();
	}

	@Override
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}

	@Override
	public void delete(Long id) throws EmployeeNotFoundException {
		employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
		employeeRepository.deleteById(id);
	}

}