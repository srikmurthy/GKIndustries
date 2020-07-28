package com.gk.indus.hr.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gk.indus.hr.model.Employee;

@Service
public class EmployeeService {

	private Map<String, Employee> eTable = new HashMap<String, Employee>();
	
	
	private void initalize() {
		Employee e1 = new Employee();
		e1.setId("emp100");
		e1.setName("Chandu");
		e1.setDob(Calendar.getInstance().getTime());
		e1.setLocation("Quincy");
		e1.setSal(1000.00);
		eTable.put(e1.getId(), e1);
		
		Employee e2 = new Employee();
		e2.setId("emp101");
		e2.setName("Chinu");
		e2.setDob(Calendar.getInstance().getTime());
		e2.setLocation("Braintree");
		e2.setSal(1000.00);
		eTable.put(e2.getId(), e2);
		
		Employee e3 = new Employee();
		e3.setId("emp102");
		e3.setName("Sai");
		e3.setDob(Calendar.getInstance().getTime());
		e3.setLocation("Boston");
		e3.setSal(1000.00);
		
		eTable.put(e3.getId(), e3);
	}
	
	
	public List<Employee> getEmployees(){
		List<Employee>  eList = new ArrayList<Employee>();
		//db connection  - select * from my employee
		initalize();
		for (Employee  emp : eTable.values()) {
			eList.add(emp);
		}
		return eList;
	}
	
	public void createEmployee(Employee newEmp) {
		newEmp.setId("emp"+eTable.size() +1);
		
		eTable.put(newEmp.getId(), newEmp);
	}
	
	
   public void saveEmployee(Employee  updateEmp) {
	   eTable.put(updateEmp.getId(), updateEmp);
	}
	
   public void deleteEmployee(Employee  delEmp) {
	   eTable.remove(delEmp.getId(), delEmp);
	}
	
	
	
}
