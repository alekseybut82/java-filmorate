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
public class FilmControllerTest {

    private static final String PATH = "/films";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void createFilmCorrectRequest() throws Exception {
        String requestBody = getContentFromFile("film/create/request/film-create-request.json");
        String responseBody = getContentFromFile("film/create/response/film-create-response.json");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseBody))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(2)
    public void updateFilm() throws Exception {
        String requestBody = getContentFromFile("film/update/request/film-update-request.json");
        String responsetBody = getContentFromFile("film/update/response/film-update-response.json");

        mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responsetBody));
    }


    @Test
    @Order(3)
    public void getAllFilms() throws Exception {
        String responsetBody = getContentFromFile("film/getAll/response/film-get-all-response.json");

        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responsetBody));
    }


    @Test
    public void createFilmFailDescriptionRequest() throws Exception {
        runNegativePostMethodTest("film/create/request/film-create-fail-description-request.json", HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createFilmFailDurationRequest() throws Exception {
        runNegativePostMethodTest("film/create/request/film-create-fail-duration-request.json", HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createFilmFailNameRequest() throws Exception {
        runNegativePostMethodTest("film/create/request/film-create-fail-name-request.json", HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createFilmFailReleaseDateRequest() throws Exception {
        runNegativePostMethodTest("film/create/request/film-create-fail-releasedate-request.json", HttpStatus.BAD_REQUEST);
    }

    @Test
    public void updateFilmUnknowIdRequest() throws Exception {
        String requestBody = getContentFromFile("film/update/request/film-update-unknown-request.json");

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