package com.elasticapp.repository;

import java.util.List;
import java.util.Map;

import com.elasticapp.model.Employee;

public interface EmployeeDAO {

	public abstract List<Employee> getAllEmployees();

	public abstract Employee saveEmployee(Employee employee);

	public abstract Map<String, Object> findById(String id);

	public abstract Map<String, Object> updateEmployee(String id, Employee employee);

	public abstract void deleteById(String id);

}
