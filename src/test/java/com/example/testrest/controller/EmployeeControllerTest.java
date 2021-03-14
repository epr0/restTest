package com.example.testrest.controller;

import com.example.testrest.model.Employee;
import com.example.testrest.service.EmployeeService;
import com.google.gson.reflect.TypeToken;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EmployeeService empService;

    @Test
    void addEmployee() throws Exception {
        Employee employee = new Employee(1l, "Tom", "Brady", "Developer", 12000);
        when(empService.save(any(Employee.class))).thenReturn(employee);

        MvcResult result = mockMvc
                .perform(post("/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJson(employee)))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertThat(status, is(equalTo(HttpStatus.CREATED.value())));

        verify(empService).save(any(Employee.class));

        Employee resultEmployee = TestUtils.jsonToObject(result.getResponse().getContentAsString(), Employee.class);
        assertNotNull(resultEmployee);
        assertEquals(1l, resultEmployee.getId().longValue());
        assertEquals(12000, resultEmployee.getSalary());


    }

    @Test
    public void saveEmployee_whenPostMethod() throws Exception {
        Employee employee = new Employee();
        employee.setFirstname("TOM");
        when(empService.save(employee)).thenReturn(employee);

        MvcResult result = mockMvc.perform(post("/employee/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", is(employee.getFirstname())))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertThat(status, is(equalTo(HttpStatus.CREATED.value())));

        verify(empService).save(any(Employee.class));
    }

    @Test
    public void getAllEmployees_WhenGetMethod() throws Exception {
        Employee employee = new Employee();
        employee.setFirstname("John");
        List<Employee> employees = Arrays.asList(employee);
        when(empService.getAll()).thenReturn(employees);

        mockMvc.perform(get("/employee/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(employees))).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstname", is(employee.getFirstname())));

    }

    @Test
    void getAllEmployees() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "Zach", "Lavine", "Developer", 12000));
        employees.add(new Employee(2L, "Koby", "White", "QA", 15000));

        when(empService.getAll()).thenReturn(employees);

        MvcResult result = mockMvc.perform(get("/employee/employees").accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(result.getResponse().getStatus(), is(equalTo(HttpStatus.OK.value())));

        //Verify that service was called once
        verify(empService).getAll();

        TypeToken<List<Employee>> token = new TypeToken<List<Employee>>() {
        };

        List employeesResult = TestUtils.jsonToList(result.getResponse().getContentAsString(), token);

        assertEquals(employeesResult.size(), employees.size());

    }

    @Test
    void getEmployee() throws Exception {
        Employee employee = new Employee(1L, "Kobe", "Bryant", "Architect", 50000);
        when(empService.getById(any(Long.class))).thenReturn(employee);

        MvcResult result = mockMvc
                .perform(get("/employee/employee/{id}", new Long(1))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(result.getResponse().getStatus(), is(equalTo(HttpStatus.OK.value())));

        Employee resultEmployee = TestUtils.jsonToObject(result.getResponse().getContentAsString(), Employee.class);
        assertNotNull(resultEmployee);
        assertEquals(1l, resultEmployee.getId().longValue());
    }

    @Test
    void updateEmployee() throws Exception {
        Employee employee = new Employee(1L, "Eddy", "Watta", "Manager", 18000);
        when(empService.getById(any(Long.class))).thenReturn(employee);

        MvcResult result = mockMvc
                .perform(put("/employee/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(TestUtils.objectToJson(employee)))
                .andReturn();

        assertThat(result.getResponse().getStatus(), is(equalTo(HttpStatus.OK.value())));

        verify(empService).save(any(Employee.class));
    }

    @Test
    void deleteEmployee() throws Exception {
        Employee employee = new Employee(1L);
        when(empService.getById(any(Long.class))).thenReturn(employee);

        MvcResult result = mockMvc.perform(delete("/employee/delete/{id}", new Long(1))).andReturn();

        assertThat(result.getResponse().getStatus(), is(equalTo(HttpStatus.GONE.value())));

        verify(empService).delete(any(Long.class));
    }
}