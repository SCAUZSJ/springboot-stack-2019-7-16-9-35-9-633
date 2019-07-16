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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        Company company =new Company("A",2, employees);
        String objectJson = new JSONObject(company).toString();
        this.mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).
                content(objectJson)).andExpect(status().isCreated());
    }
}
