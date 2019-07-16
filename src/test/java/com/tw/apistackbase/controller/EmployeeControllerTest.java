package com.tw.apistackbase.controller;

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

        Map<String, String> map = new HashMap<>();
        map.put("id","2");
        map.put("name","B");
        map.put("age","18");
        map.put("gender","male");
        map.put("salary","6000");
        String objectJson = JSONObject.toJSONString(map);
        this.mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON_UTF8).
                content(objectJson)).andExpect(status().isCreated());
    }

    @Test
    public void should_return_new_object_info_when_put_success() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("name","A");
        map.put("age","20");
        map.put("gender","male");
        map.put("salary","8000");
        String objectJson = JSONObject.toJSONString(map);
        String content = this.mockMvc.perform(put("/employees/1").contentType(MediaType.APPLICATION_JSON_UTF8).
                content(objectJson)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JSONObject obj = (JSONObject) JSONValue.parse(content);
        Assertions.assertEquals(8000,obj.get("salary"));
    }
    @Test
    public void should_return_new_object_info_when_put_fail() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("name","C");
        map.put("age","20");
        map.put("gender","male");
        map.put("salary","6000");
        String objectJson = JSONObject.toJSONString(map);
        this.mockMvc.perform(put("/employees/3").contentType(MediaType.APPLICATION_JSON_UTF8).
                content(objectJson)).andExpect(status().isBadRequest());
    }
    @Test
    public void should_return_code_200_when_delete_success() throws Exception {
        //success
        this.mockMvc.perform(delete("/employees/1")).andExpect(status().isOk());
    }

    @Test
    public void should_return_code_200_when_delete_fail() throws Exception {
        this.mockMvc.perform(delete("/employees/2")).andExpect(status().isBadRequest());
    }

}
