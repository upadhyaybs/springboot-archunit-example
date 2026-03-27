package com.avarna.customer.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.file.Files;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(scripts = "classpath:empty/reset.sql", executionPhase = BEFORE_TEST_METHOD)
    void should_create_one_user() throws Exception {
        final File jsonFile = new ClassPathResource("init/customer.json").getFile();
        final String userToCreate = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(post("/rest/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userToCreate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$", aMapWithSize(6)))

                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("John"));

        mockMvc.perform(get("/rest/api/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:empty/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:init/customer-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void testfindAll() throws Exception {
        mockMvc.perform(get("/rest/api/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(5)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("Roger")));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:empty/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:init/customer-data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    void should_findById() throws Exception {
        mockMvc.perform(get("/rest/api/customer/id/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Roger"))
                .andExpect(jsonPath("$.lastName").value("Clara"))
                .andExpect(jsonPath("$.email").value("alicia.marty@gmail.com"))
                .andExpect(jsonPath("$.dateOfBirth").value("2000-10-01"));
    }

    @Test
    @Sql(scripts = "classpath:empty/reset.sql", executionPhase = BEFORE_TEST_METHOD)
    void should_update_customer_via_patch() throws Exception {
        final String createBody = Files.readString(new ClassPathResource("init/customer.json").getFile().toPath());

        mockMvc.perform(post("/rest/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));

        String updateJson = """
                {"id":1,"firstName":"Jane","lastName":"Smith","email":"jane@example.com","dateOfBirth":"1999-05-05","phone":"555-0100"}
                """;

        mockMvc.perform(patch("/rest/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.email").value("jane@example.com"));

        mockMvc.perform(get("/rest/api/customer/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }

    @Test
    @Sql(scripts = "classpath:empty/reset.sql", executionPhase = BEFORE_TEST_METHOD)
    void should_delete_customer_by_id() throws Exception {
        final String createBody = Files.readString(new ClassPathResource("init/customer.json").getFile().toPath());

        mockMvc.perform(post("/rest/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        mockMvc.perform(get("/rest/api/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));

        mockMvc.perform(delete("/rest/api/customer/id/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rest/api/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @Sql(scripts = "classpath:empty/reset.sql", executionPhase = BEFORE_TEST_METHOD)
    void should_return_not_found_when_id_does_not_exist() throws Exception {
        mockMvc.perform(get("/rest/api/customer/id/9999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value(Matchers.containsString("9999")));
    }

    @Test
    @Sql(scripts = "classpath:empty/reset.sql", executionPhase = BEFORE_TEST_METHOD)
    void should_return_not_found_when_deleting_nonexistent_id() throws Exception {
        mockMvc.perform(delete("/rest/api/customer/id/9999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value(Matchers.containsString("9999")));
    }
}
