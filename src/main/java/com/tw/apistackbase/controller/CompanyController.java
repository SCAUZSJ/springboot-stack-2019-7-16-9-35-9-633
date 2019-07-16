package com.tw.apistackbase.controller;

import com.tw.apistackbase.entity.Company;
import com.tw.apistackbase.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final Logger log = Logger.getLogger(this.getClass().getName());
    private Map<Integer, Company> companies = new HashMap<>();

    CompanyController(){
        //init data
        List<Employee> employees = new ArrayList<>(
                Arrays.asList(new Employee(1,"A",20,"male",6000),new Employee(2,"B",20,"male",8000)));

        this.companies.put(1,new Company(1,"A",2, employees));
        this.companies.put(2,new Company(2,"B",2, employees));
        this.companies.put(3,new Company(3,"C",2, employees));
        this.companies.put(4,new Company(4,"D",2, employees));
        this.companies.put(5,new Company(5,"E",2, employees));
        this.companies.put(6,new Company(6,"F",2, employees));

    }

    private List<Company> convertMapToList(){
        List<Company> companieList = new ArrayList<>();
        for (Company company: companies.values()) {
            companieList.add(company);
        }
        return companieList;
    }

    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String gender,
                                 @RequestParam(required = false) String page ,
                                 @RequestParam(required = false) String pageSize) {
        List<Company> result = convertMapToList();
//        if(gender!=null){
//            result = result.stream().filter((obj)->{
//                return obj.getGender().equals(gender);
//            }).collect(Collectors.toList());
//        }
//        if(page!=null&&pageSize!=null){
//            List<Employee> employeeList = new ArrayList<>();
//            int index=(Integer.parseInt(page)-1)*Integer.parseInt(pageSize);
//            if(index>=result.size()){
//                result = null;
//            }
//            for(int i=index;i<Math.min(index+Integer.parseInt(pageSize),result.size()-1);i++){
//                employeeList.add((Employee) result.get(i));
//            }
//            result = employeeList;
//        }
        return ResponseEntity.ok().body(result);
    }
    @PostMapping
    public ResponseEntity create(@RequestBody Company company){
        this.companies.put(company.getCompanyId(),company);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{companyId}")
    public ResponseEntity update(@PathVariable int companyId ,@RequestBody Company company){

        if(!this.companies.containsKey(companyId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        this.companies.remove(companyId);
        company.setCompanyId(companyId);
        this.companies.put(companyId,company);
        return ResponseEntity.ok().body(company);
    }
    @DeleteMapping("/{companyId}")
    public ResponseEntity delete(@PathVariable int companyId){
        if(this.companies.containsKey(companyId)){
            this.companies.remove(companyId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping("/{companyId}")
    public ResponseEntity getbyId(@PathVariable int companyId) {
        return ResponseEntity.ok().body(companies.get(companyId));
    }

}
