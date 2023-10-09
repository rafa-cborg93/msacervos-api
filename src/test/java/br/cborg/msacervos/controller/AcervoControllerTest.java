package br.cborg.msacervos.controller;

import br.cborg.msacervos.domain.DefaultResponse;
import br.cborg.msacervos.domain.acervo.request.AcervoRequest;
import br.cborg.msacervos.domain.acervo.response.AcervoResponse;
import br.cborg.msacervos.domain.acervo.service.AcervoService;
import br.cborg.msacervos.domain.acervo.vo.FormatoFisico;
import br.cborg.msacervos.domain.acervo.vo.Imprenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class AcervoControllerTest {
    @Autowired
    private AcervoController acervoController;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AcervoService acervoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //testes de integração
    @Test
    void getAcervosSuccessTest() throws Exception {
        List<AcervoResponse> acervoResponseList = new ArrayList<>();
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.OK.value(), "Empty List.", acervoResponseList);
        when(acervoService.getAcervoList(anyString(), anyString())).thenReturn(serviceResponse);

        this.mockMvc.perform(get("/acervo/search")
                        .param("isbn", "123")
                        .param("numeroChamada", "123"))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void getAcervosReturnNotFoundStatusTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.NOT_FOUND.value(), "NOT_FOUND");
        when(acervoService.getAcervoList(anyString(), anyString())).thenReturn(serviceResponse);

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
        when(acervoService.getAcervoList(anyString(), anyString())).thenReturn(serviceResponse);

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
        when(acervoService.createAcervo(any(AcervoRequest.class))).thenReturn(serviceResponse);

        this.mockMvc.perform(post("/acervo/create")
                        .contentType("application/json")
                        .content(jsonAcervoRequest()))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(201));
    }
    @Test
    void postAcervoReturnBadRequestStatusTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST");
        when(acervoService.createAcervo(any(AcervoRequest.class))).thenReturn(serviceResponse);

        this.mockMvc.perform(post("/acervo/create")
                        .contentType("application/json")
                        .content(jsonAcervoRequest()))
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
                        .content(jsonAcervoRequest()))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("INTERNAL_SERVER_ERROR"));
    }
    @Test
    void updateAcervoSuccessTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.OK.value(), "Acervo updated successfully.");
        when(acervoService.updateAcervo(anyLong(),any(AcervoRequest.class))).thenReturn(serviceResponse);

        this.mockMvc.perform(put("/acervo/1/update")
                        .contentType("application/json")
                        .content(jsonAcervoRequest()))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(200));
    }
    @Test
    void updateAcervoReturnNotFoundStatusTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.NOT_FOUND.value(), "NOT_FOUND");
        when(acervoService.updateAcervo(anyLong(),any(AcervoRequest.class))).thenReturn(serviceResponse);

        this.mockMvc.perform(put("/acervo/1/update")
                        .contentType("application/json")
                        .content(jsonAcervoRequest()))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("NOT_FOUND"));
    }
    @Test
    void updateAcervoReturnBadRequestStatusTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST");
        when(acervoService.updateAcervo(anyLong(),any(AcervoRequest.class))).thenReturn(serviceResponse);

        this.mockMvc.perform(put("/acervo/1/update")
                        .contentType("application/json")
                        .content(jsonAcervoRequest()))
                .andDo(print())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("BAD_REQUEST"));
    }
    @Test
    void updateAcervoReturnInternalServerErrorStatusTest() throws Exception {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR");
        when(acervoService.updateAcervo(anyLong(),any(AcervoRequest.class))).thenReturn(serviceResponse);

        this.mockMvc.perform(put("/acervo/1/update")
                        .contentType("application/json")
                        .content(jsonAcervoRequest()))
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


    //testes de classes

    @Test
    void getAcervos() {
        List<AcervoResponse> acervoResponseList = new ArrayList<>();
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.OK.value(), "Empty List.", acervoResponseList);
        when(acervoService.getAcervoList("123", "123")).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.getAcervos("123", "123");

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        verify(acervoService).getAcervoList("123", "123");
    }

    @Test
    void getAcervoReturnError() {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "BAD_REQUEST");
        when(acervoService.getAcervoList("123", "123")).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.getAcervos("123", "123");

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        verify(acervoService).getAcervoList("123", "123");
    }

    @Test
    void postAcervo() {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.CREATED.value(), "Acervo created successfully.");
        when(acervoService.createAcervo(getAcervoRequest())).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.postAcervo(getAcervoRequest());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        verify(acervoService).createAcervo(getAcervoRequest());
    }

    @Test
    void postAcervoError() {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "BAD_REQUEST");
        when(acervoService.createAcervo(getAcervoRequest())).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.postAcervo(getAcervoRequest());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        verify(acervoService).createAcervo(getAcervoRequest());
    }

    @Test
    void updateAcervo() {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.OK.value(), "Acervo updated successfully.");
        when(acervoService.updateAcervo(1L, getAcervoRequest())).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.updateAcervo(1L, getAcervoRequest());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        verify(acervoService).updateAcervo(1L, getAcervoRequest());
    }
    @Test
    void updateAcervoError(){
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "BAD_REQUEST");
        when(acervoService.updateAcervo(1L, getAcervoRequest())).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.updateAcervo(1L, getAcervoRequest());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        verify(acervoService).updateAcervo(1L, getAcervoRequest());
    }
    @Test
    void updateAcervoNotFoundError(){
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.NOT_FOUND.value(), "Acervo not found.");
        when(acervoService.updateAcervo(1L, getAcervoRequest())).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.updateAcervo(1L, getAcervoRequest());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        verify(acervoService).updateAcervo(1L, getAcervoRequest());
    }

    @Test
    void deleteAcervo() {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.OK.value(), "Acervo deleted successfully.");
        when(acervoService.deleteAcervo(1L)).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.deleteAcervo(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        verify(acervoService).deleteAcervo(1L);
    }

    @Test
    void deleteAcervoError(){
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "BAD_REQUEST");
        when(acervoService.deleteAcervo(1L)).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.deleteAcervo(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        verify(acervoService).deleteAcervo(1L);
    }

    @Test
    void updateAcervoErrorNotFound(){
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.NOT_FOUND.value(), "Acervo not found.");
        when(acervoService.deleteAcervo(1L)).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.deleteAcervo(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        verify(acervoService).deleteAcervo(1L);
    }

    public AcervoRequest getAcervoRequest() {
        AcervoRequest request = new AcervoRequest();
        request.setIsbn("123");
        request.setNumeroChamada("123");
        request.setTitulo("Titulo");
        request.setAutor("Autor");
        request.setImprenta(getImprenta());
        request.setFormatoFisico(getFormatoFisico());
        request.setAssuntos("Assuntos");
        request.setOutrosAutores("Outros Autores");
        return request;
    }

    public Imprenta getImprenta() {
        Imprenta imprenta = new Imprenta();
        imprenta.setEditora("Editora");
        imprenta.setLocal("Local");
        imprenta.setAno("Ano");
        return imprenta;
    }

    public FormatoFisico getFormatoFisico() {
        FormatoFisico formatoFisico = new FormatoFisico();
        formatoFisico.setPaginas(200);
        formatoFisico.setIl(true);
        formatoFisico.setDimensoes("12cm x 20cm");
        return formatoFisico;
    }
    //JSONS
    private String jsonAcervoRequest() {
        return "{\n" +
                "    \"isbn\": \"123\",\n" +
                "    \"titulo\": \"titulo\",\n" +
                "    \"subtitulo\": \"subtitulo\",\n" +
                "    \"autor\": \"autor\",\n" +
                "    \"editora\": \"editora\",\n" +
                "    \"edicao\": \"edicao\",\n" +
                "    \"ano\": \"ano\",\n" +
                "    \"numeroChamada\": \"numeroChamada\",\n" +
                "    \"imprenta\": {\n" +
                "        \"local\": \"local\",\n" +
                "        \"editora\": \"editora\",\n" +
                "        \"ano\": \"ano\"\n" +
                "    }\n" +
                "}";
    }
}