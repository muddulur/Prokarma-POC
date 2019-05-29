package com.elasticapp.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elasticapp.model.Employee;
import com.elasticapp.repository.EmployeeDAO;


@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeDAO employeedao;
	
	@GetMapping("/employees/all")
	public List<Employee> retreiveAllEmployees() throws Exception{
		
		return employeedao.getAllEmployees();	
		
	}
	
	@PostMapping("/employees/save")
	public Employee addEmployees(@RequestBody Employee employee) throws Exception{
	
		return employeedao.saveEmployee(employee);
		
		
	}
	
	@GetMapping("/employees/{id}")
	public Map<String, Object> findEmployeeById(@PathVariable String id) {
		
		return employeedao.findById(id);
		
	}
	
	@PutMapping("/employees/{id}")
	public Map<String, Object> updateEmployeeById(@PathVariable String id,@RequestBody Employee employee) {
		return employeedao.updateEmployee(id,employee);
		
	}
	
	@DeleteMapping("/employees/{id}")
	public boolean deleteEmployeeById(@PathVariable String id) {
		
		employeedao.deleteById(id);
		return true;
	}
	
}
