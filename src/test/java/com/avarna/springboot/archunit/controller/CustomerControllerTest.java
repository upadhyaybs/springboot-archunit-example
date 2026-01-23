package com.tp.springboot.archunit.controller;

import com.tp.springboot.archunit.domain.Customer;
import com.tp.springboot.archunit.service.ICustomerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SqlGroup({
        @Sql(value = "classpath:empty/reset.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:init/customer-data.sql", executionPhase = BEFORE_TEST_METHOD)
})
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ICustomerService service;

    @MockBean
    ModelMapper mapper;


    @Test
    void should_create_one_user() throws Exception {
        final File jsonFile = new ClassPathResource("init/customer.json").getFile();
        final String userToCreate = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(post("/rest/api/customer")
                        .contentType(APPLICATION_JSON)
                        .content(userToCreate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$", aMapWithSize(3)));

        assertThat(this.service.listAll()).hasSize(1);
    }


    @Test
    public void testfindAll() throws Exception {
        Customer customer = newCustomer();
        List<Customer> customers = Arrays.asList(customer);

        Mockito.when(service.listAll()).thenReturn(customers);

        mockMvc.perform(get("/rest/api/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("John")));
    }


    @Test
    public void should_findById() throws Exception {
        Customer customer = newCustomer();

        Mockito.when(service.findById(1l)).thenReturn(customer);

        mockMvc.perform(get("/rest/api/customer/{id}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$id").value(1))
                .andExpect(jsonPath("$firstName"). value("John"))
                .andExpect(jsonPath("$lastName"). value("Doe"))
                .andExpect(jsonPath("$email"). value("john.doe@gmail.com"))
                .andExpect(jsonPath("$dateOfBirth"). value("2000-01-02"));

    }


    private Customer newCustomer(){
        Customer customer=new Customer();
        customer.setId(1l);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@gmail.com");
        customer.setDateOfBirth("2000-01-02");
        return customer;
    }

}
