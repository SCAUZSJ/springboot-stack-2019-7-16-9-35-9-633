package com.tw.apistackbase.controller;

import com.tw.apistackbase.entity.Employee;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_employees_when_get() throws Exception {
        String content = this.mockMvc.perform(get("/employees")).andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JSONArray json = new JSONArray(content);
        Assertions.assertEquals(20,json.getJSONObject(0).getInt("age"));
    }

    @Test
    public void should_return_code_201_when_post() throws Exception {
        Employee employee = new Employee(2,"B",18,"male",6000);
        String objectJson = new org.json.JSONObject(employee).toString();
        this.mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON_UTF8).
                content(objectJson)).andExpect(status().isCreated());
    }

    @Test
    public void should_return_new_object_info_when_put_success() throws Exception {
        Employee employee = new Employee(1,"A",20,"male",8000);
        String objectJson = new org.json.JSONObject(employee).toString();
        String content = this.mockMvc.perform(put("/employees/1").contentType(MediaType.APPLICATION_JSON_UTF8).
                content(objectJson)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JSONObject obj = (JSONObject) JSONValue.parse(content);
        Assertions.assertEquals(8000,obj.get("salary"));
    }
    @Test
    public void should_return_new_object_info_when_put_fail() throws Exception {

        Employee employee = new Employee(5,"C",20,"male",8000);
        String objectJson = new org.json.JSONObject(employee).toString();
        this.mockMvc.perform(put("/employees/5").contentType(MediaType.APPLICATION_JSON_UTF8).
                content(objectJson)).andExpect(status().isBadRequest());
    }
    @Test
    public void should_return_code_200_when_delete_success() throws Exception {
        //success
        this.mockMvc.perform(delete("/employees/6")).andExpect(status().isOk());
    }

    @Test
    public void should_return_code_200_when_delete_fail() throws Exception {
        this.mockMvc.perform(delete("/employees/4")).andExpect(status().isBadRequest());
    }


}
