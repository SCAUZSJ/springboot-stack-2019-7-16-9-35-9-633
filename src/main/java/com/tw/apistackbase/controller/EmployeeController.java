package com.tw.apistackbase.controller;

import com.tw.apistackbase.entity.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final Logger log = Logger.getLogger(this.getClass().getName());
    private Map<Integer,Employee> employees = new HashMap<>();

    @GetMapping
    public ResponseEntity getAll() {
        this.employees.put(1,new Employee(1,"A",20,"male",6000));
        return ResponseEntity.ok().body(employees.values().toArray());
    }
}
