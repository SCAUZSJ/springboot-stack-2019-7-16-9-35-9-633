package com.tw.apistackbase.controller;

import com.tw.apistackbase.entity.Company;
import com.tw.apistackbase.entity.Employee;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_companies_when_get() throws Exception {
        String content = this.mockMvc.perform(get("/companies")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JSONArray json = new JSONArray(content);
        Assertions.assertEquals("A",json.getJSONObject(0).getString("companyName"));
    }
    @Test
    public void should_return_code_201_when_post() throws Exception {
        List<Employee> employees = new ArrayList<>(
                Arrays.asList(new Employee(10,"A",20,"male",6000),new Employee(20,"B",20,"male",8000)));

        Company company =new Company(10,"A",2, employees);
        String objectJson = new JSONObject(company).toString();
        this.mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).
                content(objectJson)).andExpect(status().isCreated());
    }
    @Test
    public void should_return_new_object_info_when_put_success() throws Exception {
        List<Employee> employees = new ArrayList<>(
                Arrays.asList(new Employee(10,"A",20,"male",6000),new Employee(20,"B",20,"male",8000)));

        Company company =new Company(5,"kkk",2, employees);
        String objectJson = new org.json.JSONObject(company).toString();
        String content = this.mockMvc.perform(put("/companies/5").contentType(MediaType.APPLICATION_JSON_UTF8).
                content(objectJson)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JSONObject obj = new JSONObject(content);
        Assertions.assertEquals("kkk",obj.get("companyName"));
    }
    @Test
    public void should_return_new_object_info_when_put_fail() throws Exception {

        List<Employee> employees = new ArrayList<>(
                Arrays.asList(new Employee(10,"A",20,"male",6000),new Employee(20,"B",20,"male",8000)));
        Company company =new Company(20,"kkk",2, employees);
        String objectJson = new org.json.JSONObject(company).toString();
        this.mockMvc.perform(put("/companies/20").contentType(MediaType.APPLICATION_JSON_UTF8).
                content(objectJson)).andExpect(status().isBadRequest());
    }
    @Test
    public void should_return_code_200_when_delete_success() throws Exception {
        this.mockMvc.perform(delete("/companies/5")).andExpect(status().isOk());
    }
}
