package com.example.testrest.service;

import com.example.testrest.exception.EmployeeNotFoundException;
import com.example.testrest.model.Employee;
import com.example.testrest.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultEmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DefaultEmployeeService employeeService;

    @Test
    public void whenUserSaved_shouldReturnUser() {
        Employee employeeStub = new Employee();
        employeeStub.setFirstname("Test Employee");

        when(employeeRepository.save(ArgumentMatchers.any(Employee.class))).thenReturn(employeeStub);

        Employee createdEmployee = employeeService.save(employeeStub);

        assertThat(createdEmployee.getFirstname(), equalTo(employeeStub.getFirstname()));
        verify(employeeRepository).save(employeeStub);
    }

    @Test
    public void shouldReturnAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> expectedEmployees = employeeService.getAll();

        assertThat(expectedEmployees, equalTo(employees));
        verify(employeeRepository).findAll();
    }

    @Test
    public void whenGivenId_shouldDeleteEmployee_ifFound() throws Exception {
        Employee employeeStub = new Employee();
        employeeStub.setFirstname("Employee Test");
        employeeStub.setId(1L);
        when(employeeRepository.findById(employeeStub.getId())).thenReturn(Optional.of(employeeStub));
        employeeService.delete(employeeStub.getId());
        verify(employeeRepository).deleteById(employeeStub.getId());
    }

    @Test
    public void shouldThrowAnException_whenEmployeeNotFound() {
        Employee employeeStub = new Employee();
        employeeStub.setFirstname("Employee Test");
        employeeStub.setId(1L);
        EmployeeNotFoundException exception = assertThrows(
                EmployeeNotFoundException.class, () -> employeeService.delete(employeeStub.getId())
        );

        assertTrue(exception.getMessage().contains("Employee not found"));
    }


}