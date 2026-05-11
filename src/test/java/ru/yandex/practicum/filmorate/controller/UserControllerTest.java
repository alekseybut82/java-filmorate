package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    private static final String PATH = "/users";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void createUserCorrectRequest() throws Exception {
        String requestBody = getContentFromFile("user/create/request/user-create-request.json");
        String responseBody = getContentFromFile("user/create/response/user-create-response.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseBody))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(2)
    public void createUserWithEmptyNameRequest() throws Exception {
        String requestBody = getContentFromFile("user/create/request/user-create-empty-name-request.json");
        String responseBody = getContentFromFile("user/create/response/user-create-empty-name-response.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseBody))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(3)
    public void updateUser() throws Exception {
        String requestBody = getContentFromFile("user/update/request/user-update-request.json");
        String responsetBody = getContentFromFile("user/update/response/user-update-response.json");

        mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responsetBody));
    }


    @Test
    @Order(4)
    public void getAllUsers() throws Exception {
        String responsetBody = getContentFromFile("user/getAll/response/user-get-all-response.json");

        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responsetBody));
    }


    @Test
    public void createUserFailBirthdayRequest() throws Exception {
        runNegativePostMethodTest("user/create/request/user-create-fail-birthday-request.json", HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUserFailEmailRequest() throws Exception {
        runNegativePostMethodTest("user/create/request/user-create-fail-email-request.json", HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUserFailLoginRequest() throws Exception {
        runNegativePostMethodTest("user/create/request/user-create-fail-login-request.json", HttpStatus.BAD_REQUEST);
    }

    @Test
    public void updateUserUnknowIdRequest() throws Exception {
        String requestBody = getContentFromFile("user/update/request/user-update-fail-id-request.json");

        mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private void runNegativePostMethodTest(String requestPath, HttpStatus expectedStatus) throws Exception {
        String requestBody = getContentFromFile(requestPath);

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(expectedStatus.value()));
    }

    private String getContentFromFile(String filename) {
        try {
            return Files.readString(ResourceUtils.getFile("classpath:" + filename).toPath(),
                    StandardCharsets.UTF_8);
        } catch (Exception exception) {
            throw new RuntimeException("Ошибка открытия файла "  + filename, exception);
        }
    }
}