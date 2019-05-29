package com.elasticapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class Employee {

  private String id;
  private String name;
  private int salary;
  private String role;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getSalary() {
	return salary;
}
public void setSalary(int salary) {
	this.salary = salary;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public Employee(String id, String name, int salary, String role) {
	super();
	this.id = id;
	this.name = name;
	this.salary = salary;
	this.role = role;
}
@Override
public String toString() {
	return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", role=" + role + "]";
}
public Employee() {
	super();
}
  
}
