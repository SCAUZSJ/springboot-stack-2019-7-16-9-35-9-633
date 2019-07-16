package com.tw.apistackbase.controller;

import com.tw.apistackbase.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final Logger log = Logger.getLogger(this.getClass().getName());
    private Map<Integer,Employee> employees = new HashMap<>();

    EmployeeController(){
        //init data
        this.employees.put(1,new Employee(1,"A",20,"male",6000));
        this.employees.put(3,new Employee(3,"B",20,"female",7000));
        this.employees.put(6,new Employee(6,"C",20,"female",5000));
        this.employees.put(7,new Employee(7,"D",20,"female",1000));
        this.employees.put(8,new Employee(8,"E",20,"female",2000));
        this.employees.put(9,new Employee(9,"F",20,"male",3000));
    }

    private List<Employee> convertMapToList(){
        List<Employee> employeeList = new ArrayList<>();
        for (Employee employee: employees.values()) {
            employeeList.add(employee);
        }
        return employeeList;
    }

    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String gender,
                                 @RequestParam(required = false) String page ,
                                 @RequestParam(required = false) String pageSize) {
        List<Employee> result = convertMapToList();
        if(gender!=null){
            result = result.stream().filter((obj)->{
                return obj.getGender().equals(gender);
            }).collect(Collectors.toList());
        }
        if(page!=null&&pageSize!=null){
            List<Employee> employeeList = new ArrayList<>();
            int index=(Integer.parseInt(page)-1)*Integer.parseInt(pageSize);
            if(index>=result.size()){
                result = null;
            }
            for(int i=index;i<Math.min(index+Integer.parseInt(pageSize),result.size()-1);i++){
                employeeList.add((Employee) result.get(i));
            }
            result = employeeList;
        }
        return ResponseEntity.ok().body(result);
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
    @GetMapping("/{employeeID}")
    public ResponseEntity getbyId(@PathVariable int employeeID) {
        return ResponseEntity.ok().body(employees.get(employeeID));
    }


}
