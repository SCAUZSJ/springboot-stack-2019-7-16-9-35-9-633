package com.tw.apistackbase.controller;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
