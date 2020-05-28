package com.webapp.assignment.Repository;

import com.webapp.assignment.Controller.MainController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest

@RunWith(SpringRunner.class)
public class UserRepositoryIntegrityTest {

    @Autowired
   private MainController controller;
    @Test
    public void contextLoads() {

        assertNotNull("HelloController is not loaded",controller);
    }
//    private MockMvc mockMvc;
//
//    @Before
//    public void setup() {
//        this.mockMvc = standaloneSetup(new MainController()).build();
//    }

//    @Test
//    public void testSayHelloWorld() throws Exception {
//        this.mockMvc.perform(get("/")
//                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"));
//
//    }
//    @Autowired
//    private MainController controller;
//
//    @Test
//    public void contexLoads() throws Exception {
//        assertThat(controller).isNotNull();
//    }
}
