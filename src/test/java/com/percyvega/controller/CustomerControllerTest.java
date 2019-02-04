package com.percyvega.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void findByLastName() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/customers/lastName/Palmer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("David")));
    }

    @Test
    public void findById_found() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/customers/id/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Jack")));
    }

    @Test
    public void findById_not_found() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/customers/id/284")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
