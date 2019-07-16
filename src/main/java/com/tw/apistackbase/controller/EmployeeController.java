package com.tw.apistackbase.controller;

import com.tw.apistackbase.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final Logger log = Logger.getLogger(this.getClass().getName());
    private Map<Integer,Employee> employees = new HashMap<>();

    EmployeeController(){
        //init data
        this.employees.put(1,new Employee(1,"A",20,"male",6000));
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok().body(employees.values().toArray());
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Employee employee){
        this.employees.put(employee.getId(),employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{employeeID}")
    public ResponseEntity update(@PathVariable int employeeID,@RequestBody Employee employee){

        if(!this.employees.containsKey(employeeID)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        this.employees.remove(employeeID);
        employee.setId(employeeID);
        this.employees.put(employeeID,employee);
        return ResponseEntity.ok().body(employee);
    }
    @DeleteMapping("/{employeeID}")
    public ResponseEntity delete(@PathVariable int employeeID){
        if(this.employees.containsKey(employeeID)){
            this.employees.remove(employeeID);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

}
