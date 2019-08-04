package com.sp.backend.boilerplate;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sp.common.util.ContractTestingUtilities;

@SpringBootTest
public class GenerateSwaggerTest {

    @Autowired
    WebApplicationContext context;

    @Test
    public void generateSwaggerV10() throws Exception {
        final String OUTPUT_DIR = "./src/main/resources/";
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();
        mockMvc.perform(MockMvcRequestBuilders.get("/v2/api-docs?group=auto-complete-api-1.0")
            .accept(MediaType.APPLICATION_JSON))
            .andDo((result) ->
                {
                    String swaggerJson = result.getResponse().getContentAsString();
                    Files.createDirectories(Paths.get(OUTPUT_DIR));
                    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(OUTPUT_DIR, "swagger2_v1.0.json"), StandardCharsets.UTF_8)){
                        writer.write(swaggerJson);
                    }
                    ContractTestingUtilities.generateContract(swaggerJson, Optional.of("swagger2_v1.0.yaml"), Optional.empty());
                });

    }

    @Test
    public void generateSwaggerV20() throws Exception {
        final String OUTPUT_DIR = "./src/main/resources/";
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();
        mockMvc.perform(MockMvcRequestBuilders.get("/v2/api-docs?group=auto-complete-api-2.0")
            .accept(MediaType.APPLICATION_JSON))
            .andDo((result) ->
                {
                    String swaggerJson = result.getResponse().getContentAsString();
                    Files.createDirectories(Paths.get(OUTPUT_DIR));
                    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(OUTPUT_DIR, "swagger2_v2.0.json"), StandardCharsets.UTF_8)){
                        writer.write(swaggerJson);
                    }
                    ContractTestingUtilities.generateContract(swaggerJson, Optional.of("swagger2_v2.0.yaml"), Optional.empty());
                });

    }

    @Test
    public void generateSwaggerV21() throws Exception {
        final String OUTPUT_DIR = "./src/main/resources/";
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();
        mockMvc.perform(MockMvcRequestBuilders.get("/v2/api-docs?group=auto-complete-api-2.1")
            .accept(MediaType.APPLICATION_JSON))
            .andDo((result) ->
                {
                    String swaggerJson = result.getResponse().getContentAsString();
                    Files.createDirectories(Paths.get(OUTPUT_DIR));
                    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(OUTPUT_DIR, "swagger2_v2.1.json"), StandardCharsets.UTF_8)){
                        writer.write(swaggerJson);
                    }
                    ContractTestingUtilities.generateContract(swaggerJson, Optional.of("swagger2_v2.1.yaml"), Optional.empty());
                });

    }
}
