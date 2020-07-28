package com.gk.indus.hr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gk.indus.hr.model.Employee;
import com.gk.indus.hr.service.EmployeeService;


@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value = "/employees", method = RequestMethod.GET,headers="Accept=application/json")
	public List<Employee> getEmployeesCollection() 
    {
		return employeeService.getEmployees();
    }
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	

}
