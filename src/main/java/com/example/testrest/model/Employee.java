package com.example.testrest.model;

import javax.persistence.*;

@Entity
public class Employee {

    private static final long serialVersionUID = 4910225916550731446L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(length = 50)
    private String firstname;

    @Column(length = 50)
    private String lastname;

    @Column(length = 20)
    private String designation;

    private Integer salary;

    public Employee() {
    }

    public Employee(Long id) {
        this.id = id;
    }

    public Employee(Long id, String firstname, String lastname, String designation, Integer salary) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.designation = designation;
        this.salary = salary;
    }

    public Employee(String firstname, String lastname, String designation, Integer salary) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.designation = designation;
        this.salary = salary;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getSalary() {
        return this.salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(this.id).append(", firstName: ").append(this.firstname).append(", lastName: ")
                .append(this.lastname).append(", Designation: ").append(this.designation).append(", Salary: ")
                .append(this.salary);
        return sb.toString();
    }
}