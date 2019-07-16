package com.tw.apistackbase.controller;

import com.tw.apistackbase.entity.Company;
import com.tw.apistackbase.entity.Employee;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final Logger log = Logger.getLogger(this.getClass().getName());
    private Map<String, Company> companies = new HashMap<>();

    CompanyController(){
        //init data
        List<Employee> employees = new ArrayList<>(
                Arrays.asList(new Employee(1,"A",20,"male",6000),new Employee(2,"B",20,"male",8000)));

        this.companies.put("A",new Company("A",2, employees));
        this.companies.put("B",new Company("B",2, employees));
        this.companies.put("C",new Company("C",2, employees));
        this.companies.put("D",new Company("D",2, employees));
        this.companies.put("E",new Company("E",2, employees));
        this.companies.put("F",new Company("F",2, employees));

    }

}
