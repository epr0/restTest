package com.example.testrest.service;

import com.example.testrest.exception.EmployeeNotFoundException;

import java.io.Serializable;
import java.util.List;

public interface CRUDService<E> {

	E save(E entity);

	E getById(Serializable id);

	List<E> getAll();

	void delete(Long id) throws EmployeeNotFoundException;
}