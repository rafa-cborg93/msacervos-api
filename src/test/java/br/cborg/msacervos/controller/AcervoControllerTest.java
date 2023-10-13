package br.cborg.msacervos.controller;

import br.cborg.msacervos.domain.DefaultResponse;
import br.cborg.msacervos.domain.acervo.request.AcervoRequest;
import br.cborg.msacervos.domain.acervo.response.AcervoResponse;
import br.cborg.msacervos.service.AcervoService;
import br.cborg.msacervos.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class AcervoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AcervoService acervoService;
    private String jsonRequest;
    private AcervoRequest acervoRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jsonRequest = TestUtils.getAcervoRequestJson();
        acervoRequest = TestUtils.getAcervoRequest();
    }

    @Test
    void getAcervosSuccessTest() throws Exception {
        List<AcervoResponse> acervoResponseList = new ArrayList<>();
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.OK.value(), "List.", acervoResponseList);
        when(acervoService.getAcervoList("123", "123")).thenReturn(serviceResponse);

        this.mockMvc.perform(get("/acervo/search")
                        .param("isbn", "123")
                        .param("numeroChamada", "123"))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void getAcervosReturnNotFoundStatusTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.NOT_FOUND.value(), "NOT_FOUND");
        when(acervoService.getAcervoList("123", "123")).thenReturn(serviceResponse);

        this.mockMvc.perform(get("/acervo/search")
                        .param("isbn", "123")
                        .param("numeroChamada", "123"))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("NOT_FOUND"));
    }

    @Test
    void getAcervosReturnInternalServerErrorStatusTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "BAD_REQUEST");
        when(acervoService.getAcervoList("123", "123")).thenReturn(serviceResponse);

        this.mockMvc.perform(get("/acervo/search")
                        .param("isbn", "123")
                        .param("numeroChamada", "123"))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("BAD_REQUEST"));
    }

    @Test
    void postAcervoSuccessTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.CREATED.value(), "Acervo created successfully.");
        when(acervoService.createAcervo(acervoRequest)).thenReturn(serviceResponse);

        this.mockMvc.perform(post("/acervo/create")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(201));
    }

    @Test
    void postAcervoReturnBadRequestStatusTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST");
        when(acervoService.createAcervo(any(AcervoRequest.class))).thenReturn(serviceResponse);

        this.mockMvc.perform(post("/acervo/create")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("BAD_REQUEST"));
    }

    @Test
    void postAcervoReturnInternalServerErrorStatusTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR");
        when(acervoService.createAcervo(any(AcervoRequest.class))).thenReturn(serviceResponse);

        this.mockMvc.perform(post("/acervo/create")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("INTERNAL_SERVER_ERROR"));
    }

    @Test
    void updateAcervoSuccessTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.OK.value(), "Acervo updated successfully.");
        when(acervoService.updateAcervo(anyLong(), any(AcervoRequest.class))).thenReturn(serviceResponse);

        this.mockMvc.perform(put("/acervo/1/update")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void updateAcervoReturnNotFoundStatusTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.NOT_FOUND.value(), "NOT_FOUND");
        when(acervoService.updateAcervo(anyLong(), any(AcervoRequest.class))).thenReturn(serviceResponse);

        this.mockMvc.perform(put("/acervo/1/update")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("NOT_FOUND"));
    }

    @Test
    void updateAcervoReturnBadRequestStatusTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST");
        when(acervoService.updateAcervo(anyLong(), any(AcervoRequest.class))).thenReturn(serviceResponse);

        this.mockMvc.perform(put("/acervo/1/update")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("BAD_REQUEST"));
    }

    @Test
    void updateAcervoReturnInternalServerErrorStatusTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR");
        when(acervoService.updateAcervo(anyLong(), any(AcervoRequest.class))).thenReturn(serviceResponse);

        this.mockMvc.perform(put("/acervo/1/update")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("INTERNAL_SERVER_ERROR"));
    }

    @Test
    void deleteAcervoSuccessTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.OK.value(), "Acervo deleted successfully.");
        when(acervoService.deleteAcervo(anyLong())).thenReturn(serviceResponse);

        this.mockMvc.perform(delete("/acervo/1/delete"))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Acervo deleted successfully."));
    }

    @Test
    void deleteAcervoReturnNotFoundStatusTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.NOT_FOUND.value(), "NOT_FOUND");
        when(acervoService.deleteAcervo(anyLong())).thenReturn(serviceResponse);

        this.mockMvc.perform(delete("/acervo/1/delete"))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("NOT_FOUND"));
    }

    @Test
    void deleteAcervoReturnInternalServerErrorStatusTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR");
        when(acervoService.deleteAcervo(anyLong())).thenReturn(serviceResponse);

        this.mockMvc.perform(delete("/acervo/1/delete"))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("INTERNAL_SERVER_ERROR"));
    }
}