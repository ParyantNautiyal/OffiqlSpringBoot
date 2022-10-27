package com.example.demo;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.entity.users;
import com.example.demo.entity.email;
import com.example.demo.repository.repositoryEmail;
import com.example.demo.repository.repositoryUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private repositoryEmail repoE;
    @Autowired
    private repositoryUser repoU;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        repoE.deleteAll();
        repoU.deleteAll();
    }
    // User testing
    @Test
    public void givenUserObject_whenCreateUser_thenReturnStatus() throws Exception{

        // given - precondition or setup
        users user = new users(1,"Ramesh","Fade","ramesh@gmail.com",123456789);

        // when - action or behaviour that we are going test
        // Post Mapping
        ResultActions response = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
               andExpect(status().isOk());


    }

    // JUnit test for Get All user REST API
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{

        // given - precondition or setup
        List<users> listOfUser = new ArrayList<>();
        listOfUser.add(new users(1,"Ramesh","Fade","ramesh@gmail.com",123456789));
        listOfUser.add(new users(2,"Ramesh2","Fade2","ramesh2@gmail.com",23456789));
        repoU.saveAll(listOfUser);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/users"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());

    }

    // positive scenario - valid user id
    // JUnit test for GET user by id REST API
    @Test
    public void givenUserId_whenGetUserById_thenReturnUserObject() throws Exception{

        // given - precondition or setup
        users user = new users(1,"Ramesh","Fade","ramesh@gmail.com",123456789);
        repoU.save(user);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/user/{id}", user.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());


    }

    // negative scenario - valid user id
    // JUnit test for GET user by id REST API
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        int userId = 134;
        users user = new users(1,"Ramesh","Fade","ramesh@gmail.com",123456789);
        repoU.save(user);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/user/{id}", userId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    //  REST API - positive scenario

    // Email Testing

    @Test
    public void givenEmailObject_whenCreateEmail_thenReturnStatus() throws Exception{

        // given - precondition or setup
       email e = new email(1,"abc@gmail.com","to@gmail.com","subject","Body");
        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(e)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isOk());

    }
    // positive scenario - valid user id
    // JUnit test for GET count  by id REST API
    @Test
    public void givenUserId_whenGetUserById_thenReturnEmailCount() throws Exception{

        // given - precondition or setup
        // Add Users
        List<users> listOfUser = new ArrayList<>();
        listOfUser.add(new users(1,"Ramesh","Fade","ramesh@gmail.com",123456789));
        listOfUser.add(new users(2,"Ramesh2","Fade2","ramesh2@gmail.com",23456789));
        repoU.saveAll(listOfUser);

        //Add Emails
        List<email> listOfEmail = new ArrayList<>();
        listOfEmail.add(new email(1,"Ramesh","Fade","ramesh@gmail.com","Body1"));
        listOfEmail.add(new email(2,"Ramesh2","Fade2","ramesh2@gmail.com","Body2"));
        repoE.saveAll(listOfEmail);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/emails/count?user={userId}", 1));

        // then - verify the output
        response.andExpect(status().isOk()).andExpect(result -> result.getResponse().getContentAsString().equals("1"))
                .andDo(print());


    }

    // negative scenario - Invalid user id
    // JUnit test for GET email count by id REST API
    @Test
    public void givenInvalidUserId_whenGetUserById_thenReturnEmptyOrZero() throws Exception{
        // given - precondition or setup
        int userId = 134;
        List<users> listOfUser = new ArrayList<>();
        listOfUser.add(new users(1,"Ramesh","Fade","ramesh@gmail.com",123456789));
        listOfUser.add(new users(2,"Ramesh2","Fade2","ramesh2@gmail.com",23456789));
        repoU.saveAll(listOfUser);

        //Add Emails
        List<email> listOfEmail = new ArrayList<>();
        listOfEmail.add(new email(1,"Ramesh","Fade","ramesh@gmail.com","Body1"));
        listOfEmail.add(new email(2,"Ramesh2","Fade2","ramesh2@gmail.com","Body2"));
        repoE.saveAll(listOfEmail);
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/emails/count?user={userId}", userId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }
}

